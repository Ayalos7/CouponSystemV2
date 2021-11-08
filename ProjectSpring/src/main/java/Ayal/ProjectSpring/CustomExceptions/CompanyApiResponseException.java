package Ayal.ProjectSpring.CustomExceptions;

public class CompanyApiResponseException extends Exception{

    public CompanyApiResponseException(){

    }

    public CompanyApiResponseException(String message){
        super(message);
    }

}
