package com.techvify.interview.service.serviceimp;

import com.techvify.interview.entity.LevelQuestion;
import com.techvify.interview.payload.request.LevelIntervieweeRequestForCreating;
import com.techvify.interview.payload.request.LevelQuestionRequestForCreating;
import com.techvify.interview.payload.request.UpdatingLevelRequest;
import com.techvify.interview.repository.ILevelQuestionRepository;
import com.techvify.interview.repository.SettingRepository;
import com.techvify.interview.service.interfaceservice.ILevelQuestionService;
import com.techvify.interview.specification.LevelQuestionSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class LevelQuestionService implements ILevelQuestionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ILevelQuestionRepository repository;

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public Page<LevelQuestion> getAllLevels(Pageable pageable, String search) {
        Specification<LevelQuestion> where = LevelQuestionSpecification.buildWhere(search);
        return repository.findAll(where,pageable);
    }

    @Override
    public LevelQuestion getLevelByID(int id) {
        return repository.findById(id).get();
    }

    @Override
    public void createLevel(LevelQuestionRequestForCreating form) {
        LevelQuestion level = modelMapper.map(form, LevelQuestion.class);
        repository.save(level);
    }

    @Override
    public void updateLevel(int id, UpdatingLevelRequest level) {
        LevelQuestion levelUpdate = modelMapper.map(level, LevelQuestion.class);
        levelUpdate.setId(id);
        repository.save(levelUpdate);
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
