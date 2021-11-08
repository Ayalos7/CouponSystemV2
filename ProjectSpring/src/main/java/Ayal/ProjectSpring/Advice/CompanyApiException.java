package Ayal.ProjectSpring.Advice;


import Ayal.ProjectSpring.CustomExceptions.CompanyApiResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CompanyApiException {
    @ExceptionHandler(value = {CompanyApiResponseException.class})
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)

    public ErrorDetail CompanyApiResponseException(Exception exception){
        return new ErrorDetail("error", exception.getMessage());
    }

}
