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
    //encryption -
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    
    private String encodedSecretKey = "****+**+**+***+***+**+****+**+**+*****+***+****+****";
    
    private Key decodedSecrectKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), this.signatureAlgorithm);

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();        //create new hash map for claims
        //claims.put("userPass", userDetails.getPassword());  //insert password
        claims.put("userType", userDetails.getUserType());  //insert user type (role)
        return createToken(claims, userDetails.getEmail()); //send the subject (email)
    }

    //we create the JWT token from the information that we got.
    private String createToken(Map<String, Object> claims, String email) {
        Instant now = Instant.now();
        return Jwts.builder()       
                .setClaims(claims)  //set data (clamis-user,password,role,etc...)
                .setSubject(email)  //set subject
                .setIssuedAt(Date.from(now))  //create issued at , which is current time
               
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecrectKey) 
                .compact();   //compact the token
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

    //tokenTester
    public static void main(String[] args) {
        //create an user instance 
        UserDetails admin = new UserDetails("admin@admin.com", "12345", "Admin");
        
        JwtUtils myUtil = new JwtUtils();
        
        String myToken = myUtil.generateToken(admin);
        
        System.out.println("Token:\n" + myToken);
        
        System.out.println("Token body:\n" + myUtil.extractAllClaims(myToken));
        System.out.println("email of token:\n" + myUtil.extractEmail(myToken));
    }
}

