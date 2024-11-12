package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.PaginadoDto;
import com.example.demo.dto.SucursalDto;
import com.example.demo.dto.UsuarioDto;

public interface UsuarioService {

	public UsuarioDto getUsuarioByCorreo(String correo);
	public UsuarioDto login(UsuarioDto usuarioDto);
	public UsuarioDto registro(UsuarioDto usuarioDto);
	public Page<UsuarioDto> listarUsuariosPaginados(int size, String sort, int numPage) throws Exception;
	public List<SucursalDto> listarSucursales() ;
}
