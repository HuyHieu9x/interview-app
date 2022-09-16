package com.techvify.interview.config.exception;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

	@NonNull
	private String message;
	@NonNull
	private String detailMessage;

	private Object error;


	private Integer code;


	private String moreInformation;

	public ErrorResponse(String message,String detailMessage) {
		this.message = message;
		this.detailMessage = detailMessage;
	}

}
