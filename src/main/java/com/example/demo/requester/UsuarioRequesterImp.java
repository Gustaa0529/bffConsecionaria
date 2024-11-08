package com.example.demo.requester;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.demo.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Service
public class UsuarioRequesterImp implements UsuarioRequester {

    @Value("${pathBaseUsuario}")
    private String usuariosServiceUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public UsuarioRequesterImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UsuarioDto getUsuarioByCorreo(String correo) {
        return restTemplate.getForObject(usuariosServiceUrl + "/usuarios/correo/" + correo, UsuarioDto.class);
    }

    @Override
    public UsuarioDto login(UsuarioDto usuarioDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<UsuarioDto> request = new HttpEntity<>(usuarioDto, headers);
        ResponseEntity<UsuarioDto> response = restTemplate.postForEntity(usuariosServiceUrl + "/usuarios/login", request, UsuarioDto.class);
        return response.getBody();
    }

    @Override
    public UsuarioDto registro(UsuarioDto usuarioDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<UsuarioDto> request = new HttpEntity<>(usuarioDto, headers);
        ResponseEntity<UsuarioDto> response = restTemplate.postForEntity(usuariosServiceUrl + "/usuarios/registro", request, UsuarioDto.class);
        return response.getBody();
    }
}
