package com.techvify.interview.validation.level;

import com.techvify.interview.service.interfaceservice.ILevelIntervieweeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LevelIDExistsValidator implements ConstraintValidator<LevelIDExists, Integer>{
	
	@Autowired
	private ILevelIntervieweeService service;
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(Integer id, ConstraintValidatorContext context) {
		
		if(StringUtils.isEmpty(id)) {
			return true;
		}
		
		return service.isLevelExistsByID(id);
	}
}
