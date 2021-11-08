package Ayal.ProjectSpring.Advice;


import Ayal.ProjectSpring.CustomExceptions.CustomerApiResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CustomerApiException {
    @ExceptionHandler(value = {CustomerApiResponseException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)

    public ErrorDetail CustomerApiResponseException(Exception exception){
        return new ErrorDetail("error", exception.getMessage());
    }
}
