package com.techvify.interview.service.interfaceservice;

import com.techvify.interview.entity.Question;
import com.techvify.interview.payload.request.Filter;
import com.techvify.interview.payload.request.QuestionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IQuestionService {
    Page<Question> getFindAll(Pageable pageable, String search, Filter questionFilter);

    void deleteById(int id);

    List<Question> findAll();

    void create(QuestionRequest form);

    Question updateQuestion(int id, Question form);

    Question getQuestionByID(int id);

    List<Question> getQuestionListByProgrammingLanguage(String programmingLanguageName);

    List<Question> getQuestionListByLevelAndLanguage(int levelId, String programmingLanguageName);

    List<Question> getQuestionListByLanguageAndFramework(String framework, String language);
}
