package com.techvify.interview.service.serviceimp;

import com.techvify.interview.entity.*;
import com.techvify.interview.payload.request.*;
import com.techvify.interview.payload.response.MessageResponse;
import com.techvify.interview.repository.*;
import com.techvify.interview.specification.InterviewSessionSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class InterviewSessionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IInterviewSessionRepository interviewSessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionServiceImpl questionService;

    @Autowired
    IIntervieweeRepository intervieweeRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    IQuestionRepository questionRepository;

    @Autowired
    SettingRepository settingRepository;

    @Autowired
    ILevelQuestionRepository levelQuestionRepository;

    Random random = null;

    public InterviewSessionService() {
        random = new SecureRandom();
    }

    public Page<InterviewSession> getFindAllSession(Pageable pageable, String search, IntervieweeFilterRequest filterRequest) {
        Specification<InterviewSession> where = InterviewSessionSpecification.buildWhere(search, filterRequest);
        return interviewSessionRepository.findAll(where, pageable);
    }

    public InterviewSession create(InterviewSessionRequest interviewSessionRequest) {
        InterviewSession session = new InterviewSession();

        Set<User> users = new HashSet<>();
        User user = null;

        for (Integer usersId : interviewSessionRequest.getUserId()) {
            Optional<User> userOptional = userRepository.findById(usersId);
            if (userOptional.isPresent()) {
                user = userOptional.get();
            }
            users.add(user);
        }

        session.setUsers(new HashSet<>(users));

        session.setInterviewee(new Interviewee() {{
            setId(interviewSessionRequest.getIntervieweeId());
        }});
        session.setDate(interviewSessionRequest.getDate());
        session.setStatus(StatusName.UP_COMING);
        interviewSessionRepository.save(session);
        return session;
    }

    public InterviewSession update(int id, InterviewSessionRequest interviewSessionRequest) {
        InterviewSession session = interviewSessionRepository.findById(id).orElseThrow();
        Set<User> users = new HashSet<>();
        for (Integer usersId : interviewSessionRequest.getUserId()) {
            User tempUser = userRepository.findById(usersId).orElseThrow();
            users.add(tempUser);
        }
        session.setUsers(new HashSet<>(users));
        Interviewee interviewee = intervieweeRepository.findById(interviewSessionRequest.getIntervieweeId()).orElseThrow();

        session.setInterviewee(interviewee);

        session.setDate(interviewSessionRequest.getDate());
        interviewSessionRepository.save(session);
        return session;
    }

    public ResponseEntity<InterviewSession> updateConclusion(int id, UpdateConclusion updateNoteAll) {

        InterviewSession session = interviewSessionRepository.findById(id).orElseThrow();
        session.setConclusion(updateNoteAll.getConclusion());
        interviewSessionRepository.save(session);
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    public void delete(int id) {
        interviewSessionRepository.deleteById(id);
    }

    public InterviewSession find(String key) {
        return interviewSessionRepository.findByIntervieweeName(key);
    }

    public InterviewSession findById(int id) {
        InterviewSession interviewSession = interviewSessionRepository.findById(id).orElseThrow();
        List<Question> questions = new ArrayList<>();
        List<Feedback> feedbacks = feedbackRepository.findBySessionId(id);

        for (Feedback feedback : feedbacks) {
            questions.add(new Question(questionService.getQuestionByID(feedback.getQuestionId()).getId(), questionService.getQuestionByID(feedback.getQuestionId()).getContent(), questionService.getQuestionByID(feedback.getQuestionId()).getLevelQuestion(), questionService.getQuestionByID(feedback.getQuestionId()).getLanguage(), questionService.getQuestionByID(feedback.getQuestionId()).getFramework(), questionService.getQuestionByID(feedback.getQuestionId()).getCreatedAt(), questionService.getQuestionByID(feedback.getQuestionId()).getUpdatedAt(), questionService.getQuestionByID(feedback.getQuestionId()).getAnswer(), feedback));
        }

        interviewSession.setQuestions(questions);
        return interviewSession;
    }

    public ResponseEntity<?> getRandomQuestionList(int id) {
        int index;
        InterviewSession interviewSession = interviewSessionRepository.findById(id).orElseThrow();
        List<Question> result = new ArrayList<>();

        String lang = interviewSession.getInterviewee().getLanguage().getName();
        int levelIntervieweeId = interviewSession.getInterviewee().getLevelInterviewee().getId();

        List<Setting> settings = settingRepository.findByLevelIntervieweeId(levelIntervieweeId);

        for (int i = 0; i < settings.size(); i++) {
            int flag = 0;
            if (questionService.getQuestionListByLevelAndLanguage(settings.get(i).getLevelQuestionId(), lang).size() < settings.get(i).getNumberQuestion()) {
                return new ResponseEntity<>(new MessageResponse("Not enough question level "+levelQuestionRepository.findById(settings.get(i).getLevelQuestionId()).orElseThrow().getName()),HttpStatus.BAD_REQUEST);
            }

            while (flag < settings.get(i).getNumberQuestion()) {
                index = random.nextInt(questionService.getQuestionListByLevelAndLanguage(settings.get(i).getLevelQuestionId(), lang).size());
                if (!result.contains(questionService.getQuestionListByLevelAndLanguage(settings.get(i).getLevelQuestionId(), lang).get(index))){
                    result.add(questionService.getQuestionListByLevelAndLanguage(settings.get(i).getLevelQuestionId(), lang).get(index));
                    flag ++;
                }
            }
        }

        interviewSession.setQuestions(result);
        interviewSessionRepository.save(interviewSession);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public Feedback updateScoreFeedback(FeedbackScoreRequest feedbackScoreRequest, int id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow();
        feedback.setScore(feedbackScoreRequest.getScore());
        return feedbackRepository.save(feedback);
    }

    public Feedback updateNoteFeedback(FeedbackNoteRequest feedbackNoteRequest, int id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow();
        feedback.setNote(feedbackNoteRequest.getNote());
        return feedbackRepository.save(feedback);
    }


    public ResponseEntity<?> updateStatus(int id, StatusRequest statusRequest) {
        InterviewSession interviewSession = interviewSessionRepository.findById(id).orElseThrow();
        String status = statusRequest.getStatusName();

        switch (status) {
            case "IN_PROCESS":
                interviewSession.setStatus(StatusName.IN_PROCESS);
                break;
            case "DONE":
                interviewSession.setStatus(StatusName.DONE);
                break;
            default:
                break;
        }

        interviewSessionRepository.save(interviewSession);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<MessageResponse> updateQuestion(int idSession, int idQuestion) {

        InterviewSession interviewSession = interviewSessionRepository.findById(idSession).orElseThrow();
        List<Question> questions = interviewSession.getQuestions();

        Question question = null;
        Optional<Question> questionOptional = questionRepository.findById(idQuestion);
        if (questionOptional.isPresent()) {
            question = questionOptional.get();
        }
        questions.add(question);

        interviewSession.setQuestions(questions);
        interviewSessionRepository.save(interviewSession);

        return ResponseEntity.ok(new MessageResponse("Add question successfully!"));
    }
}
