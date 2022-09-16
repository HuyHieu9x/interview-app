package com.techvify.interview.payload.response;

import com.techvify.interview.entity.Feedback;
import com.techvify.interview.entity.Framework;
import com.techvify.interview.entity.LevelInterviewee;
import com.techvify.interview.entity.ProgrammingLanguage;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class QuestionResponse {
    private int id;
    private String content;
    private String answer;
    private Framework framework;
    private LevelInterviewee level;
    private ProgrammingLanguage language;
    private Feedback feedback;

    public QuestionResponse(int id, String content, String answer, Framework framework, LevelInterviewee level, ProgrammingLanguage language, Feedback feedback) {
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.framework = framework;
        this.level = level;
        this.language = language;
        this.feedback = feedback;
    }
}


