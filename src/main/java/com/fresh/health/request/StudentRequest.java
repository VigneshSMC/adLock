package com.fresh.health.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentRequest {
    private String firstName;
    private String secondName;
    private Integer mark;
    private Integer grade;
    private String studentId;
}
