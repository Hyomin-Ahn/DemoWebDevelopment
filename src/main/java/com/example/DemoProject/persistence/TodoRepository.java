package com.example.DemoProject.persistence;

import com.example.DemoProject.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    List<TodoEntity> findByUserId(String userId);
    // ?1은 메소드의 매개변수 순서 위치
    @Query("select t from TodoEntity t where t.userId = ?1")
    TodoEntity findByUserIdQuery(String userId);
}
