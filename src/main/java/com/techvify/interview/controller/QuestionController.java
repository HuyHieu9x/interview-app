package com.techvify.interview.controller;

import com.techvify.interview.entity.Question;
import com.techvify.interview.payload.request.Filter;
import com.techvify.interview.payload.request.QuestionRequest;
import com.techvify.interview.payload.request.UpdatingForQuestionRequest;
import com.techvify.interview.payload.response.QuestionResponse;
import com.techvify.interview.service.interfaceservice.IQuestionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins ="*", allowedHeaders ="*")
@RestController
@RequestMapping("/api/v1/question")
@Validated
public class QuestionController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IQuestionService service;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> getAllInterviewees(Pageable pageable,
                                                @RequestParam(value = "search", required = false)
                                                String search, Filter questionFilter, LocalDateTime sortDirection) {

        Page<Question> entities = service.getFindAll(pageable, search, questionFilter);

        // convert entities --> dtos
        List<QuestionResponse> dtos = modelMapper.map(
                entities.getContent(),
                new TypeToken<List<QuestionResponse>>() {
                }.getType());

        Page<QuestionResponse> dtoPages = new PageImpl<>(dtos, pageable, entities.getTotalElements());
        return new ResponseEntity<>(dtoPages, HttpStatus.OK);

    }


    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> createQuestion(@RequestBody @Valid QuestionRequest form) {
        service.create(form);
        return new ResponseEntity<>("Create successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        service.deleteById(id);
        return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> getQuestionByID(@PathVariable(name = "id") int id) {
        Question entity = service.getQuestionByID(id);

        // convert entity to dto
        QuestionResponse request = modelMapper.map(entity, QuestionResponse.class);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> updateQuestion(@PathVariable(name = "id") int id, @RequestBody UpdatingForQuestionRequest form) {
        Question questionRequest = modelMapper.map(form, Question.class);

        Question question = service.updateQuestion(id, questionRequest);

        // entity to DTO
        UpdatingForQuestionRequest updatingForQuestionRequestResponse = modelMapper.map(question,UpdatingForQuestionRequest.class);

        return ResponseEntity.ok().body(updatingForQuestionRequestResponse);
    }

    @GetMapping(value = "/questionByProgrammingLanguage/{lang}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public List<Question> getQuestionListByProgrammingLanguageName(@PathVariable(name = "lang") String programmingLanguageName) {
        return service.getQuestionListByProgrammingLanguage(programmingLanguageName);
    }

   /* @GetMapping(value = "/questionListByLevel/{level}/{lang}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public List<Question> getQuestionListByLevel(@PathVariable(name = "level") String levelName,@PathVariable(name = "lang") String programmingLanguageName) {
        return service.getQuestionListByLevelAndLanguage(levelName, programmingLanguageName);
    }*/

    @GetMapping(value = "/questionListByLanguageAndFramework/{language}/{framework}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public List<Question> getQuestionListByLanguageAndFramework(@PathVariable(name = "framework") String framework,@PathVariable(name = "language") String language) {
        return service.getQuestionListByLanguageAndFramework(framework, language);
    }

}

