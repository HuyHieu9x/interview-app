package com.techvify.interview.service.interfaceservice;

import com.techvify.interview.entity.InterviewSession;
import com.techvify.interview.entity.Question;
import com.techvify.interview.payload.request.InterviewSessionRequest;
import com.techvify.interview.payload.request.IntervieweeFilterRequest;
import com.techvify.interview.payload.request.StatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IInterviewSessionService{
    Page<InterviewSession> getFindAllSession(Pageable pageable, String search, IntervieweeFilterRequest filterRequest);
    ResponseEntity<?> create(InterviewSessionRequest interviewSessionRequest);
    ResponseEntity<?> update(int id, InterviewSessionRequest interviewSessionRequest);
    ResponseEntity<?> delete(int id);
    ResponseEntity<?> status(int id, StatusRequest statusRequest);
    ResponseEntity<?> find(String key);
    ResponseEntity<List<Question>> getRandomQuestionList(int id);
    InterviewSession findById(int id);
}
