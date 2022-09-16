package com.techvify.interview.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Filter {
    private String content;
    private String language ;
    private String level;
    private String framework;
}
