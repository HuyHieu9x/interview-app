package com.techvify.interview.service.interfaceservice;

import com.techvify.interview.entity.LevelInterviewee;
import com.techvify.interview.payload.request.LevelIntervieweeRequestForCreating;
import com.techvify.interview.payload.request.UpdatingLevelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ILevelIntervieweeService {
    Page<LevelInterviewee> getAllLevels(Pageable pageable, String search);

    LevelInterviewee getLevelByID(int id);

    void createLevel(LevelIntervieweeRequestForCreating levelRequestForCreating);

    ResponseEntity<?> updateLevel(int id, UpdatingLevelRequest level);

    void deleteLevel(int id);

/*    boolean isLevelExistsByCode(String code);*/

    boolean isLevelExistsByID(Integer id);
}
