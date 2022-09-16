package com.techvify.interview.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {

    @NotBlank(message = "The name mustn't be null value")
    private String content;
    private int levelId;
    private int languageId;
    private int frameworkId;
    @NotBlank(message = "The answer mustn't be null value")
    private String answer;

}

