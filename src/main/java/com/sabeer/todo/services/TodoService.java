package com.sabeer.todo.services;

import com.sabeer.todo.exceptions.ResourceNotFoundException;
import com.sabeer.todo.models.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Component
@Service
public class TodoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoService.class);

    List<Todo> todos = new ArrayList<>();

    // create todo method
    public Todo createTodo(Todo todo) {
        // create...
        // change the logic
        todos.add(todo);
        LOGGER.info("Todos {}", this.todos);
        return todo;
    }

    // get all todos method
    public List<Todo> getAllTodos() {
        return todos;
    }

    // get todo method
    public Todo getTodo(int todoId) {
//        Todo todo = todos.stream().filter(t -> todoId == t.getId()).findFirst().orElse(null);
        Todo todo = todos.stream()
                .filter(t -> todoId == t.getId())
                .findAny()
                .orElseThrow(()->new ResourceNotFoundException("Todo not found with given ID", HttpStatus.NOT_FOUND));
        LOGGER.info("TODO : {}", todo);
        return todo;
    }

    // update todo method
    public Todo updateTodo(int todoId, Todo todo) {
        /*
        Todo requiredTodo = todos.stream().filter(t -> todoId == t.getId()).findAny().get();
        requiredTodo.setId(todoId);
        requiredTodo.setTitle(todo.getTitle());
        requiredTodo.setContent(todo.getContent());
        requiredTodo.setStatus(todo.getStatus());
        return requiredTodo;
        */

        List<Todo> newUpdateList = todos.stream().map(t -> {
            if (t.getId() == todoId) {
                // perform update
                t.setTitle(todo.getTitle());
                t.setContent(todo.getContent());
                t.setStatus(todo.getStatus());
                return t;
            } else {
                return t;
            }
        }).collect(Collectors.toList());

        todos = newUpdateList;
        todo.setId(todoId);
        return todo;
    }

    // delete todo method
    public void deleteTodo(int todoId) {
//        todos.removeIf(todo->todo.getId() == todoId);

        LOGGER.info("DELETING TODO");
        List<Todo> newList = todos.stream().filter(t -> t.getId() != todoId).collect(Collectors.toList());
        todos = newList;
    }
}
