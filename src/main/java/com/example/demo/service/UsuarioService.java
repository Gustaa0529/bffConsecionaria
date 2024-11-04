package com.example.demo.service;

import com.example.demo.dto.UsuarioDto;

public interface UsuarioService {

	public UsuarioDto getUsuarioByCorreo(String correo);
	public UsuarioDto login(UsuarioDto usuarioDto);
	public UsuarioDto registro(UsuarioDto usuarioDto);
}
