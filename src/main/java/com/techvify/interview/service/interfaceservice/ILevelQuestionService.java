package com.techvify.interview.service.interfaceservice;

import com.techvify.interview.entity.LevelQuestion;
import com.techvify.interview.payload.request.LevelIntervieweeRequestForCreating;
import com.techvify.interview.payload.request.LevelQuestionRequestForCreating;
import com.techvify.interview.payload.request.UpdatingLevelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILevelQuestionService {
    Page<LevelQuestion> getAllLevels(Pageable pageable, String search);

    LevelQuestion getLevelByID(int id);

    void createLevel(LevelQuestionRequestForCreating form);

    void updateLevel(int id, UpdatingLevelRequest level);

    void deleteLevel(int id);

    /*    boolean isLevelExistsByCode(String code);*/

    boolean isLevelExistsByID(Integer id);
}
