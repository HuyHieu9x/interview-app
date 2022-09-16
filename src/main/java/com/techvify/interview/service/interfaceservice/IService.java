package com.techvify.interview.service.interfaceservice;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;

public interface IService<E> {
        ResponseEntity<?>create(E e);
        ResponseEntity<Map<String, Object>> getAllByPage(Pageable pageable);
        ResponseEntity<?> update(int id , E e);
        ResponseEntity<?> delete(int id);
        ResponseEntity<?> getByID(int id);

}
