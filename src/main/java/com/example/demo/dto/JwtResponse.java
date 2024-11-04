package com.example.demo.dto;

import lombok.Data;

@Data
public class JwtResponse {
	
	private String token;
    private String rol;
    private String username;
    private Object authorities;

    public JwtResponse(String token, String rol, String username, Object authorities) {
        this.token = token;
        this.rol = rol;
        this.username = username;
        this.authorities = authorities;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Object authorities) {
        this.authorities = authorities;
    }
    
}
