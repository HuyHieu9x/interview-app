package com.techvify.interview.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  UpdatingForQuestionRequest {

    private int id;
    @NotBlank(message = "The name mustn't be null value")
    private String content;

    @NotBlank(message = "The answer mustn't be null value")
    private String answer;

    private int levelId;

    private int languageId;

    private int frameworkId;

    @CreationTimestamp
    private LocalDateTime updatedAt;

}
