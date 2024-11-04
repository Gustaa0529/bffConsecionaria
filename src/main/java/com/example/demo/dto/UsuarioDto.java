package com.example.demo.dto;

import lombok.Data;

@Data
public class UsuarioDto {

	 private static final long serialVersionUID = 1L;

	    private Integer idUsuario;
	    private String nombre;
	    private String correo;
	    private String contrasena;
	    private Integer idSucursal;
	    private Rol rol; 
	    
}
