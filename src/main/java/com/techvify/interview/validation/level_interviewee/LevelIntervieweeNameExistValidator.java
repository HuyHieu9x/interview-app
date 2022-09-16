package com.techvify.interview.validation.level_interviewee;

import com.techvify.interview.repository.ILevelIntervieweeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LevelIntervieweeNameExistValidator implements ConstraintValidator<LevelIntervieweeNameExist,String> {
    @Autowired
    ILevelIntervieweeRepository iLevelIntervieweeRepository;

    @Override
    public boolean isValid(String levelIntervieweeName, ConstraintValidatorContext constraintValidatorContext) {
        return !iLevelIntervieweeRepository.existsByName(levelIntervieweeName);
    }
}
