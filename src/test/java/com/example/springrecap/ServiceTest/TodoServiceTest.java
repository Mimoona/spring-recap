package com.example.springrecap.ServiceTest;

import com.example.springrecap.dto.TodoDto;
import com.example.springrecap.exceptions.TodoNotFoundException;
import com.example.springrecap.model.Todo;
import com.example.springrecap.repository.TodoRepo;
import com.example.springrecap.service.IdService;
import com.example.springrecap.service.TodoService;
import com.example.springrecap.todoEnum.Status;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

public class TodoServiceTest {
    TodoRepo mockRepo = Mockito.mock(TodoRepo.class);
    IdService mockIdService = Mockito.mock(IdService.class);

    @Test
    void getAllTodos_shouldReturnAllTodos() {
        //Given
        TodoService todoService = new TodoService(mockRepo, mockIdService);
        Todo todo = new Todo("1", "description", Status.OPEN);
        Mockito.when(mockRepo.findAll()).thenReturn(List.of(todo));

        //WHEN
        todoService.getAllTodos();
    }

    @Test
    void getTodoById_shouldReturnTodo_whenCalledWithValidId() throws TodoNotFoundException {
        //Given
        TodoService todoService = new TodoService(mockRepo, mockIdService);
        Todo expected = new Todo("1", "description", Status.OPEN);
        Mockito.when(mockRepo.findById("1")).thenReturn(Optional.of(expected));

        // WHEN
        Todo actual = todoService.getTodoById("1");

        // THEN
        assertEquals(expected,actual);

    }


    @Test
    void getTodoById_shouldThrowException_whenCalledWithInvalidId(){
        //Given
        TodoService todoService = new TodoService(mockRepo, mockIdService);
        Mockito.when(mockRepo.findById("1")).thenReturn(Optional.empty());

        //WHEN
        try{
            todoService.getTodoById("1");
            fail();

        }catch (TodoNotFoundException e){
            //THEN
            assertTrue(true);
        }

    }

    @Test
    void getTodoById_shouldThrowException_whenCalledWithEmptyId(){
        //Given
        TodoService todoService = new TodoService(mockRepo, mockIdService);
        Mockito.when(mockRepo.findById("")).thenReturn(Optional.empty());

        //WHEN
        assertThrows(TodoNotFoundException.class, () -> todoService.getTodoById("1"));

    }



}
