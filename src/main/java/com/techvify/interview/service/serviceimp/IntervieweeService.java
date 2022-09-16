package com.techvify.interview.service.serviceimp;

import com.techvify.interview.entity.Interviewee;
import com.techvify.interview.payload.request.IntervieweeFilterRequest;
import com.techvify.interview.payload.request.IntervieweeRequestForCreating;
import com.techvify.interview.repository.IIntervieweeRepository;
import com.techvify.interview.repository.ILevelIntervieweeRepository;
import com.techvify.interview.service.interfaceservice.IIntervieweeService;
import com.techvify.interview.specification.IntervieweeSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class IntervieweeService implements IIntervieweeService {
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private IIntervieweeRepository repository;
	
	@Autowired
	private ILevelIntervieweeRepository levelRepository;

	@Override
	public Page<Interviewee> getAllInterviewees(Pageable pageable, String search, IntervieweeFilterRequest filterLevelName) {
		Specification<Interviewee> where = IntervieweeSpecification.buildWhere(search,filterLevelName);
		return repository.findAll(where,pageable);
	}

	@Override
	public Interviewee getIntervieweeByID(int id) {
		return repository.findById(id).orElseThrow();
	}

	@Override
	public void createInterviewee(IntervieweeRequestForCreating form) {
				// commit id field
				TypeMap<IntervieweeRequestForCreating,Interviewee> typeMap = modelMapper.getTypeMap(IntervieweeRequestForCreating.class, Interviewee.class);
				if(typeMap == null) { // if not already added
					// skip field
					modelMapper.addMappings(new PropertyMap<IntervieweeRequestForCreating, Interviewee>() {

						@Override
						protected void configure() {
							skip(destination.getId());
						}
					});
				}
				
				// convert form to entity
				Interviewee interviewee = modelMapper.map(form, Interviewee.class);
				repository.save(interviewee);
	}

/*	@Override
	public void updateInterviewee(int id, UpdatingForIntervieweeRequest form) {
		LevelQuestion levelId = levelRepository.findById(form.getLevelId()).orElseThrow();
		
		Interviewee oldInterviewee = repository.findById(id).orElseThrow();
		Interviewee newInterviewee = modelMapper.map(form, Interviewee.class);
		
		oldInterviewee.setName(newInterviewee.getName());
		oldInterviewee.setEmail(newInterviewee.getEmail());
		oldInterviewee.setLevelInterviewee(levelId);
		
		repository.save(oldInterviewee);
	}*/

	@Override
	public void deleteInterviewee(int id) {
		repository.deleteById(id);
	}

	@Override
	public boolean isIntervieweeExistsByEmail(String email) {
		return repository.existsByEmail(email);
	}

	@Override
	public boolean isIntervieweeExistsByID(Integer id) {
		return repository.existsById(id);
	}

}
