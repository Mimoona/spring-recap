package com.example.springrecap.controller;

import com.example.springrecap.model.Todo;
import com.example.springrecap.repository.TodoRepo;
import com.example.springrecap.todoEnum.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;    // we can send request to application from outside
    @Autowired
    private TodoRepo todoRepo;


    @Test
    void getAllTodos_shouldReturnAllTodos_whenCalled() throws Exception{
        //GIVEN

        //Todo todo = new Todo("1", "something", Status.OPEN);
        //WHEN &  THEN
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getTodoById_shouldReturnTodo1_whenCalledWithValidId() throws Exception {
        Todo todo = new Todo("1", "something", Status.OPEN);
        todoRepo.save(todo);

        mockMvc.perform(get("/api/todo/"+todo.id()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                            {
                                              "id": "1",
                                              "description": "something",
                                              "status": "OPEN"
                                            }
                                          """));
    }


    @Test
    void createTodo_shouldReturnTodo_whenCalledWithDto() throws Exception{
        mockMvc.perform(post("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                         {
                         "description": "something",
                         "status": "OPEN"
                         }
                         """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                    {
                                                    "description": "something",
                                                    "status": "OPEN"
                                                    }
                                                    """))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void updateTodo_shouldReturnTodo_whenCalled() throws Exception{
        Todo todo = new Todo("1", "something", Status.OPEN);
        todoRepo.save(todo);

        mockMvc.perform(put("/api/todo/"+todo.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                         {
                         "id": "1",
                         "description": "something",
                         "status": "OPEN"
                         }
                         """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                    {
                                                    "id": "1",
                                                    "description": "something",
                                                    "status": "OPEN"
                                                    }
                                                    """))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void deleteTodo_shouldReturnTodo_whenCalled() throws Exception{
        Todo todo = new Todo("1", "something", Status.OPEN);
        todoRepo.save(todo);

        mockMvc.perform(delete("/api/todo/"+todo.id()))
                .andExpect(status().isOk());
    }

}