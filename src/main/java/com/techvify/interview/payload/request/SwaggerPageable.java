package com.techvify.interview.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwaggerPageable {

    private Integer size;

    private Integer page;

    private String sort;

}
