package com.example.demo.requester;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.PaginadoDto;
import com.example.demo.dto.VehiculoDto;

@Service
public class VehiculoRequesterImp implements VehiculoRequester {

	private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${pathBaseVehiculo}")
	private String urlBase;
	
	@Value("${paginadoVehiculo}")
	private String pathlistar;
	
	
	private static final Logger log = LoggerFactory.getLogger(VehiculoRequesterImp.class);

	public VehiculoRequesterImp(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	
	}

	@Override
	public List<VehiculoDto> listar() throws Exception {
        log.info("Se envía solicitud a: {}", urlBase.concat(pathlistar));

        // Realiza la llamada al microservicio
        ResponseEntity<List<VehiculoDto>> response = this.restTemplate.exchange(
            urlBase.concat(pathlistar),
            HttpMethod.GET, // Cambia a POST si es necesario
            null, // Si necesitas enviar un cuerpo, agrégalo aquí
            new ParameterizedTypeReference<List<VehiculoDto>>() {}
        );

        // Verifica el estado de la respuesta
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody(); // Devuelve la lista de VehiculoDto
        } else {
            throw new Exception("Error al consumir el microservicio: " + response.getStatusCode());
        }
	}

	@Override
	public Page<VehiculoDto> listarConPaginadoPorSucursal(Integer size, String sort, Integer numPage, int idSucursal) throws Exception {
	    
	    String url = String.format("%s%s?size=%d&sort=%s&numPage=%d&idSucursal=%s", 
	                               urlBase, pathlistar, size, sort, numPage, idSucursal);
	    log.info("Se envía solicitud a: {}", url);

	    try {
	       
	        ResponseEntity<PaginadoDto<VehiculoDto>> response = restTemplate.exchange(
	                url,
	                HttpMethod.GET,
	                null,
	                new ParameterizedTypeReference<PaginadoDto<VehiculoDto>>() {}
	        );

	        if (response.getStatusCode().is2xxSuccessful()) {
	            PaginadoDto<VehiculoDto> paginadoDto = response.getBody();
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


	
}
