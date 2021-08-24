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

import moussaoui.mohammed.technicalTest.advice.TrackExecutionTime;
import moussaoui.mohammed.technicalTest.advice.TrackRequest;
import moussaoui.mohammed.technicalTest.model.User;
import moussaoui.mohammed.technicalTest.service.UserService;

/**
 * UserController is the main controller of the app,
 * this controller exposes the /user end point
 * 
 * @author moussaoui
 *
 */
@RestController
@RequestMapping(path = "/api")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Manages the POST request on the /user endpoint, creates the user and returns
     * the errors if they occurs
     * 
     * @param user the user to be saved, it will first pass by a validation layer
     * @return ResponseEntity<User> the user saved if it pass the validation layer [Status = OK],
     * if not, it return the list of errors [Status BAD_REQUEST]
     * if the user already exists [Status CONFLICT]
     */
    @PostMapping(path = "/user")
    @TrackRequest
    @TrackExecutionTime
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        if (this.userService.userExists(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(user);
        } else {
            return ResponseEntity.ok(this.userService.addUser(user));
        }
    }

    /**
     * Manages the GET request on the /user/{username} endpoint, return the user if it exists
     * or NOT_FOUND error if not
     * 
     * @param username the username of the user to find
     * @return ResponseEntity<User> the user if exists, or NOT_FOUND error if not
     */
    @GetMapping(path = "/user/{username}")
    @TrackExecutionTime
    @TrackRequest
    public ResponseEntity<User> getUser(@PathVariable String username) {
    	User user = this.userService.getUserByUsername(username);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Manages the validation layer, it return a Map of errors.
     * 
     * @param ex
     * @return Map<String, String> list of errors
     */
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
