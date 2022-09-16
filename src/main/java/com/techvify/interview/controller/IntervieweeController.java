package com.techvify.interview.controller;

import com.techvify.interview.entity.Interviewee;
import com.techvify.interview.payload.request.IntervieweeFilterRequest;
import com.techvify.interview.payload.request.IntervieweeRequestForCreating;
import com.techvify.interview.payload.response.IntervieweeResponse;
import com.techvify.interview.service.interfaceservice.IIntervieweeService;
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
@RequestMapping(value = "api/v1/interviewees")
@Validated
public class IntervieweeController {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private IIntervieweeService service;

	@GetMapping()
	@PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
	public ResponseEntity<?> getAllInterviewees(Pageable pageable,
												@RequestParam(value = "search",required = false)
			String search, IntervieweeFilterRequest filterLevelName) {
		
		Page<Interviewee> entities = service.getAllInterviewees(pageable,search,filterLevelName);
		
		// convert entities --> dto
		List<IntervieweeResponse> dto = modelMapper.map(
				entities.getContent(),
				new TypeToken<List<IntervieweeResponse>>(){}.getType());
		
		Page<IntervieweeResponse> dtoPages = new PageImpl<>(dto, pageable, entities.getTotalElements());
		return new ResponseEntity<>(dtoPages, HttpStatus.OK);
		
	}


	@PostMapping()
	@PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
	public ResponseEntity<?> createInterviewee(@RequestBody @Valid IntervieweeRequestForCreating form) {
		service.createInterviewee(form);
		return new ResponseEntity<>("Create successfully", HttpStatus.OK);
	}
	
/*
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
	public ResponseEntity<?>  updateInterviewee(@IntervieweeIDExists @PathVariable(name = "id") int id,@Valid @RequestBody UpdatingForIntervieweeRequest form) {
		service.updateInterviewee(id,form);
		return new ResponseEntity<>("Update successfully", HttpStatus.OK);
	}
*/

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('HR')")
	public ResponseEntity<?>deleteInterviewee(@PathVariable(name = "id") int id) {
		service.deleteInterviewee(id);
		return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
	}
}
