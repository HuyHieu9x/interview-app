package com.techvify.interview.validation.title;

import com.techvify.interview.service.serviceimp.TitleService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TitleNameExistsValidator implements ConstraintValidator<TitleNameExists,String> {
    @Autowired
    private TitleService titleService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return !titleService.titleNameExists(name);
    }
}
