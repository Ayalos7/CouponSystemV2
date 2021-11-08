package Ayal.ProjectSpring.controllers;

import Ayal.ProjectSpring.Beans.Category;

import Ayal.ProjectSpring.Beans.Company;
import Ayal.ProjectSpring.Beans.Coupon;
import Ayal.ProjectSpring.Beans.RestCompany;
import Ayal.ProjectSpring.CustomExceptions.CompanyApiResponseException;
import Ayal.ProjectSpring.CustomExceptions.LoginException;
import Ayal.ProjectSpring.Login.UserDetails;
import Ayal.ProjectSpring.Services.CompanyService;
import Ayal.ProjectSpring.Tokens.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("apiCompany")

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class CompanyApiProgram extends ClientController {
    private final CompanyService companyService;
    private final JwtUtils jwtUtils;


    @PostMapping("Company/addCoupon")

    public ResponseEntity<?> addCoupon(@RequestHeader (name ="Authorization") String jwtToken,@RequestBody Coupon coupon) throws LoginException,CompanyApiResponseException {
        if(jwtUtils.validateToken(jwtToken)) {
            try {
                companyService.addCoupon(coupon);
            }catch (CompanyApiResponseException companyApiResponseException){
                throw new CompanyApiResponseException(companyApiResponseException.getMessage());
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .headers(getHeaders(jwtToken))
                    .body(new RestCompany(newToken(jwtToken),null,null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }


    @PostMapping("Company/updateCoupon")

    public ResponseEntity<?> updateCoupon(@RequestHeader (name ="Authorization") String jwtToken,@RequestBody Coupon coupon) throws CompanyApiResponseException,LoginException {
        if(jwtUtils.validateToken(jwtToken)) {
            try {
                companyService.updateCoupon(coupon);
            } catch (CompanyApiResponseException companyApiResponseException) {
                throw new CompanyApiResponseException(companyApiResponseException.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestCompany(newToken(jwtToken),null,null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");

    }

    @PostMapping("Company/deleteCoupon")
    public ResponseEntity<?> deleteCoupon(@RequestHeader (name ="Authorization") String jwtToken,@RequestBody Coupon coupon) throws LoginException,CompanyApiResponseException {
        if(jwtUtils.validateToken(jwtToken)) {
            try {
                companyService.deleteCoupon(coupon.getId());
            }catch (CompanyApiResponseException companyApiResponseException){
                throw new CompanyApiResponseException(companyApiResponseException.getMessage());
            }
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestCompany(newToken(jwtToken),null,null));
        }
        throw new LoginException("jwtToken is wrong- user is not logged in");

    }

    @PostMapping("Company/getCompanyCoupons")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader (name ="Authorization") String jwtToken) throws LoginException {
        if(jwtUtils.validateToken(jwtToken)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestCompany(newToken(jwtToken),null,companyService.getAllCoupons()));

        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Company/getCompanyCouponsFromCategory")
    public ResponseEntity<?> getCompanyCouponsFromCategory(@RequestHeader (name ="Authorization") String jwtToken, @RequestBody Coupon coupon) throws LoginException{
        if(jwtUtils.validateToken(jwtToken)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestCompany(newToken(jwtToken),null,companyService.getAllCouponsFromCategory(coupon.getCategory())));

        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Company/getCompanyCouponsUpToPrice")
    public ResponseEntity<?> getCompanyCouponsUpToPrice(@RequestHeader (name ="Authorization") String jwtToken, @RequestBody Coupon coupon) throws LoginException{
        if(jwtUtils.validateToken(jwtToken)) {
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestCompany(newToken(jwtToken),null,companyService.getAllCouponsWithPriceLessThan(coupon.getPrice())));


        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }

    @PostMapping("Company/getCompanyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader (name ="Authorization") String jwtToken) throws LoginException {
        if(jwtUtils.validateToken(jwtToken)) {
            List<Company> companies = new ArrayList<>();
            companies.add(companyService.getCompanyDetails());
            return ResponseEntity.ok()
                    .headers(getHeaders(jwtToken))
                    .body(new RestCompany(newToken(jwtToken),companies,null));

        }
        throw new LoginException("jwtToken is wrong- user is not logged in");
    }


    @Override
    @PostMapping("Company/login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws LoginException {
        if (companyService.login(userDetails.getEmail(), userDetails.getPassword())){
            RestCompany answer = new RestCompany(
                    jwtUtils.generateToken(userDetails), null,null
            );
            return new ResponseEntity<>(answer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wrong email/password", HttpStatus.BAD_REQUEST);
        }
    }

    private HttpHeaders getHeaders(String token) {
        //create new userDetail and DI
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(jwtUtils.extractEmail(token));
        userDetails.setUserType((String) jwtUtils.extractAllClaims(token).get("userType"));
        //send ok with header of new token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", jwtUtils.generateToken(userDetails));
        return httpHeaders;
    }

    private String newToken(String token) {
        //create new userDetail and DI
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(jwtUtils.extractEmail(token));
        userDetails.setUserType((String) jwtUtils.extractAllClaims(token).get("userType"));
        //send ok with header of new token
        return jwtUtils.generateToken(userDetails);
    }
}
