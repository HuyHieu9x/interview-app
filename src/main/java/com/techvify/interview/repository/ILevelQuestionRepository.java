package com.techvify.interview.repository;

import com.techvify.interview.entity.LevelQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ILevelQuestionRepository extends JpaRepository<LevelQuestion, Integer>, JpaSpecificationExecutor<LevelQuestion> {
}
