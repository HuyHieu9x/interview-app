package com.techvify.interview.payload.request;

import com.techvify.interview.validation.interviewee.IntervieweeEmailNotExists;
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
public class IntervieweeRequestForCreating {
	
	@NotBlank(message = "{Interviewee.createInterviewee.form.name.NotBlank}")
	@Length(max = 50, message = "{Interviewee.createInterviewee.form.name.Length}")
	private String name;
	
	@NotBlank(message = "{Interviewee.createInterviewee.form.email.NotBlank}")
	@Email(message = "{Interviewee.createInterviewee.form.email.Email}")
	@IntervieweeEmailNotExists
	private String email;
	
	@Positive(message = "{Interviewee.createInterviewee.form.levelId.Positive}")
	private int levelId;

	@Positive(message = "{Interviewee.createInterviewee.form.frameworkId.Positive}")
	private int frameworkId;

	@Positive(message = "{Interviewee.createInterviewee.form.languageId.Positive}")
	private int languageId;
}
