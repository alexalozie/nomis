package org.nomisng.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.nomisng.domain.entity.Role;
import org.nomisng.repository.UserRepository;
import org.nomisng.security.UserPrincipal;
import org.nomisng.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    @Value("${jwt.base64-secret}")
    private String secret;
    @Value("${jwt.token-validity-in-milli-seconds}")
    private long tokenValidityInMilliseconds;
    @Value("${jwt.token-validity-in-milli-seconds-for-remember-me}")
    private long tokenValidityInMillisecondsForRememberMe;
    @Autowired
    UserRepository userRepository;

    @Value("${jwt.base64-secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String createToken(Authentication authentication, UserService userService, boolean rememberMe) {
        long now = System.currentTimeMillis();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        org.nomisng.domain.entity.User user = userService.getUserWithRoles().get();
        //getting & adding user details to token
        String name = user.getFirstName() + " " + user.getLastName();
        String authorities = user.getRole().stream().map(Role::getName).collect(Collectors.joining(","));
        return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities).claim("name", name)
                .setIssuedAt(new Date(now))
                .signWith(this.getSigningKey(), SignatureAlgorithm.HS512)
                .setExpiration(validity).compact();

    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(this.getSigningKey()).build().parseClaimsJws(token).getBody();
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public String generateTokenFromUsername(String username) {
        long now = System.currentTimeMillis();
        Date validity  = new Date(now + this.tokenValidityInMillisecondsForRememberMe);

        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(this.getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(this.getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
