package Ayal.ProjectSpring.controllers;

import Ayal.ProjectSpring.CustomExceptions.LoginException;
import Ayal.ProjectSpring.Login.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class ClientController {

    public abstract ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws LoginException;


}
