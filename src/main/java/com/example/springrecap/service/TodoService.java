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

    public Todo getTodoById(String id) throws TodoNotFoundException,  IllegalArgumentException{
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Todo Id must not be empty");
        }
        return todoRepo.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(
                 "Todo with id " + id + " not found"
        ));
    }

    public Todo createTodo(TodoDto todoDto) {
        Todo todo= new Todo(
                idService.generateId(),
                todoDto.description(),
                todoDto.status());
        todoRepo.save(todo);
        return todo;
    }

    public Todo updateTodo(Todo todo) throws TodoNotFoundException{

        if(todoRepo.existsById(todo.id())){
            todoRepo.save(todo);
            return todo;
        } else {
            throw new TodoNotFoundException("Todo with id"+ todo.id()+ "not found");
        }
    }


//    public Todo updateTodo(String id, TodoDto todoDto){
//
//        if(todoRepo.existsById(id)){
//            todoRepo.save(todoDto);
//            return todoDto;
//        } else {
//            throw new TodoNotFoundException("Todo with id"+ id+ "not found");
//
//        }
//
//    }

    public void deleteTodo(String id) throws TodoNotFoundException{
        if (todoRepo.existsById(id)){
            todoRepo.deleteById(id);
        } else {
            throw new TodoNotFoundException("Todo with id " + id + "not found");
        }
    }
}
