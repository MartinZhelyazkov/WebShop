package com.web_shop.shop.condig;

import com.web_shop.shop.model.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${secret-key}")
    private String SECRET_KEY;

    public String extractUserName(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public String generateJwtToken(Customer customer){
        return generateToken(new HashMap<>(),customer);
    }

    private String generateToken(HashMap<String, Objects> claims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000* 60*60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T>T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyAsBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyAsBytes);
    }

    public boolean isValid(String jwt, UserDetails userDetails) {
        final String username = extractClaim(jwt,Claims::getSubject);
        final String email = userDetails.getUsername();
        return (username.equals(email)) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        return extractExp(jwt).before(new Date());
    }

    private Date extractExp(String jwt ){
        return extractClaim(jwt,Claims::getExpiration);
    }
}
