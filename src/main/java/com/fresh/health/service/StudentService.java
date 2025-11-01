package com.fresh.health.service;

import com.fresh.health.request.StudentRequest;
import org.springframework.web.bind.annotation.RequestBody;

public interface StudentService {

    public void addStudent(StudentRequest name);

}
