package com.techvify.interview.repository;

import com.techvify.interview.entity.ProgrammingLanguage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage,Integer> {
    Page<ProgrammingLanguage> findAll(Pageable pageable);
}
