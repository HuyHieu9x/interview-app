package com.techvify.interview.controller;

import com.techvify.interview.entity.LevelQuestion;
import com.techvify.interview.payload.request.LevelQuestionRequestForCreating;
import com.techvify.interview.payload.request.UpdatingLevelRequest;
import com.techvify.interview.payload.response.LevelResponse;
import com.techvify.interview.service.serviceimp.LevelQuestionService;
import com.techvify.interview.validation.level.LevelIDExists;
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
import java.util.List;

@CrossOrigin(origins ="*", allowedHeaders ="*")
@RestController
@RequestMapping(value = "api/v1/level-question")
@Validated
public class LevelQuestionController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LevelQuestionService service;


    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> getAllLevels(Pageable pageable,
                                          @RequestParam(value = "search",required = false)
                                          String search) {
        Page<LevelQuestion> entities = service.getAllLevels(pageable,search);

        // convert entities --> dtos
        List<LevelResponse> dtos = modelMapper.map(
                entities.getContent(),
                new TypeToken<List<LevelResponse>>(){}.getType());

        Page<LevelResponse> dtoPages = new PageImpl<>(dtos, pageable, entities.getTotalElements());
        return new ResponseEntity<>(dtoPages, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> getLevelByID(@PathVariable(name = "id") @LevelIDExists int id) {
        LevelQuestion entity = service.getLevelByID(id);

        // convert entity to dto
        LevelResponse dto = modelMapper.map(entity, LevelResponse.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> createLevelQuestion(@RequestBody @Valid LevelQuestionRequestForCreating form) {
        service.createLevel(form);
        return new ResponseEntity<>("Create successfully", HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> updateLevel(@LevelIDExists @PathVariable(name = "id") int id,@Valid @RequestBody UpdatingLevelRequest form) {
        service.updateLevel(id,form);
        return new ResponseEntity<>("Update successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> deleteLevel(@PathVariable(name = "id") int id) {
        service.deleteLevel(id);
        return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
    }
}
