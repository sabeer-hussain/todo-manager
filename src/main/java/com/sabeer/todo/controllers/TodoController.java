package com.sabeer.todo.controllers;

import com.sabeer.todo.models.Todo;
import com.sabeer.todo.services.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoService todoService;

    Random random = new Random();

    // create
    @PostMapping
    public ResponseEntity<Todo> createTodoHandler(@RequestBody Todo todo) {
        // for NullPointerException
//        String str = null;
//        LOGGER.info("{}", str.length());

        // for NumberFormatException
//        Integer.parseInt("l4fsakfh");

        // create todo
        int id = random.nextInt(9999999);
        todo.setId(id);
        // create date with system default current date
        Date currentDate = new Date();
        LOGGER.info("current date: {}", currentDate);
        LOGGER.info("Todo date: {}", todo.getToDoDate());
        todo.setAddedDate(currentDate);
        LOGGER.info("Create Todo");
        // call service to create todo
        Todo createdTodo = todoService.createTodo(todo);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    // get all todo method
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodosHandler() {
        // get all todos
        List<Todo> allTodos = todoService.getAllTodos();
        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }

    // get single todo
    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getSingleTodoHandler(@PathVariable int todoId) {
        // get single todo
        Todo todo = todoService.getTodo(todoId);
        return ResponseEntity.ok(todo);
    }

    // update todo
    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodoHandler(@RequestBody Todo todoWithNewDetails, @PathVariable int todoId) {
        // update todo
        Todo updatedTodo = todoService.updateTodo(todoId, todoWithNewDetails);
        return ResponseEntity.ok(updatedTodo);
    }

    // delete todo
    @DeleteMapping("/{todoId}")
    public ResponseEntity<String> deleteTodoHandler(@PathVariable int todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo Successfully deleted");
    }

    // null pointer exception handler
//    @ExceptionHandler(NullPointerException.class)
//    public ResponseEntity<String> nullPointerExceptionHandler(NullPointerException ex) {
//        LOGGER.info("{}", ex.getMessage());
//        LOGGER.info("Null pointer exception generated");
//        return new ResponseEntity<>("Null pointer exception generated " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    // number format exception handler
//    @ExceptionHandler(NumberFormatException.class)
//    public ResponseEntity<String> numberFormatExceptionHandler(NumberFormatException ex) {
//        LOGGER.info("{}", ex.getMessage());
//        LOGGER.info("Number format exception generated");
//        return new ResponseEntity<>("Number format exception generated " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    // multiple exception handler
//    @ExceptionHandler(value = {NullPointerException.class, NumberFormatException.class})
//    public ResponseEntity<String> multipleExceptionHandler(RuntimeException ex) {
//        LOGGER.info("{}", ex.getMessage());
//        LOGGER.info("{} generated", ex.getClass().getSimpleName());
//        return new ResponseEntity<>(ex.getClass().getSimpleName() + " generated " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
