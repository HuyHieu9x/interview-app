package com.techvify.interview.validation.interviewee;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.techvify.interview.service.interfaceservice.IIntervieweeService;

public class IntervieweeIDExistsValidator implements ConstraintValidator<IntervieweeIDExists, Integer>{
	
	@Autowired
	private IIntervieweeService service;
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(Integer id, ConstraintValidatorContext context) {
		
		if(StringUtils.isEmpty(id)) {
			return true;
		}
		
		return service.isIntervieweeExistsByID(id);
	}
}
