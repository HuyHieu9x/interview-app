package com.techvify.interview.service.interfaceservice;

import com.techvify.interview.payload.request.LoginForm;
import com.techvify.interview.payload.request.SignUpForm;
import org.springframework.http.ResponseEntity;


public interface IAuthService {

	ResponseEntity<?> authenticateUser(LoginForm loginRequest);

	ResponseEntity<?> registerUser(SignUpForm signUpRequest);

}
