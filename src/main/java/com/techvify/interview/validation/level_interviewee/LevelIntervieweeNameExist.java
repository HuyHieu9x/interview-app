package com.techvify.interview.validation.level_interviewee;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { LevelIntervieweeNameExistValidator.class })
@Repeatable(LevelIntervieweeNameExist.List.class)
public @interface LevelIntervieweeNameExist {
    String message() default "{level.interviewee.name.unique}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        LevelIntervieweeNameExist[] value();
    }
}
