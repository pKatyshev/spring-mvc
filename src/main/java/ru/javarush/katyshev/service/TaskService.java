package ru.javarush.katyshev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javarush.katyshev.model.Status;
import ru.javarush.katyshev.model.Task;
import ru.javarush.katyshev.model.TaskDTO;
import ru.javarush.katyshev.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll(int offset, int limit) {
        return taskRepository.findAll(limit, offset);
    }

    public void save(TaskDTO taskDTO) {
        Task task = new Task();
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        taskRepository.save(task);
    }
    public void update(int id, TaskDTO taskDTO) {
        taskRepository.update(new Task(id, taskDTO.getDescription(), taskDTO.getStatus()));
    }

    public void delete(int id) {
        taskRepository.delete(id);
    }

    public int getCount() {
        return taskRepository.getCount();
    }
}
