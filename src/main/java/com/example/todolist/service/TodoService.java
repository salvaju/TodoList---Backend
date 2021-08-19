package com.example.todolist.service;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo addTodo(Todo todo) {
        todoRepository.save(todo);
        return todo;
    }

    public Todo getTodoById(Integer id) {
        return todoRepository.findById((id)).orElse(null);
    }

    public Todo updateTodoInformation(Integer id, Todo updatedTodoInfo) {
        return Optional.of(getTodoById(id))
                .map(todo -> updateTodoInfo(todo, updatedTodoInfo))
                .map(this::addTodo)
                .orElse(null);
    }

    private Todo updateTodoInfo(Todo todo, Todo updatedTodoInfo) {

        if (updatedTodoInfo.getDone() != null) {
            todo.setDone(updatedTodoInfo.getDone());
        }

        return todo;
    }

}
