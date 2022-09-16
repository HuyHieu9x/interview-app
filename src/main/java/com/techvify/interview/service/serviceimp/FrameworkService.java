package com.techvify.interview.service.serviceimp;

import com.techvify.interview.entity.Framework;
import com.techvify.interview.entity.ProgrammingLanguage;
import com.techvify.interview.payload.request.FrameworkRequest;
import com.techvify.interview.repository.IFrameworkRepository;
import com.techvify.interview.service.interfaceservice.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FrameworkService implements IService<FrameworkRequest> {
    @Autowired
    private IFrameworkRepository frameworkRepository;

    @Override
    public ResponseEntity<?> create(FrameworkRequest frameworkRequest) {
        Framework framework = new Framework();
        framework.setProgrammingLanguage(new ProgrammingLanguage() {{
            setId(frameworkRequest.getProgrammingLanguage());
        }});
        framework.setName(frameworkRequest.getName());
        frameworkRepository.save(framework);
        return ResponseEntity.ok().body("Create successFully");
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllByPage(Pageable pageable) {
        try {
            List<Framework> frameworks ;
            Page<Framework> pageInts = frameworkRepository.findAll(pageable);
            frameworks = pageInts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("content", frameworks);
            response.put("currentPage", pageInts.getNumber() + 1);
            response.put("totalItems", pageInts.getTotalElements());
            response.put("totalPages", pageInts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> update(int id, FrameworkRequest frameworkRequest) {
        Framework framework = frameworkRepository.findById(id).orElseThrow();
        framework.setProgrammingLanguage(new ProgrammingLanguage() {{
            setId(frameworkRequest.getProgrammingLanguage());
        }});
        framework.setName(frameworkRequest.getName());
        frameworkRepository.save(framework);
        return ResponseEntity.ok().body("update successFully");
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        frameworkRepository.deleteById(id);
        return ResponseEntity.ok().body("delete successFully");
    }

    @Override
    public ResponseEntity<?> getByID(int id) {
        frameworkRepository.findById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
