package com.toyu.simple.http.api;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskEntity createTask(@RequestBody CreateTaskRequest request) {
        val entity = TaskEntity.builder()
                               .title(request.getTitle())
                               .description(request.getDescription())
                               .build();
        return repository.save(entity);
    }

    @GetMapping("/{id}")
    public TaskEntity readTask(@PathVariable("id") Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data Not Found"));
    }

    @PutMapping("/{id}")
    public TaskEntity updateTask(@PathVariable("id") Long id,
                                 @RequestBody CreateTaskRequest request) {
        val entity = repository.findById(id)
                               .map(it -> {
                                   it.setTitle(request.getTitle());
                                   it.setTitle(request.getDescription());
                                   return it;
                               })
                               .orElse(TaskEntity.builder()
                                                 .title(request.getTitle())
                                                 .description(request.getDescription())
                                                 .build());
        return repository.save(entity);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
}
