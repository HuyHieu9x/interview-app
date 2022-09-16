package com.techvify.interview.payload.response;

import com.techvify.interview.entity.InterviewSession;
import com.techvify.interview.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {
    private int id;
    private String note;
    private int score;
    private Question question;
    private InterviewSession interviewSession;
}
