package spring.api.uteating.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public Map<String, Object> generateTokenWithExpiry(String userName) {
        Map<String, Object> claims = new HashMap<>();
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 1);
        String token = createToken(claims, userName, expirationDate);

        Map<String, Object> tokenDetails = new HashMap<>();
        tokenDetails.put("access_token", token);
        tokenDetails.put("expires_at", expirationDate);
        return tokenDetails;
    }

    public Map<String, Object> generateToken(String userName, int roleId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role_id", roleId);
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        String token = createToken(claims, userName, expirationDate);

        Map<String, Object> tokenDetails = new HashMap<>();
        tokenDetails.put("access_token", token);
        tokenDetails.put("expires_at", expirationDate);
        return tokenDetails;
    }

//    public String generateToken(String userName) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userName);
//    }

    private String createToken(Map<String, Object> claims, String userName, Date expirationDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public int extractRoleId(String token) {
        return extractClaim(token, claims -> claims.get("role_id", Integer.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
