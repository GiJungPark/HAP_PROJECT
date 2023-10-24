package me.gijung.HAP.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;


public class JwtUtil {

    @Value("${jwt.token.secret}")
    private static String key;

    private static Long expireTimeMs = 1000 * 60 * 60L;


    public static String createToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact()
                ;

    }

    public static boolean isExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public static Date getExpiration(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJwt(token)
                .getBody().getExpiration();
    }

    public static String getEmail(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token)
                .getBody().get("email", String.class);
    }
}
