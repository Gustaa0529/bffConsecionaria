package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.requester.UsuarioRequester;

@Service
public class UsuarioServiceImp implements UsuarioService {

	    private final UsuarioRequester usuarioClient;
	    
	    @Autowired
	    public UsuarioServiceImp(UsuarioRequester usuarioClient) {
	        this.usuarioClient = usuarioClient;
	    }
	
	    @Override
	    public UsuarioDto getUsuarioByCorreo(String correo) {
	        return usuarioClient.getUsuarioByCorreo(correo);
	    }

	    @Override
	    public UsuarioDto login(UsuarioDto usuarioDto) {
	        return usuarioClient.login(usuarioDto);
	    }

	    @Override
	    public UsuarioDto registro(UsuarioDto usuarioDto) {
	        return usuarioClient.registro(usuarioDto);
	    }
}
