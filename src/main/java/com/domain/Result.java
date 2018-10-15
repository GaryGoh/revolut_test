package com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    private Boolean success;
    private String code;

    public Result(){
        // Needed by Jackson deserialization
    }

    public Result(Boolean success, String code) {
        this.success = success;
        this.code = code;
    }

    @JsonProperty
    public String getResult() {
        return "success - " + success + ", with code - " + code;
    }

    @JsonProperty
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty
    public String getCode() {
        return code;
    }
}
