package com.api.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.todo.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

    List<Task> findByUserId(Long id);

}
