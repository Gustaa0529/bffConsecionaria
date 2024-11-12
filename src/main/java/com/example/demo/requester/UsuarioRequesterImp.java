package com.example.demo.requester;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.PaginadoDto;
import com.example.demo.dto.SucursalDto;
import com.example.demo.dto.UsuarioDto;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class UsuarioRequesterImp implements UsuarioRequester {

    @Value("${pathBaseUsuario}")
    private String usuariosServiceUrl;

    private final RestTemplate restTemplate;
    
    private static final Logger log = LoggerFactory.getLogger(VehiculoRequesterImp.class);

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
    
    public Page<UsuarioDto> listarUsuariosPaginados(int size, String sort, int numPage) throws Exception {
        String url = String.format("%s/usuarios/listar/paginado?size=%d&sort=%s&numPage=%d", usuariosServiceUrl, size, sort, numPage);
   
        log.info("Enviando solicitud a: {}", url);
        
        try {
            ResponseEntity<PaginadoDto<UsuarioDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PaginadoDto<UsuarioDto>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                PaginadoDto<UsuarioDto> paginadoDto = response.getBody();
                Pageable pageable = PageRequest.of(numPage, size);
                return new PageImpl<>(paginadoDto.getContent(), pageable, paginadoDto.getTotalElements());
            } else {
                log.error("Error al consumir el microservicio: {}", response.getStatusCode());
                throw new Exception("Error al consumir el microservicio: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Excepción al consumir el microservicio: {}", e.getMessage());
            throw new Exception("Excepción al consumir el microservicio: " + e.getMessage());
        }
    }

    public List<SucursalDto> listarSucursales() {
        String url = usuariosServiceUrl + "/usuarios/sucursales";
        ResponseEntity<List<SucursalDto>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<SucursalDto>>() {}
        );
        return response.getBody();
    }
    
}
