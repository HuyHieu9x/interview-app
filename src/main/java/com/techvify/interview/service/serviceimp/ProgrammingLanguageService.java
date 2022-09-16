package com.techvify.interview.service.serviceimp;

import com.techvify.interview.entity.ProgrammingLanguage;
import com.techvify.interview.repository.IProgrammingLanguageRepository;
import com.techvify.interview.service.interfaceservice.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProgrammingLanguageService implements IService<ProgrammingLanguage> {
    @Autowired
    private IProgrammingLanguageRepository programmingLanguageRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllByPage(Pageable pageable) {
            List<ProgrammingLanguage> programmingLanguages;
            Page<ProgrammingLanguage> pageInts = programmingLanguageRepository.findAll(pageable);
            programmingLanguages = pageInts.getContent();
            Map<String,Object> response = new HashMap<>();
            response.put("content",programmingLanguages);
            response.put("currentPage", pageInts.getNumber() + 1);
            response.put("totalItems", pageInts.getTotalElements());
            response.put("totalPages", pageInts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> create(ProgrammingLanguage programmingLanguage) {
        programmingLanguageRepository.save(programmingLanguage);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<?> update(int id, ProgrammingLanguage programmingLanguage) {
        ProgrammingLanguage newLanguage = programmingLanguageRepository.findById(id).orElseThrow();
        newLanguage.setName(programmingLanguage.getName());
        programmingLanguageRepository.save(newLanguage);
        return ResponseEntity.ok().body("Update SuccessFully");
    }
    @Override
    public ResponseEntity<?> delete(int id) {
        programmingLanguageRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getByID(int id) {
        return null;
    }


}
