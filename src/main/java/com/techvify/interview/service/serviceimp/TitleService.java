package com.techvify.interview.service.serviceimp;

import com.techvify.interview.entity.Title;
import com.techvify.interview.payload.request.TitleRequest;
import com.techvify.interview.repository.ITitleRepository;
import com.techvify.interview.service.interfaceservice.ITitleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TitleService implements ITitleService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ITitleRepository titleRepository;

    @Override
    public ResponseEntity<List<Title>> getAllByPage(int page, int size) {
        List<Title> titles = new ArrayList<Title>();
        List<Title> titleList = titleRepository.findAll((page - 1) * size, size);

        return new ResponseEntity<>(titleList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Title> create(TitleRequest titleRequest) {
        try {
            Title title = modelMapper.map(titleRequest, Title.class);
            return new ResponseEntity<>(titleRepository.save(title), HttpStatus.CREATED);
        } catch (Exception e) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(e.getMessage(), "CREATE FAIL");
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Title> update(int id, TitleRequest titleRequest) {
        try {
            Title title = titleRepository.findById(id).get();
            title.setName(titleRequest.getName());

            return new ResponseEntity<>(titleRepository.save(title),HttpStatus.OK);
        } catch (Exception e) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(e.getMessage(), "UPDATE FAIL");
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> delete(int id) {
        try {
            titleRepository.deleteById(id);
           return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Title find(String key) {
        return titleRepository.findByName(key);
    }

    @Override
    public boolean titleNameExists(String name) {
        return titleRepository.existsByName(name);
    }

    @Override
    public boolean titleIdExists(int id) {
        return titleRepository.existsById(id);
    }
}
