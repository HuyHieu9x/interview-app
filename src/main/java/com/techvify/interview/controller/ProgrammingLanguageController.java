package com.techvify.interview.controller;

import com.techvify.interview.entity.ProgrammingLanguage;
import com.techvify.interview.service.serviceimp.ProgrammingLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins ="*", allowedHeaders ="*")
@RestController
@RequestMapping("/api/v1/programming-language")
@Validated
public class ProgrammingLanguageController {
    @Autowired
    private ProgrammingLanguageService programmingLanguageService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<Map<String,Object>> getAllByPage(Pageable pageable){
        return programmingLanguageService.getAllByPage(pageable);
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> create(@RequestBody ProgrammingLanguage programmingLanguage){
        return ResponseEntity.ok(programmingLanguageService.create(programmingLanguage));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ProgrammingLanguage programmingLanguage){
        return ResponseEntity.ok(programmingLanguageService.update(id, programmingLanguage));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        programmingLanguageService.delete(id);
        return new ResponseEntity<>("Delete Successfully",HttpStatus.OK);
    }
}
