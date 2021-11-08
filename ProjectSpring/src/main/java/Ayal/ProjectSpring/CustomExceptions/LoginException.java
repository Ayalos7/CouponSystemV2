package Ayal.ProjectSpring.CustomExceptions;

public class LoginException extends Exception{
    public LoginException(){

    }

    public LoginException(String message){
        super(message);
    }
}
