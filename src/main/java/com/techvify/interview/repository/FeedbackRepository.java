package com.techvify.interview.repository;

import com.techvify.interview.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer>, JpaSpecificationExecutor<Feedback> {
    List<Feedback> findBySessionId(int id);
}
