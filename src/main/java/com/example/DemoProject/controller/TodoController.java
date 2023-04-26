package com.example.DemoProject.controller;

import com.example.DemoProject.dto.ResponseDTO;
import com.example.DemoProject.dto.TodoDTO;
import com.example.DemoProject.model.TodoEntity;
import com.example.DemoProject.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;
    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService(); // 테스트 서비스 사용
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    //create
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try{
            String temporaryUserId = "temporary-user"; //temporary user id

            //1. TodoEntity로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);

            //2. id를 null로 초기화 -> 생성 당시에는 id 없어야
            entity.setId(null);

            //3. 임시 유저 아이디 생성
            entity.setUserId(temporaryUserId);

            //4. 서비스를 이용해 Todo 엔티티 생성
            List<TodoEntity> entities = service.create(entity);

            //5. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            //6. 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            //7. ResponseDTO를 리턴
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            //8. 예외 발생하는 경우
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    //post
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(){
        String temporaryUserId = "temporary-user";

        //1. 서비스 메소드의 retrieve 메소드 사용해 Todo 리스트 가져옴
        List<TodoEntity> entities = service.retrieve(temporaryUserId);
        //2. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 반환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        //6. 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        //7. ResponseDTO를 리턴
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
        String temporaryUserId = "temporary-user";

        //1. dto -> entity
        TodoEntity entity = TodoDTO.toEntity(dto);

        //2. id를 temporaryUserID로 초기화
        entity.setUserId(temporaryUserId);

        //3. entity 업데이트
        List<TodoEntity> entities = service.update(entity);

        //4. 자바 스트림을 이용해 리턴된 엔티티 리스트 TodoDTO 리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        //5. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        //6. ResponseDTO 리턴
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
        try{
            String temporaryUserId = "temporary-user";

            //1. TodoEntity 변환
            TodoEntity entity = TodoDTO.toEntity(dto);

            //2. 임시 유저 아이디 설정
            entity.setUserId(temporaryUserId);

            //3. 서비스로 entity 삭제
            List<TodoEntity> entities = service.delete(entity);

            //4. 자바 스트림 이용, 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            //5. 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            //6. ResponseDTO 리턴
            return ResponseEntity.ok().body(response);
        }catch(Exception e){
            //8. 예외 발생 시 dto 대신 error에 메시지 넣어서 리턴
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

}
