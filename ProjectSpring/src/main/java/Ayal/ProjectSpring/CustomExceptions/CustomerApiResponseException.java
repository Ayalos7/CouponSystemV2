package Ayal.ProjectSpring.CustomExceptions;

public class CustomerApiResponseException extends Exception{

    public CustomerApiResponseException(){

    }

    public CustomerApiResponseException(String message){
        super(message);
    }
}
