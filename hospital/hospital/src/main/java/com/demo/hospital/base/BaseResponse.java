package com.demo.hospital.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse<T extends Object> {

    public int status_code;
    public String message;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "data")
    public T t;

}

