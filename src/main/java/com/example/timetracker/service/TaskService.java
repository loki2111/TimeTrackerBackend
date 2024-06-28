package com.example.timetracker.service;

import com.example.timetracker.entity.Task;
import com.example.timetracker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAllByOrderByLoginTimeAsc();
    }

    public List<Task> getAllTaskByEmail(String email){
        return  taskRepository.findAllByEmail(email);
    }
}
