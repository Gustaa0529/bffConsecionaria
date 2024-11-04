package com.example.demo.controller;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtTokenProvider;
import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UsuarioDto;
import com.example.demo.requester.UsuarioRequester;
import com.example.demo.requester.UsuarioRequesterImp;
import com.example.demo.service.JwtUserDetailsService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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


    @PostMapping(value = "/registro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDto> registro(@RequestBody UsuarioDto usuarioDto) throws Exception {
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

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authentication", token);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        AtomicReference<String> rol = new AtomicReference<>("");
        userDetails.getAuthorities().forEach(x -> {
            rol.set(x.getAuthority());
        });

        return new ResponseEntity<>(new JwtResponse(token, rol.get(), userDetails.getUsername(), Collections.emptyList()), responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDto> obtenerPerfil(@RequestHeader("Authorization") String token) {
        // Validar token y obtener perfil de usuario
        if (jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsername(token);
            UsuarioDto usuario = usuarioRequester.getUsuarioByCorreo(username);
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
