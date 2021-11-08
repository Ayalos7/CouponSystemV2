package Ayal.ProjectSpring.controllers;

import Ayal.ProjectSpring.Beans.Company;
import Ayal.ProjectSpring.Beans.Customer;
import Ayal.ProjectSpring.Beans.RestAdmin;
import Ayal.ProjectSpring.CustomExceptions.AdminApiExceptions.AdminApiResponseException;
import Ayal.ProjectSpring.CustomExceptions.LoginException;

import Ayal.ProjectSpring.Login.UserDetails;
import Ayal.ProjectSpring.Services.AdminService;

import Ayal.ProjectSpring.Tokens.JwtUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("apiAdmin")

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class AdminApiProgram extends ClientController {
    private final AdminService adminService;

    private final JwtUtils jwtUtils;


    @PostMapping("Admin/addCompany")
    private ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String jwtToken, @RequestBody Company company) throws AdminApiResponseException, LoginException {
        if (jwtUtils.validateToken(jwtToken)) {
            try {
                adminService.addCompany(company);
            } catch (AdminApiResponseException adminApiResponseException) {
                throw new AdminApiResponseException(adminApiResponseException.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestAdmin(newToken(jwtToken),null,null,null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");

    }

    @PostMapping("Admin/updateCompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String jwtToken, @RequestBody Company company) throws AdminApiResponseException, LoginException {
        if (jwtUtils.validateToken(jwtToken)) {
            try {
                adminService.updateCompany(company);
            } catch (AdminApiResponseException adminApiResponseException) {
                throw new AdminApiResponseException(adminApiResponseException.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestAdmin(newToken(jwtToken),null,null,null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Admin/deleteCompany")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String jwtToken, @RequestBody Company company) throws AdminApiResponseException, LoginException {
        if (jwtUtils.validateToken(jwtToken)) {
            try {
                adminService.deleteCompany(company.getId());
            } catch (AdminApiResponseException adminApiResponseException) {
                throw new AdminApiResponseException(adminApiResponseException.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestAdmin(newToken(jwtToken),null,null,null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Admin/getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name= "Authorization") String jwtToken) throws LoginException {
        if (jwtUtils.validateToken(jwtToken)) {
            return ResponseEntity.ok()
                    .body(new RestAdmin(newToken(jwtToken), adminService.getAllCompanies(), null, null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Admin/GetOneCompany")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String jwtToken, @RequestBody Company company) throws LoginException {
        if (jwtUtils.validateToken(jwtToken)) {
            List<Company> companies = new ArrayList<>();
            companies.add(adminService.getOneCompany(company.getId()));
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestAdmin(newToken(jwtToken),companies,null,null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");

    }

    @PostMapping("Admin/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String jwtToken, @RequestBody Customer customer) throws AdminApiResponseException, LoginException {
        if (jwtUtils.validateToken(jwtToken)) {
            try {
                adminService.addCustomer(customer);
            } catch (AdminApiResponseException adminApiResponseException) {
                throw new AdminApiResponseException(adminApiResponseException.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestAdmin(newToken(jwtToken),null,null,null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Admin/updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String jwtToken, @RequestBody Customer customer) throws AdminApiResponseException, LoginException {
        if (jwtUtils.validateToken(jwtToken)) {
            try {
                adminService.updateCustomer(customer);
            } catch (AdminApiResponseException adminApiResponseException) {
                throw new AdminApiResponseException(adminApiResponseException.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestAdmin(newToken(jwtToken),null,null,null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Admin/deleteCustomer")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String jwtToken, @RequestBody Customer customer) throws LoginException, AdminApiResponseException {
        if (jwtUtils.validateToken(jwtToken)) {
            try {
                adminService.deleteCustomer(customer.getId());
            } catch (AdminApiResponseException adminApiResponseException) {
                throw new AdminApiResponseException(adminApiResponseException.getMessage());
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .headers(getHeaders(jwtToken))
                    .body(new RestAdmin(newToken(jwtToken),null,null,null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");

    }


    @PostMapping("Admin/getAllCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String jwtToken) throws LoginException {
        if (jwtUtils.validateToken(jwtToken)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestAdmin(newToken(jwtToken), null, null, adminService.getAllCustomers()));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Admin/getOneCustomer")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String jwtToken, @RequestBody Customer customer) throws LoginException {
        if (jwtUtils.validateToken(jwtToken)) {
            List<Customer> customers = new ArrayList<>();
            customers.add(adminService.getOneCustomer(customer.getId()));
            return ResponseEntity.ok()
                    .body(new RestAdmin(newToken(jwtToken), null, null, customers));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @Override
    @PostMapping("Admin/login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws LoginException {
        if (adminService.login(userDetails.getEmail(), userDetails.getPassword())) {
            return ResponseEntity.ok()
                    .body(new RestAdmin(jwtUtils.generateToken(userDetails), null, null, null));
        } else {
            throw new LoginException("Wrong email/password");
        }
    }

    private HttpHeaders getHeaders(String token) {
        //create new userDetail and DI
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(jwtUtils.extractEmail(token));
        userDetails.setUserType((String) jwtUtils.extractAllClaims(token).get("userType"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", jwtUtils.generateToken(userDetails));
        return httpHeaders;
    }

    private String newToken(String token) {
        //create new userDetail and DI
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(jwtUtils.extractEmail(token));
        userDetails.setUserType((String) jwtUtils.extractAllClaims(token).get("userType"));
        return jwtUtils.generateToken(userDetails);
    }
}

