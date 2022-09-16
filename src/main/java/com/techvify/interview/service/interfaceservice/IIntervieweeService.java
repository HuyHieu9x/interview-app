package com.techvify.interview.service.interfaceservice;


import com.techvify.interview.entity.Interviewee;
import com.techvify.interview.payload.request.IntervieweeFilterRequest;
import com.techvify.interview.payload.request.IntervieweeRequestForCreating;
import com.techvify.interview.payload.request.UpdatingForIntervieweeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IIntervieweeService {

	Page<Interviewee> getAllInterviewees(Pageable pageable, String search, IntervieweeFilterRequest filterLevelName);

	Interviewee getIntervieweeByID(int id);

	void createInterviewee(IntervieweeRequestForCreating form);

/*	void updateInterviewee(int id, UpdatingForIntervieweeRequest form);*/

	void deleteInterviewee(int id);
	
	boolean isIntervieweeExistsByEmail(String email);
	
	boolean isIntervieweeExistsByID(Integer id);
}
