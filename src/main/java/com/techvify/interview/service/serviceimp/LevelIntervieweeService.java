package com.techvify.interview.service.serviceimp;

import com.techvify.interview.entity.LevelInterviewee;
import com.techvify.interview.entity.LevelQuestion;
import com.techvify.interview.payload.request.LevelIntervieweeRequestForCreating;
import com.techvify.interview.payload.request.UpdatingLevelRequest;
import com.techvify.interview.payload.response.MessageResponse;
import com.techvify.interview.repository.ILevelIntervieweeRepository;
import com.techvify.interview.repository.ILevelQuestionRepository;
import com.techvify.interview.service.interfaceservice.ILevelIntervieweeService;
import com.techvify.interview.specification.LevelIntervieweeSpecification;
import org.apache.catalina.connector.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelIntervieweeService implements ILevelIntervieweeService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ILevelIntervieweeRepository repository;

    @Autowired
    private ILevelQuestionRepository levelQuestionRepository;

    @Override
    public Page<LevelInterviewee> getAllLevels(Pageable pageable, String search) {
        Specification<LevelInterviewee> where = LevelIntervieweeSpecification.buildWhere(search);
        return repository.findAll(where, pageable);
    }

    @Override
    public LevelInterviewee getLevelByID(int id) {
        return repository.findById(id).get();
    }

    @Override
    public void createLevel(LevelIntervieweeRequestForCreating levelRequestForCreating) {
        List<LevelQuestion> questionList = levelQuestionRepository.findAll();
        levelRequestForCreating.setLevelQuestionList(questionList);

        LevelInterviewee levelInterviewee = new LevelInterviewee();
        levelInterviewee.setName(levelRequestForCreating.getName());
        levelInterviewee.setLevelQuestions(levelRequestForCreating.getLevelQuestionList());

        repository.save(levelInterviewee);
    }

    @Override
    public ResponseEntity<?> updateLevel(int id, UpdatingLevelRequest level) {
        LevelInterviewee levelInterviewee = repository.findById(id).orElseThrow();
        if (levelInterviewee != null) {
            levelInterviewee.setName(level.getName());
        } else {
            MessageResponse messageResponse = new MessageResponse("No find Level Interviewee");
            return ResponseEntity.badRequest().body(messageResponse);
        }
        return new ResponseEntity<>(repository.save(levelInterviewee), HttpStatus.OK);
    }

    @Override
    public void deleteLevel(int id) {
        repository.deleteById(id);
    }

/*	@Override
	public boolean isLevelExistsByCode(String code) {
		return repository.existsByCode(code);
	}*/

    @Override
    public boolean isLevelExistsByID(Integer id) {
        return repository.existsById(id);
    }

}
