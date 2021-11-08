package Ayal.ProjectSpring.CustomExceptions.AdminApiExceptions;

public class AdminApiResponseException extends Exception{

    public AdminApiResponseException(){

    }

    public AdminApiResponseException(String message){
        super(message);
    }
}
