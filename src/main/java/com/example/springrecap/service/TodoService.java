package com.example.springrecap.service;

import com.example.springrecap.dto.TodoDto;
import com.example.springrecap.exceptions.TodoNotFoundException;
import com.example.springrecap.model.Todo;
import com.example.springrecap.repository.TodoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepo todoRepo;
    private final IdService idService;

    public TodoService(TodoRepo todoRepo, IdService idService) {
        this.idService = idService;
        this.todoRepo = todoRepo;
    }

    public List<Todo> getAllTodos() {
        return todoRepo.findAll();
    }

    public Todo getTodoById(String id){
        return todoRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Todo with id " + id + " not found"
        ));
    }

    public Todo createTodo(TodoDto todoDto) {
        Todo todo= new Todo(idService.generateId(), todoDto.description(), todoDto.status());
        return todoRepo.save(todo);
    }

    public Todo updateTodo(String id, TodoDto todoDto){

        Todo existingTodo = todoRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Todo with id " + id + " not found"
        ));
        todoRepo.save(existingTodo);
        return existingTodo;
    }

    public void deleteTodo(String id){
        if (todoRepo.existsById(id)){
            todoRepo.deleteById(id);
        }
    }
}
