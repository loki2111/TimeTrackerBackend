package com.example.timetracker.repository;

import com.example.timetracker.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOrderByLoginTimeAsc();
    List<Task> findAllByEmail(String email);
}
