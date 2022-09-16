package com.techvify.interview.controller;

import com.techvify.interview.entity.Feedback;
import com.techvify.interview.entity.InterviewSession;
import com.techvify.interview.payload.response.InterviewSessionResponse;
import com.techvify.interview.payload.response.MessageResponse;
import com.techvify.interview.payload.response.WriteExcelInterviewSession;
import com.techvify.interview.service.serviceimp.InterviewSessionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/interview-session")
@Validated
public class InterviewSessionController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private InterviewSessionService interviewSessionService;

    @Autowired
    private WriteExcelInterviewSession writeExcelInterviewSession;

    @GetMapping
    public ResponseEntity<?> getAllInterviewSession(Pageable pageable,
                                                    @RequestParam(value = "search", required = false)
                                                    String search, com.techvify.interview.payload.request.IntervieweeFilterRequest filterLevelName) {

        Page<InterviewSession> entities = interviewSessionService.getFindAllSession(pageable, search, filterLevelName);

        // convert entities --> dtos
        List<InterviewSessionResponse> dtos = modelMapper.map(
                entities.getContent(),
                new TypeToken<List<InterviewSessionResponse>>() {
                }.getType());

        Page<InterviewSessionResponse> dtoPages = new PageImpl<>(dtos, pageable, entities.getTotalElements());
        return ResponseEntity.ok().body(dtoPages);
    }

    @GetMapping(value = "/randomList/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> getRandomQuestionList(@PathVariable(name = "id") int id){
        return interviewSessionService.getRandomQuestionList(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<InterviewSession> getById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(interviewSessionService.findById(id),HttpStatus.OK);
    }


    @PutMapping("/conclusion/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<InterviewSession> updateConclusion(@PathVariable(name = "id") int id, @RequestBody com.techvify.interview.payload.request.UpdateConclusion updateConclusion) {
        return interviewSessionService.updateConclusion(id, updateConclusion);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<InterviewSession> create(@RequestBody com.techvify.interview.payload.request.InterviewSessionRequest interviewSessionRequests) {
        try {
            interviewSessionService.create(interviewSessionRequests);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<InterviewSession> update(@PathVariable("id") int id, @RequestBody com.techvify.interview.payload.request.InterviewSessionRequest interviewSessionRequest) {
        return ResponseEntity.ok(interviewSessionService.update(id, interviewSessionRequest));
    }

    @PutMapping("/status/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> updateStatus(@PathVariable("id") int id, @RequestBody com.techvify.interview.payload.request.StatusRequest status) {
        return ResponseEntity.ok(interviewSessionService.updateStatus(id, status));
    }

    @PutMapping("/add-question/{id-session}/{id-question}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> updateQuestion(@PathVariable("id-session") int idSession, @PathVariable("id-question") int idQuestion) {
        return ResponseEntity.ok(interviewSessionService.updateQuestion(idSession, idQuestion));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        interviewSessionService.delete(id);
        return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public InterviewSession getByFullName(@PathVariable("name") String name) {
        return interviewSessionService.find(name);
    }

    @PutMapping("/feedback/note/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public Feedback updateFeedbackNote(@PathVariable("id") int id, @RequestBody com.techvify.interview.payload.request.FeedbackNoteRequest feedbackRequest) {
        return interviewSessionService.updateNoteFeedback(feedbackRequest, id);
    }

    @PutMapping("/feedback/score/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public Feedback updateFeedbackScore(@PathVariable("id") int id, @RequestBody com.techvify.interview.payload.request.FeedbackScoreRequest feedbackRequest) {
        return interviewSessionService.updateScoreFeedback(feedbackRequest, id);
    }

    @GetMapping(value = "/excel/{id}")
    public ResponseEntity<?> getExcel(HttpServletResponse response, @PathVariable("id") int id) throws IOException {
        try {
            InterviewSession interviewSession = interviewSessionService.findById(id);
            InterviewSessionResponse interviewSessionResponse = modelMapper.map(interviewSession, InterviewSessionResponse.class);
            /* final String excelFilePath = "C:/demo/session.xlsx";*/

            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormatter.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=session" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            writeExcelInterviewSession.writeExcel(interviewSessionResponse, response);
            return new ResponseEntity<>("Export Successfully!",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("No find interview-session!",HttpStatus.BAD_REQUEST);
        }
    }
}