package com.techvify.interview.repository;

import com.techvify.interview.entity.Framework;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFrameworkRepository extends JpaRepository<Framework,Integer> {
    Page<Framework> findAll(Pageable pageable);
}
