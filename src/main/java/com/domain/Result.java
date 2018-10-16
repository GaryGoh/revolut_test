package com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    private Boolean success;
    private String code;
    private String message;
    public Result() {
        // Needed by Jackson deserialization
    }

    public Result(Boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
        //TODO: need to build up the dictionary of <code, message> map.
    }

    @JsonProperty
    public String getResult() {
        return "success - " + success + ", with code - " + code + ". " + "Message: " + message;
    }

    @JsonProperty
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty
    public String getCode() {
        return code;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }
}
