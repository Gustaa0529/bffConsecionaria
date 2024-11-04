package com.example.demo.requester;

import com.example.demo.dto.UsuarioDto;

public interface UsuarioRequester {

	 public UsuarioDto getUsuarioByCorreo(String correo);
	 public UsuarioDto login(UsuarioDto usuarioDto);
	 public UsuarioDto registro(UsuarioDto usuarioDto);
}
