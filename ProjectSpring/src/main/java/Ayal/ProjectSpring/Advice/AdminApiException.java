package Ayal.ProjectSpring.Advice;

import Ayal.ProjectSpring.CustomExceptions.AdminApiExceptions.AdminApiResponseException;
import Ayal.ProjectSpring.CustomExceptions.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class AdminApiException {

    @ExceptionHandler( value= {AdminApiResponseException.class})
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public ErrorDetail AdminApiResponseException(Exception exception){
        return new ErrorDetail("Error: ",exception.getMessage());
    }

    @ExceptionHandler( value= {LoginException.class})
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public ErrorDetail LoginException(Exception exception){
        return new ErrorDetail("Error: ",exception.getMessage());
    }



}