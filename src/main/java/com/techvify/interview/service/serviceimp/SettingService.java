package com.techvify.interview.service.serviceimp;

import com.techvify.interview.entity.Setting;
import com.techvify.interview.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;

    public void updateSetting(int levelIntervieweeId, List<Integer> listNQuestion) {
        List<Setting> settings = settingRepository.findByLevelIntervieweeId(levelIntervieweeId);

        for (int i = 0; i < settings.size(); i++) {
            settings.get(i).setNumberQuestion(listNQuestion.get(i));
            settingRepository.save(settings.get(i));
        }
    }

    public List<Setting> getByLevelIntervieweeId(int id){
        List<Setting> settings = settingRepository.findByLevelIntervieweeId(id);
        return settings;
    }
}
