package com.techvify.interview.validation.interviewee;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.techvify.interview.service.interfaceservice.IIntervieweeService;

public class IntervieweeEmailNotExistsValidator implements ConstraintValidator<IntervieweeEmailNotExists, String>{
	
	@Autowired
	private IIntervieweeService service;
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		
		if(StringUtils.isEmpty(email)) {
			return true;
		}
		
		return !service.isIntervieweeExistsByEmail(email);
	}
}
