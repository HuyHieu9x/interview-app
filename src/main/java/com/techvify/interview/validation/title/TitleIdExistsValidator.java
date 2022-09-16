package com.techvify.interview.validation.title;

import com.techvify.interview.service.serviceimp.TitleService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TitleIdExistsValidator implements ConstraintValidator<TitleIdExists,Integer> {
    @Autowired
    private TitleService titleService;

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {
        return titleService.titleIdExists(id);
    }
}
