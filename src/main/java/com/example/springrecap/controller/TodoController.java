package com.example.springrecap.controller;

import com.example.springrecap.dto.TodoDto;
import com.example.springrecap.exceptions.TodoNotFoundException;
import com.example.springrecap.model.Todo;
import com.example.springrecap.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")

public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAllTodos(){
        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable String id) throws TodoNotFoundException {
        return todoService.getTodoById(id);
    }

    @PostMapping
    public Todo createTodo(@RequestBody TodoDto todoDto){
        return todoService.createTodo(todoDto);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody TodoDto todoDto) throws TodoNotFoundException{
        return todoService.updateTodo(id, todoDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable String id) throws TodoNotFoundException{
         todoService.deleteTodo(id);
    }


}


