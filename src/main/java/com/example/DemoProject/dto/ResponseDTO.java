package com.example.DemoProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
//오류해결..T 누락
public class ResponseDTO<T> {
    private String error;
    private List<T> data;
}
