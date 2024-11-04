package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.User;

import com.example.demo.requester.UsuarioRequester;
import com.example.demo.dto.UsuarioDto;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRequester usuarioRequester;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioDto usuario = usuarioRequester.getUsuarioByCorreo(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo: " + username);
        }
        
        UserBuilder builder = User.withUsername(usuario.getCorreo());
        builder.password(usuario.getContrasena());
        builder.roles(usuario.getRol().getDisplayName());

        return builder.build();
    }
}
