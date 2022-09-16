package com.techvify.interview.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterviewSessionRequest {
    private int intervieweeId;
    private Set<Integer> userId = new HashSet<>();
    private LocalDateTime date;
    private String status;
}
