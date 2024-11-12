package com.example.demo.dto;

import lombok.Data;

@Data
public class JwtResponse {
	
	private String token;
    private String rol;
    private String username;
    private Object authorities;
    private Integer idSucursal;
    private Rol rolUsuario;
    
	public JwtResponse(String token, String rol, String username, Object authorities, Integer idSucursal,
			Rol rolUsuario) {
		super();
		this.token = token;
		this.rol = rol;
		this.username = username;
		this.authorities = authorities;
		this.idSucursal = idSucursal;
		this.rolUsuario = rolUsuario;
	}
    
}
