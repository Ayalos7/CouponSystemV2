package Ayal.ProjectSpring.controllers;

import Ayal.ProjectSpring.Beans.Category;
import Ayal.ProjectSpring.Beans.Coupon;
import Ayal.ProjectSpring.Beans.Customer;
import Ayal.ProjectSpring.Beans.RestCustomer;
import Ayal.ProjectSpring.CustomExceptions.LoginException;
import Ayal.ProjectSpring.Login.UserDetails;
import Ayal.ProjectSpring.Services.CustomerService;
import Ayal.ProjectSpring.Tokens.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("apiCustomer")

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class CustomerApiProgram extends ClientController {
    private final CustomerService customerService;
    private final JwtUtils jwtUtils;


    @PostMapping("Customer/purchaseCoupon")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader (name ="Authorization") String jwtToken, @RequestBody Coupon coupon) throws LoginException{
        if(jwtUtils.validateToken(jwtToken)) {
            customerService.purchaseCoupon(coupon);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .headers(getHeaders(jwtToken))
                    .body(new RestCustomer(newToken(jwtToken),null,null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Customer/getCustomerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader (name ="Authorization") String jwtToken) throws LoginException{
        if(jwtUtils.validateToken(jwtToken)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestCustomer(newToken(jwtToken),customerService.getAllPurchasedCoupons(),null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Customer/getCustomerCouponsFromCategory")
    public ResponseEntity<?> getCustomerCouponsFromCategory(@RequestHeader (name ="Authorization") String jwtToken,@RequestBody Coupon coupon) throws LoginException{
        if(jwtUtils.validateToken(jwtToken)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestCustomer(newToken(jwtToken),customerService.getAllPurchasedCouponsFromCategory(coupon.getCategory()),null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Customer/getCustomerCouponsUpToPrice")
    public ResponseEntity<?> getCustomerCouponsUpToPrice(@RequestHeader (name ="Authorization") String jwtToken, @RequestBody Coupon coupon) throws LoginException {
        if(jwtUtils.validateToken(jwtToken)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestCustomer(newToken(jwtToken),customerService.getAllPurchasedCouponsWithPriceLessThan(coupon.getPrice()),null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Customer/getCustomerDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader (name ="Authorization") String jwtToken) throws LoginException {
        if(jwtUtils.validateToken(jwtToken)) {
            List<Customer> customers = new ArrayList<>();
            customers.add(customerService.getCustomerDetails());
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestCustomer(newToken(jwtToken),null,customers));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }


    @Override
    @PostMapping("Customer/login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws LoginException {
        if (customerService.login(userDetails.getEmail(), userDetails.getPassword())) {
            return ResponseEntity.ok()
            .body(new RestCustomer(jwtUtils.generateToken(userDetails),null,null));
        }
        throw new LoginException("Wrong email/password");
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
