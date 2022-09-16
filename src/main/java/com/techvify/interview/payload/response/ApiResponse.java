package com.techvify.interview.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;

    public ApiResponse(boolean success, String message, HttpStatus ok) {
        this.setSuccess(success);
        this.setMessage(message);
    }

}
