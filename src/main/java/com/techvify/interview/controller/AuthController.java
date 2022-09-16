package com.techvify.interview.controller;

import com.techvify.interview.payload.request.LoginForm;
import com.techvify.interview.payload.response.UserIdentityAvailability;
import com.techvify.interview.repository.UserRepository;
import com.techvify.interview.service.interfaceservice.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private IAuthService userService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        return new ResponseEntity<>(userService.authenticateUser(loginRequest),HttpStatus.OK);
    }

    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {

        Boolean isAvailable = userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);

    }

}
