package com.tinie.Services.responses;

import lombok.Data;

@Data
public class UnauthorisedResponse {

    private String status;
    private int code;
    private long timestamp;
    private String message;
}
