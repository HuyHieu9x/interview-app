package com.techvify.interview.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatingForIntervieweeRequest {
	
	private int id;

	@NotBlank(message = "{Interviewee.createInterviewee.form.name.NotBlank}")
	@Length(max = 50, message = "{Interviewee.createInterviewee.form.name.Length}")
	private String name;

	@NotBlank(message = "{Interviewee.createInterviewee.form.email.NotBlank}")
	@Email(message = "{Interviewee.createInterviewee.form.email.Email}")
	private String email;

	@Positive(message = "{Interviewee.createInterviewee.form.levelId.Positive}")
	private int levelId;

	private int frameworkId;

	private int languageId;

}
