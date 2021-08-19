package moussaoui.mohammed.technicalTest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import moussaoui.mohammed.technicalTest.model.User;
import moussaoui.mohammed.technicalTest.service.UserService;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/user")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        if (this.userService.userExists(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(user);
        } else {
            return ResponseEntity.ok(this.userService.addUser(user));
        }
    }

    @GetMapping(path = "/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
    	User user = this.userService.getUserByUsername(username);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return errors;
    }
}
