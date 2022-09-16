package com.techvify.interview.controller;

import com.techvify.interview.entity.Setting;
import com.techvify.interview.service.serviceimp.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="*", allowedHeaders ="*")
@RestController
@RequestMapping("/api/v1/setting")
@Validated
public class SettingController {
    @Autowired
    private SettingService settingService;

    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id ,@RequestBody List<Integer> integers){
        settingService.updateSetting(id,integers);
    }

    @GetMapping("/{level_interviewee_id}")
    public List<Setting> getLevelIntervieweeId(@PathVariable("level_interviewee_id") int levelIntervieweeId ){
       return settingService.getByLevelIntervieweeId(levelIntervieweeId);
    }

}
