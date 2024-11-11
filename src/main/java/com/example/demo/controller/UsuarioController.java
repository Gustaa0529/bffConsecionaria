package com.example.demo.controller;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UsuarioDto;
import com.example.demo.requester.UsuarioRequester;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
    private UsuarioRequester usuarioRequester;

	@Autowired
    private AuthenticationManager authenticationManager;
	
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @PostMapping("/registro")
    public ResponseEntity<UsuarioDto> registro(@RequestBody UsuarioDto usuarioDto) throws Exception {
    	if (usuarioDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UsuarioDto usuarioGuardado = usuarioRequester.registro(usuarioDto);
        return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest, HttpSession session) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getCorreo(),
                        loginRequest.getContrasena()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UsuarioDto usuarioDto = usuarioRequester.getUsuarioByCorreo(loginRequest.getCorreo());

        final String token = jwtTokenProvider.createToken(authentication, usuarioDto);

        if (usuarioDto != null && usuarioDto.getIdSucursal() != null) {
        	session.setAttribute("usuario", usuarioDto); 
        	session.setAttribute("idSucursal", usuarioDto.getIdSucursal()); 
        	} else { 
        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        		}

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authentication", token);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        AtomicReference<String> rol = new AtomicReference<>("");
        userDetails.getAuthorities().forEach(x -> {
            rol.set(x.getAuthority());
        });

        return new ResponseEntity<>(new JwtResponse(token, rol.get(), userDetails.getUsername(), usuarioDto), responseHeaders, HttpStatus.OK);
    }


    @GetMapping("/verToken")
    public ResponseEntity<String> verContenidoToken(@RequestHeader("Authorization") String token) {
        try {
            // Eliminar el prefijo "Bearer " del token (si lo tiene)
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

            // Validar el token
            if (!jwtTokenProvider.validateToken(jwtToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no válido");
            }

            // Obtener los claims del token
            Claims claims = jwtTokenProvider.getClaims(jwtToken);

            // Mostrar todos los claims
            StringBuilder tokenInfo = new StringBuilder();
            tokenInfo.append("Token Claims: \n");

            // Imprimir los claims del token (puedes adaptarlo según lo que necesites ver)
            claims.forEach((key, value) -> tokenInfo.append(key).append(": ").append(value).append("\n"));

            // Devolver los claims como respuesta
            return ResponseEntity.ok(tokenInfo.toString());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el token: " + e.getMessage());
        }
    }
    
    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDto> obtenerPerfil(HttpSession session) {
        // Recuperamos el usuario desde la sesión
        UsuarioDto usuario = (UsuarioDto) session.getAttribute("usuario");
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    
    @PostMapping("/logout") 
    public ResponseEntity<Void> logout(HttpSession session) { 
    	
    	session.invalidate(); 
    	return ResponseEntity.ok().build(); 
    	
    }

}
