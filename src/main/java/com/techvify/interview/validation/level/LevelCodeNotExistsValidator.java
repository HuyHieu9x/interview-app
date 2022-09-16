package com.techvify.interview.validation.level;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.techvify.interview.service.interfaceservice.ILevelIntervieweeService;

public class LevelCodeNotExistsValidator implements ConstraintValidator<LevelCodeNotExists, String>{
	
	@Autowired
	private ILevelIntervieweeService service;
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(String code, ConstraintValidatorContext context) {
		
		if(StringUtils.isEmpty(code)) {
			return true;
		}
		
		/*return !service.isLevelExistsByCode(code);*/
		return false;
	}
}
