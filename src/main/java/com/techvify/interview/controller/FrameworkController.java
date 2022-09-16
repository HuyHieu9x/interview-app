package com.techvify.interview.controller;

import com.techvify.interview.entity.Framework;
import com.techvify.interview.payload.request.FrameworkRequest;
import com.techvify.interview.service.serviceimp.FrameworkService;
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
@RequestMapping("/api/v1/framework")
@Validated
public class FrameworkController {

    @Autowired
    private FrameworkService frameworkService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<Map<String, Object>> getAllByPage(Pageable pageable) {
        return frameworkService.getAllByPage(pageable);
    }

    @PostMapping  
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<Framework> create(@RequestBody FrameworkRequest framework) {
        frameworkService.create(framework);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<Framework> update(@PathVariable("id") int id, @RequestBody FrameworkRequest framework) {
        frameworkService.update(id, framework);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INTERVIEWER')")
    public ResponseEntity<Framework> delete(@PathVariable("id") int id) {
        frameworkService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}