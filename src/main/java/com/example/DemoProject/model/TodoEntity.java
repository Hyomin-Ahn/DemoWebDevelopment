package com.example.DemoProject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoEntity {

    private String id; //object's id
    private String userId; //user id to generate this object
    private String title; // Todo title ex. exercise
    private boolean done; // true- if todo completed

}
