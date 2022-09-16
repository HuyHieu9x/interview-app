package com.techvify.interview.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatingLevelRequest {
	@NotBlank(message = "{Level.createLevel.form.name.NotBlank}")
	@Length(max = 20, message = "{Level.createLevel.form.name.Length}")
	private String name;
}
