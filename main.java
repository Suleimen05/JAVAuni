package com.example.demo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

class UserDto {
    @NotBlank(message = "Имя обязательно")
    public String name;

    @Email(message = "Неверный формат email")
    public String email;

    @Min(value = 18, message = "Минимум 18 лет")
    public int age;
}

@RestController
@RequestMapping("/api")
class UserController {
    @PostMapping("/test")
    public String test(@Valid @RequestBody UserDto user) {
        return "OK!";
    }
}

@RestControllerAdvice
class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handle(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> 
            errors.put(err.getField(), err.getDefaultMessage()));
        return errors;
    }
}
