package com.techvify.interview.controller;

import com.techvify.interview.entity.User;
import com.techvify.interview.payload.request.FilterUser;
import com.techvify.interview.payload.request.SignUpForm;
import com.techvify.interview.payload.request.UserUpdateAdmin;
import com.techvify.interview.payload.response.UserProfile;
import com.techvify.interview.service.interfaceservice.IAuthService;
import com.techvify.interview.service.interfaceservice.IUserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAuthService authService;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        return ResponseEntity.ok(authService.registerUser(signUpRequest));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser(Pageable pageable,
                                                @RequestParam(value = "search", required = false)
                                                String search, FilterUser userFilter) {

        Page<User> entities = userService.getFindAll(pageable, search, userFilter);

        // convert entities --> dtos
        List<UserProfile> dtos = modelMapper.map(
                entities.getContent(),
                new TypeToken<List<UserProfile>>() {
                }.getType());

        Page<UserProfile> dtoPages = new PageImpl<>(dtos, pageable, entities.getTotalElements());
        return new ResponseEntity<>(dtoPages, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("User is in a interview session");
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUserRole(@PathVariable(name = "id") int id, @RequestBody UserUpdateAdmin userUpdateAdmin){
        userService.UpdateRole(id, userUpdateAdmin);
        return new ResponseEntity<>("Update Successfully",HttpStatus.OK);
    }

}
