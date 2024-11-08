package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UsuarioDto;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    private String encodeSecret() {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(Authentication authentication, UsuarioDto usuarioDto) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());
        claims.put("idUsuario", usuarioDto.getIdUsuario());
        claims.put("nombre", usuarioDto.getNombre());
        claims.put("correo", usuarioDto.getCorreo());
        claims.put("contrasena", usuarioDto.getContrasena());
        claims.put("idSucursal", usuarioDto.getIdSucursal());
        claims.put("rol", usuarioDto.getRol().getDisplayName());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, encodeSecret())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(encodeSecret()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(encodeSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(encodeSecret()).parseClaimsJws(token).getBody();
    }
    
}
