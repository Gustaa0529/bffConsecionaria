package com.example.demo.dto;

import lombok.Data;

public class UsuarioDto {

	 private static final long serialVersionUID = 1L;

	    private Integer idUsuario;
	    private String nombre;
	    private String correo;
	    private String contrasena;
	    private Integer idSucursal;
	    private Rol rol;
		public Integer getIdUsuario() {
			return idUsuario;
		}
		public void setIdUsuario(Integer idUsuario) {
			this.idUsuario = idUsuario;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getCorreo() {
			return correo;
		}
		public void setCorreo(String correo) {
			this.correo = correo;
		}
		public String getContrasena() {
			return contrasena;
		}
		public void setContrasena(String contrasena) {
			this.contrasena = contrasena;
		}
		public Integer getIdSucursal() {
			return idSucursal;
		}
		public void setIdSucursal(Integer idSucursal) {
			this.idSucursal = idSucursal;
		}
		public Rol getRol() {
			return rol;
		}
		public void setRol(Rol rol) {
			this.rol = rol;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		} 
	    
}
