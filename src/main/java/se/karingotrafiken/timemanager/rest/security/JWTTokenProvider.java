package se.karingotrafiken.timemanager.rest.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.entitys.User;
import se.karingotrafiken.timemanager.rest.service.users.UserServiceImpl;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTTokenProvider {

    private String secretKey = "mySecretKey";
    private final long VALIDITY_TIME_IN_MILLIS = 1000 * 3600 * 24; // Token valid for 24h
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("auth", user.getRoles().stream().map(roleType -> new SimpleGrantedAuthority(roleType.getAuthority())).collect(Collectors.toList()));
        claims.put("employeeId", user.getEmployee().getId());

        Date now = new Date();
        Date validityDate = new Date(now.getTime() + VALIDITY_TIME_IN_MILLIS);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validityDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String cleanedToken) {
        UserDetails userDetails = userServiceImpl.loadUserByUsername(getUsernameFrom(cleanedToken));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public String getUsernameFrom(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public long getEmployeeIdFrom(String token) {
        token = token.substring(7, token.length());
        return (long) (int) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("employeeId");
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }


    public boolean isValidateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
