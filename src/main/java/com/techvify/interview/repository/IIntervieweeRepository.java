package com.techvify.interview.repository;


import com.techvify.interview.entity.Interviewee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface IIntervieweeRepository extends JpaRepository<Interviewee, Integer>,JpaSpecificationExecutor<Interviewee>{
	boolean existsByEmail(String email);
}
