package com.techvify.interview.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {
    @NotBlank
    private String username;
 
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}
