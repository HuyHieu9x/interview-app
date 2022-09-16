package com.techvify.interview.repository;

import com.techvify.interview.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IQuestionRepository extends JpaRepository<Question, Integer>, JpaSpecificationExecutor<Question> {
}
