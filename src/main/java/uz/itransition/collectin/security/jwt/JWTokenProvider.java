package uz.itransition.collectin.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import uz.itransition.collectin.entity.User;
import uz.itransition.collectin.entity.enums.Role;
import uz.itransition.collectin.exception.JwtValidationException;
import uz.itransition.collectin.security.oauth2.UserPrincipal;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static uz.itransition.collectin.security.jwt.AuthConst.*;

@Service
public class JWTokenProvider {
    @Value("${jwt.access.secret.key}")
    private String accessTokenSecretKey;
    @Value("${jwt.refresh.secret.key}")
    private String refreshTokenSecretKey;
    @Value("${jwt.access.expiration.time}")
    private long accessTokenExpirationTime;
    @Value("${jwt.refresh.expiration.time}")
    private long refreshTokenExpirationTime;

    public String generateAccessToken(User user) {
        return AUTH_TYPE + generateToken(user, false);
    }
    public String generateAccessToken(UserPrincipal user) {
        return AUTH_TYPE + generateToken(user, false);
    }

    public String generateRefreshToken(User user) {
        return AUTH_TYPE + generateToken(user, true);
    }

    public boolean validateAccessToken(String accessToken) {
        return parseToken(accessToken, false) != null;
    }

    public String validateRefreshToken(String refreshToken) {
        return parseToken(refreshToken, true)
                .getSubject();
    }

    public Authentication getAuthorities(String jwtAccessToken) {
        Claims claims = parseToken(jwtAccessToken, false);
        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                getAuthorities(claims.get(ROLE_VALUE, Integer.class))
        );
    }

    private String generateToken(User user, boolean forRefresh) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(ROLE_VALUE, user.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + (forRefresh ? refreshTokenExpirationTime : accessTokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS256, forRefresh ? refreshTokenSecretKey : accessTokenSecretKey)
                .compact();
    }

    private String generateToken(UserPrincipal user, boolean forRefresh) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(ROLE_VALUE, user.getAuthorities().size())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + (forRefresh ? refreshTokenExpirationTime : accessTokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS256, forRefresh ? refreshTokenSecretKey : accessTokenSecretKey)
                .compact();
    }

    public Claims parseToken(String token, boolean forRefresh) {
        try {
            return Jwts.parser()
                    .setSigningKey(forRefresh ? refreshTokenSecretKey : accessTokenSecretKey)
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            throw new JwtValidationException(TOKEN_EXPIRED);
        } catch (Exception ex) {
            throw new JwtValidationException(INVALID_TOKEN);
        }
    }

    private Set<GrantedAuthority> getAuthorities(int roleValue) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : Role.values()) {
            if ((roleValue & role.getFlag()) > 0)
                authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}