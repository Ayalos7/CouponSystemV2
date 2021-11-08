package Ayal.ProjectSpring.Tokens;

import Ayal.ProjectSpring.Login.UserDetails;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service

public class JwtUtils {
    //type of encryption -
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    //our private key -
    private String encodedSecretKey = "this+is+my+key+and+it+must+be+at+least+256+bits+long";
    //create our private key -
    private Key decodedSecrectKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), this.signatureAlgorithm);

    //generate key
    //we need user email, password and role for create the token
    //since the userDetail is an instace of class, we need to make it hashcode
    //the token need to get clamis which is the information in hashcode
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();        //create new hash map for claims
        claims.put("userPass", userDetails.getPassword());  //insert password
        claims.put("userType", userDetails.getUserType());  //insert user type (role)
        return createToken(claims, userDetails.getEmail()); //send the subject (email)
    }

    //we create the JWT token from the information that we got.
    private String createToken(Map<String, Object> claims, String email) {
        Instant now = Instant.now();//get current time
        return Jwts.builder()       //create JWT builder to assist us with JWT creation
                .setClaims(claims)  //set our data (clamis-user,password,role,etc...)
                .setSubject(email)  //set our subject, the first item that we will check
                .setIssuedAt(Date.from(now))  //create issued at , which is current time
                //experation date, which after the date, we need to create a new token
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecrectKey) //sign the token with our secret key
                .compact();   //compact our token, that it will be smaller :)
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(this.decodedSecrectKey)
                .build();
        try {
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (Exception err) {
            return null;
        }
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExperationDate(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        try {
            extractAllClaims(token);
            return false;
        } catch (ExpiredJwtException err) {
            return true;
        }
    }

    public boolean validateToken(String token) {
        return (!isTokenExpired(token));
    }

    //tester
    public static void main(String[] args) {
        //create our instance of user that the token will be created for him.
        UserDetails admin = new UserDetails("admin@admin.com", "12345", "Admin");
        //use our new shiny JWTutil.
        JwtUtils myUtil = new JwtUtils();
        //generate a new token
        String myToken = myUtil.generateToken(admin);
        //print the token on screen to show to our mother.
        System.out.println("Token:\n" + myToken);
        //get our claims :)
        System.out.println("Token body:\n" + myUtil.extractAllClaims(myToken));
        System.out.println("email of token:\n" + myUtil.extractEmail(myToken));
    }
}

