package com.techvify.interview.payload.request;

import com.techvify.interview.validation.title.TitleNameExists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TitleRequest {
    @NotBlank(message = "{title.name.not-blank}")
    @TitleNameExists
    private String name;

}
