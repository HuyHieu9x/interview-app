package com.techvify.interview.controller;

import com.techvify.interview.entity.LevelInterviewee;
import com.techvify.interview.payload.request.LevelIntervieweeRequestForCreating;
import com.techvify.interview.payload.request.UpdatingLevelRequest;
import com.techvify.interview.payload.response.LevelResponse;
import com.techvify.interview.payload.response.MessageResponse;
import com.techvify.interview.service.interfaceservice.ILevelIntervieweeService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "api/v1/level-interviewee")
@Validated
public class LevelIntervieweeController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ILevelIntervieweeService service;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> getAllLevels(Pageable pageable,
                                          @RequestParam(value = "search", required = false)
                                          String search) {
        Page<LevelInterviewee> entities = service.getAllLevels(pageable, search);

        // convert entities --> dtos
        List<LevelResponse> dtos = modelMapper.map(
                entities.getContent(),
                new TypeToken<List<LevelResponse>>() {
                }.getType());

        Page<LevelResponse> dtoPages = new PageImpl<>(dtos, pageable, entities.getTotalElements());
        return new ResponseEntity<>(dtoPages, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> getLevelByID(@PathVariable(name = "id") @LevelIDExists int id) {
        LevelInterviewee entity = service.getLevelByID(id);
        // convert entity to dto
        LevelResponse dto = modelMapper.map(entity, LevelResponse.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> createLevelInterviewee(@Valid @RequestBody LevelIntervieweeRequestForCreating levelRequestForCreating) {
        try {
            service.createLevel(levelRequestForCreating);
            return new ResponseEntity<>("Create successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> update(@LevelIDExists @PathVariable("id") int id, @RequestBody @Valid UpdatingLevelRequest updatingLevelRequest) {
        try {
            return service.updateLevel(id, updatingLevelRequest);
        }catch (Exception e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> deleteLevel(@PathVariable(name = "id") int id) {
        service.deleteLevel(id);
        return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
    }
}
