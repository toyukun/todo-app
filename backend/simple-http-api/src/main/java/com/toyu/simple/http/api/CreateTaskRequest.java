package com.toyu.simple.http.api;

import lombok.Value;

@Value
public class CreateTaskRequest {
    String title;
    String description;
}
