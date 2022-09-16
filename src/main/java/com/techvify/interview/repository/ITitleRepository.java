package com.techvify.interview.repository;

import com.techvify.interview.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITitleRepository extends JpaRepository<Title, Integer> {

    @Query(value = "SELECT * FROM title limit :page,:size",nativeQuery = true)
    List<Title> findAll(@Param("page") int page ,@Param("size") int size);

    Title findByName(String name);

    boolean existsByName(String name);

    boolean existsById(int id);
}
