package com.example.todolist.integrationTest;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class todoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @AfterEach
    void tearDown() {
        todoRepository.deleteAll();
    }

    @Test
    void should_return_all_todos_when_call_get_all_todos_api() throws Exception {
        //given
        final Todo todo = new Todo("Buy Iphone15Pro");
        todoRepository.save(todo);

        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("Buy Iphone15Pro"))
                .andExpect(jsonPath("$[0].done").value(false));
    }

    @Test
    void should_update_todo_when_call_update_todo_api() throws Exception {
        //given
        final Todo todo = new Todo("Buy Iphone15Pro");
        final Todo savedTodo = todoRepository.save(todo);

        String todoWithNewInfo = "{\n" +
                "        \"done\": true\n" +
                "}";

        //when

        //then
        Integer id = savedTodo.getId();

        mockMvc.perform(MockMvcRequestBuilders.put("/todos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoWithNewInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Buy Iphone15Pro"))
                .andExpect(jsonPath("$.done").value(true));

    }

    @Test
    void should_add_todo_when_call_add_todo_api() throws Exception {

        //given
        String todo = "{\n" +
                "        \"text\": \"Buy fruits\",\n" +
                "        \"done\": false\n" +
                "}";

        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("Buy fruits"))
                .andExpect(jsonPath("$.done").value(false));

    }

    @Test
    void should_delete_todo_when_call_delete_todo_api() throws Exception {
        //given
        final Todo todo = new Todo("Buy Iphone15Pro");
        todoRepository.save(todo);

        //when

        //then

        int id = todo.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/{id}", id))
                .andExpect(status().isOk());
    }

}
