package com.techvify.interview.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {

    @Size(min = 3, max = 50)
    private String username;

    @Size(max = 60)
    @Email
    private String email;

    private Set<String> roles;

    @Size(min = 6, max = 40)
    private String password;

    private String name;

    private int age;

    private String address;
}
