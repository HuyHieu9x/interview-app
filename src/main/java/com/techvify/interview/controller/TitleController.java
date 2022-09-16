package com.techvify.interview.controller;

import com.techvify.interview.entity.Title;
import com.techvify.interview.payload.request.TitleRequest;
import com.techvify.interview.service.serviceimp.TitleService;
import com.techvify.interview.validation.title.TitleIdExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins ="*", allowedHeaders ="*")
@RestController
@RequestMapping("/api/v1/title")
@Validated
public class TitleController {
    @Autowired
    private TitleService titleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Title>> getAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "4") int size) {
        return titleService.getAllByPage(page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Title> create(@RequestBody @Valid TitleRequest titleRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return titleService.create(titleRequest);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Title> update(@TitleIdExists @PathVariable("id") int id, @RequestBody @Valid TitleRequest titleRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return titleService.update(id,titleRequest);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> delete(@TitleIdExists @PathVariable("id") int id) {
        return titleService.delete(id);
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public Title getByName(@PathVariable("name") String name) {
        return titleService.find(name);
    }

}
