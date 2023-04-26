package com.example.DemoProject.service;

import com.example.DemoProject.model.TodoEntity;
import com.example.DemoProject.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService(){
        //Generate TodoEntity
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        //Save TodoEntity
        repository.save(entity);
        //Search TodoEntity
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    //create
    public List<TodoEntity> create(final TodoEntity entity){
        //validations
        validate(entity);

        repository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    //리펙토링
    private void validate(final TodoEntity entity){
        if(entity==null){
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId()==null){
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    //retrieve
    public List<TodoEntity> retrieve(final String userId){
        return repository.findByUserId(userId);
    }

    //update
    public List<TodoEntity> update(final TodoEntity entity){
        //1. validate
        validate(entity);

        //2. entity id로 TodoEntity가져옴
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo->{
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
        });

        return retrieve(entity.getUserId());
    }

    //delete
    public List<TodoEntity> delete(final TodoEntity entity){
        //1. validate
        validate(entity);

        try{
            //2. entity 삭제
            repository.delete(entity);
        }catch (Exception e){
            //3. exception 발생 : id, exception 로깅
            log.error("error deleting entity ", entity.getId(), e);

            //4. 컨트롤러로 exception 날림
            throw new RuntimeException("error deleting entity "+entity.getId());
        }
        //5. 새 Todo 리스트 가져와서 리턴
        return retrieve(entity.getUserId());
    }

}
