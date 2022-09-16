package com.techvify.interview.repository;

import com.techvify.interview.entity.InterviewSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IInterviewSessionRepository extends JpaRepository<InterviewSession,Integer>, JpaSpecificationExecutor<InterviewSession> {
    InterviewSession findByIntervieweeName(String name);
}
