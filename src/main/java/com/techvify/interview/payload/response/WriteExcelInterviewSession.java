package com.techvify.interview.payload.response;

import com.techvify.interview.entity.Feedback;
import com.techvify.interview.entity.InterviewSession;
import com.techvify.interview.entity.Question;
import com.techvify.interview.repository.FeedbackRepository;
import com.techvify.interview.repository.IInterviewSessionRepository;
import com.techvify.interview.repository.IQuestionRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class WriteExcelInterviewSession {
    @Autowired
    private ModelMapper modelMapper;
    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_INTERVIEWEE = 1;
    public static final int COLUMN_INDEX_USERS = 2;
    public static final int COLUMN_INDEX_DATE = 3;
    public static final int COLUMN_INDEX_STATUS = 4;
    public static final int COLUMN_INDEX_QUESTION = 5;
    public static final int COLUMN_INDEX_ANSWER = 6;
    public static final int COLUMN_INDEX_NOTE = 7;
    public static final int COLUMN_INDEX_SCORE = 8;
    public static final int COLUMN_INDEX_CONCLUSION = 9;
    public static final int COLUMN_INDEX_TOTAL = 10;
    private CellStyle cellStyleFormatNumber = null;

    static Logger logger = Logger.getLogger(WriteExcelInterviewSession.class.getName());

    public void writeExcel(InterviewSessionResponse interviewSessionResponses, HttpServletResponse response) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook();
        // Create sheet
        Sheet sheet = workbook.createSheet("InterviewSessions"); // Create sheet with sheet name
        int rowIndex = 0;
        // Write header
        writeHeader(sheet, rowIndex);
        // Write data
        rowIndex++;
        // Create row
        Row row = sheet.createRow(rowIndex);
        // Write data on row
        writeInterviewSessionResponse(interviewSessionResponses, row);

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, response);
        logger.info("Done !!!");
    }

    @Autowired
    private IInterviewSessionRepository interviewSessionRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private IQuestionRepository questionRepository;

    // Create dummy data
    public List<InterviewSessionResponse> getInterviewSessionResponsess() {
        List<InterviewSession> interviewSessions = interviewSessionRepository.findAll();
        List<InterviewSessionResponse> interviewSessionResponses = new ArrayList<>();

        for (InterviewSession interviewSession : interviewSessions
        ) {
            InterviewSessionResponse interviewSessionResponse = modelMapper.map(interviewSession, InterviewSessionResponse.class);
            interviewSessionResponses.add(interviewSessionResponse);
        }
        return interviewSessionResponses;
    }

    // Create workbook
    private Workbook getWorkbook() {
        Workbook workbook = null;
        workbook = new XSSFWorkbook();
        return workbook;
    }

    // Write header with format
    private static void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Id");

        cell = row.createCell(COLUMN_INDEX_INTERVIEWEE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Interviewee");

        cell = row.createCell(COLUMN_INDEX_USERS);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Users");

        cell = row.createCell(COLUMN_INDEX_DATE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Date");

        cell = row.createCell(COLUMN_INDEX_STATUS);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Status");

        cell = row.createCell(COLUMN_INDEX_QUESTION);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Question");

        cell = row.createCell(COLUMN_INDEX_ANSWER);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Answer");

        cell = row.createCell(COLUMN_INDEX_NOTE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Note");

        cell = row.createCell(COLUMN_INDEX_SCORE);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Score");

        cell = row.createCell(COLUMN_INDEX_CONCLUSION);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Conclusion");

        cell = row.createCell(COLUMN_INDEX_TOTAL);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Total");
    }

    private static void formatOutput(Cell cell, InterviewSessionResponse interviewSessionResponse) {
        cell.getRow()
                .setHeightInPoints(cell.getSheet().getDefaultRowHeightInPoints() * interviewSessionResponse.getQuestions().size());
        CellStyle cellStyle = cell.getSheet().getWorkbook().createCellStyle();
        cellStyle.setWrapText(true);
        cell.setCellStyle(cellStyle);
    }

    // Write data
    private void writeInterviewSessionResponse(InterviewSessionResponse interviewSessionResponse, Row row) {
        // Format number
        short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
        //Create CellStyle
        Workbook workbook = row.getSheet().getWorkbook();
        cellStyleFormatNumber = workbook.createCellStyle();
        cellStyleFormatNumber.setDataFormat(format);

        Cell cell = row.createCell(COLUMN_INDEX_ID);
        cell.setCellValue(interviewSessionResponse.getId());

        cell = row.createCell(COLUMN_INDEX_INTERVIEWEE);
        cell.setCellValue(interviewSessionResponse.getInterviewee().getName());

        StringBuilder stringBuilderUsers = new StringBuilder();
        List<InterviewSessionResponse.UserProfile> userProfiles = interviewSessionResponse.getUsers();
        for (InterviewSessionResponse.UserProfile userProfile : userProfiles
        ) {
            stringBuilderUsers.append(userProfile.getUsername());
            stringBuilderUsers.append(",");
        }

        cell = row.createCell(COLUMN_INDEX_USERS);
        cell.setCellValue(String.valueOf(stringBuilderUsers));

        cell = row.createCell(COLUMN_INDEX_DATE);
        cell.setCellValue(interviewSessionResponse.getDate());

        cell = row.createCell(COLUMN_INDEX_STATUS);
        cell.setCellValue(interviewSessionResponse.getStatus());

        int id = interviewSessionResponse.getId();
        InterviewSession interviewSession = interviewSessionRepository.findById(id).orElseThrow();
        List<Question> questions = new ArrayList<>();
        List<Feedback> feedbacks = feedbackRepository.findBySessionId(id);

        for (Feedback feedback : feedbacks) {
            questions.add(new Question(
                    questionRepository.findById(feedback.getQuestionId()).orElseThrow().getId(),
                    questionRepository.findById(feedback.getQuestionId()).orElseThrow().getContent(),
                    questionRepository.findById(feedback.getQuestionId()).orElseThrow().getLevelQuestion(),
                    questionRepository.findById(feedback.getQuestionId()).orElseThrow().getLanguage(),
                    questionRepository.findById(feedback.getQuestionId()).orElseThrow().getFramework(),
                    questionRepository.findById(feedback.getQuestionId()).orElseThrow().getCreatedAt(),
                    questionRepository.findById(feedback.getQuestionId()).orElseThrow().getUpdatedAt(),
                    questionRepository.findById(feedback.getQuestionId()).orElseThrow().getAnswer(),
                    feedback
            ));
        }

        interviewSession.setQuestions(questions);
        interviewSessionResponse = modelMapper.map(interviewSession, InterviewSessionResponse.class);

        cell = row.createCell(COLUMN_INDEX_QUESTION);
        StringBuilder stringBuilderQuestion = new StringBuilder();
        formatOutput(cell, interviewSessionResponse);

        for (Question question : interviewSessionResponse.getQuestions()
        ) {
            stringBuilderQuestion.append(question.getContent());
            stringBuilderQuestion.append("\n");
        }
        cell.setCellValue(String.valueOf(stringBuilderQuestion));

        cell = row.createCell(COLUMN_INDEX_ANSWER);
        StringBuilder stringBuilderAnswer = new StringBuilder();
        formatOutput(cell, interviewSessionResponse);

        for (Question question : interviewSessionResponse.getQuestions()
        ) {
            stringBuilderAnswer.append(question.getAnswer());
            stringBuilderAnswer.append("\n");
        }
        cell.setCellValue(String.valueOf(stringBuilderAnswer));

        cell = row.createCell(COLUMN_INDEX_NOTE);
        StringBuilder stringBuilderNote = new StringBuilder();
        formatOutput(cell, interviewSessionResponse);

        for (Question question : interviewSessionResponse.getQuestions()
        ) {
            stringBuilderNote.append(question.getFeedback().getNote());
            stringBuilderNote.append("\n");
        }
        cell.setCellValue(String.valueOf(stringBuilderNote));

        cell = row.createCell(COLUMN_INDEX_SCORE);
        StringBuilder stringBuilderScore = new StringBuilder();
        formatOutput(cell, interviewSessionResponse);

        for (Question question : interviewSessionResponse.getQuestions()
        ) {
            stringBuilderScore.append(question.getFeedback().getScore());
            stringBuilderScore.append("\n");
        }
        cell.setCellValue(String.valueOf(stringBuilderScore));

        cell = row.createCell(COLUMN_INDEX_CONCLUSION);
        cell.setCellValue(interviewSessionResponse.getConclusion());

        // Create cell formula
        // totalMoney = price * quantity

        cell = row.createCell(COLUMN_INDEX_TOTAL);
        cell.setCellStyle(cellStyleFormatNumber);

        double score = 0;
        for (Question question : interviewSessionResponse.getQuestions()
        ) {
            score += question.getFeedback().getScore();
        }
        double scoreMax = (double) interviewSessionResponse.getQuestions().size() * 10;
        cell.setCellValue(score / scoreMax * 10);
    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 11); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFillPattern(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);

        return cellStyle;
    }

    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
    }
}
