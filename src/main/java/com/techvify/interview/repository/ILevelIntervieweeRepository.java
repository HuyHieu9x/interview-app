package com.techvify.interview.repository;

import com.techvify.interview.entity.LevelInterviewee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ILevelIntervieweeRepository extends JpaRepository<LevelInterviewee, Integer>,JpaSpecificationExecutor<LevelInterviewee>{
    boolean existsByName(String name);
}
