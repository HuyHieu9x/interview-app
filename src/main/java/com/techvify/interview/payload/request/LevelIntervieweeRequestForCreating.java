package com.techvify.interview.payload.request;

import com.techvify.interview.entity.LevelQuestion;
import com.techvify.interview.validation.level_interviewee.LevelIntervieweeNameExist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LevelIntervieweeRequestForCreating {
	@NotBlank(message = "{Level.createLevel.form.name.NotBlank}")
	@Length(max = 20, message = "{Level.createLevel.form.name.Length}")
    @LevelIntervieweeNameExist
    private String name;

    private List<LevelQuestion> levelQuestionList;
}
