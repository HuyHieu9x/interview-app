package com.techvify.interview.service.serviceimp;

import com.techvify.interview.entity.*;
import com.techvify.interview.payload.request.Filter;
import com.techvify.interview.payload.request.QuestionRequest;
import com.techvify.interview.repository.*;
import com.techvify.interview.service.interfaceservice.IQuestionService;
import com.techvify.interview.specification.Specifications;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements IQuestionService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    IQuestionRepository repository;

    @Autowired
    ILevelQuestionRepository levelQuestionRepository;

    @Autowired
    private IProgrammingLanguageRepository programmingLanguageRepository;

    @Autowired
    private IFrameworkRepository frameworkRepository;

    @Override
    public Page<Question> getFindAll(Pageable pageable, String search, Filter questionFilter) {
        Specification<Question> where = Specifications.buildWhere(search, questionFilter);
        return repository.findAll(where, pageable);
    }

    @Override
    public void deleteById(int id) {
        Question question = repository.findById(id).get();
        repository.deleteById(question.getId());
    }

    @Override
    public List<Question> findAll() {
        return repository.findAll();
    }

    @Override
    public void create(QuestionRequest form) {
        // omit id field
        TypeMap<QuestionRequest, Question> typeMap = modelMapper.getTypeMap(QuestionRequest.class, Question.class);
        if (typeMap == null) { // if not already added
            // skip field
            modelMapper.addMappings(new PropertyMap<QuestionRequest, Question>() {

                @Override
                protected void configure() {
                    skip(destination.getId());

                }
            });
        }
        // convert form to entity
        Question question = modelMapper.map(form, Question.class);
        repository.save(question);
    }

    @Override
    public Question updateQuestion(int id, Question form) {
        LevelQuestion levelId = levelQuestionRepository.findById(form.getLevelQuestion().getId()).get();
        ProgrammingLanguage programmingLanguageId = programmingLanguageRepository.findById(form.getLanguage().getId()).get();
        Framework frameworkId = frameworkRepository.findById(form.getFramework().getId()).get();

        Question oldQuestion = repository.findById(id).get();

        oldQuestion.setAnswer(form.getAnswer());
        oldQuestion.setContent(form.getContent());
        oldQuestion.setLevelQuestion(levelId);
        oldQuestion.setFramework(frameworkId);
        oldQuestion.setLanguage(programmingLanguageId);
        oldQuestion.setUpdatedAt(LocalDateTime.now());
        repository.save(oldQuestion);

        return oldQuestion;
    }

    @Override
    public Question getQuestionByID(int id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Question> getQuestionListByProgrammingLanguage(String programmingLanguage) {
        List<Question> allQuestions = findAll();
        List<Question> questionListByProgrammingLanguage = new ArrayList<>();
        for (Question question : allQuestions) {
            if (question.getLanguage().getName().equalsIgnoreCase(programmingLanguage)) {
                questionListByProgrammingLanguage.add(question);
            }
        }
        return questionListByProgrammingLanguage;
    }

    @Override
    public List<Question> getQuestionListByLevelAndLanguage(int idLevel, String language) {
        List<Question> questionsByLanguage = getQuestionListByProgrammingLanguage(language);
        List<Question> questionListByLanguageNameAndLevel = new ArrayList<>();
        for (Question question : questionsByLanguage) {
            if (question.getLevelQuestion().getId() == idLevel) {
                questionListByLanguageNameAndLevel.add(question);
            }
        }
        return questionListByLanguageNameAndLevel;
    }

    @Override
    public List<Question> getQuestionListByLanguageAndFramework(String framework, String language) {
        List<Question> questionsByLanguage = getQuestionListByProgrammingLanguage(language);
        List<Question> questionListByLanguageNameAndFramework = new ArrayList<>();
        for (Question question : questionsByLanguage) {
            if (question.getFramework().getName().equalsIgnoreCase(framework)) {
                questionListByLanguageNameAndFramework.add(question);
            }
        }
        return questionListByLanguageNameAndFramework;
    }


}
