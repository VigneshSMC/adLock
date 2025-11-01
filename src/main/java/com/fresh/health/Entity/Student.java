package com.fresh.health.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "students")
@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String secondName;
    private int mark;
    private int grade;
}
