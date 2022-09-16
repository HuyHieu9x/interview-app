package com.techvify.interview.repository;

import com.techvify.interview.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingRepository extends JpaRepository<Setting, Integer> {
    List<Setting> findByLevelIntervieweeId(int id);
}
