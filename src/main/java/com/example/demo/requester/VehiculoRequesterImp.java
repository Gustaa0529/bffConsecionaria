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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.EstadoEnum;
import com.example.demo.dto.FacturaDto;
import com.example.demo.dto.FacturaEstadoEnum;
import com.example.demo.dto.PaginadoDto;
import com.example.demo.dto.SolicitudVehiculoDto;
import com.example.demo.dto.VehiculoDto;

@Service
public class VehiculoRequesterImp implements VehiculoRequester {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${pathBaseVehiculo}")
    private String urlBase;

    @Value("${paginadoVehiculo}")
    private String pathListar;

    private static final Logger log = LoggerFactory.getLogger(VehiculoRequesterImp.class);

    public VehiculoRequesterImp(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public VehiculoDto actualizarPrecio(int id, int nuevoPrecio) throws Exception {
        String url = urlBase + "/vehiculos/" + id + "/actualizar-precio?nuevoPrecio=" + nuevoPrecio;
        ResponseEntity<VehiculoDto> response = restTemplate.exchange(url, HttpMethod.PUT, null, VehiculoDto.class);
        return response.getBody();
    }

    @Override
    public VehiculoDto agregarVehiculo(VehiculoDto vehiculoDto) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<VehiculoDto> request = new HttpEntity<>(vehiculoDto, headers);
        ResponseEntity<VehiculoDto> response = restTemplate.postForEntity(urlBase + "/vehiculos/agregar", request, VehiculoDto.class);
        return response.getBody();
    }

    @Override
    public List<VehiculoDto> listar() throws Exception {
        log.info("Se envía solicitud a: {}", urlBase.concat(pathListar));

        // Realiza la llamada al microservicio
        ResponseEntity<List<VehiculoDto>> response = this.restTemplate.exchange(
                urlBase.concat(pathListar),
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
        String url = String.format("%s%s?size=%d&sort=%s&numPage=%d&idSucursal=%d", urlBase, pathListar, size, sort, numPage, idSucursal);
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

    @Override
    public FacturaDto crearFactura(FacturaDto facturaDto) throws Exception {
        return restTemplate.postForObject(urlBase + "/factura/crearFactura", facturaDto, FacturaDto.class);
    }

    @Override
    public Page<FacturaDto> listarFacturasPaginadas(int size, String sort, int numPage, int idSucursal) throws Exception {
        String url = String.format("%s/factura/paginado?size=%d&sort=%s&numPage=%d&idSucursal=%d", urlBase, size, sort, numPage, idSucursal);
        log.info("Enviando solicitud a: {}", url);
        try {
            ResponseEntity<PaginadoDto<FacturaDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<PaginadoDto<FacturaDto>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                PaginadoDto<FacturaDto> paginadoDto = response.getBody();
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

    @Override
    public FacturaDto obtenerFacturaPorId(int id) throws Exception {
        return restTemplate.getForObject(urlBase + "/factura/" + id, FacturaDto.class);
    }

    @Override
    public FacturaDto actualizarEstadoFactura(int id, FacturaEstadoEnum nuevoEstado) throws Exception {
        return restTemplate.exchange(urlBase + "/factura/actualizarEstado/" + id, HttpMethod.PUT, new HttpEntity<>(nuevoEstado), FacturaDto.class).getBody();
    }

    @Override
    public void eliminarFactura(int id) throws Exception {
        restTemplate.delete(urlBase + "/factura/eliminar/" + id);
    }

    @Override
    public SolicitudVehiculoDto guardarSolicitud(SolicitudVehiculoDto solicitudVehiculoDto) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<SolicitudVehiculoDto> request = new HttpEntity<>(solicitudVehiculoDto, headers);
        ResponseEntity<SolicitudVehiculoDto> response = restTemplate.postForEntity(urlBase + "/vehiculos/guardarSoli", request, SolicitudVehiculoDto.class);
        return response.getBody();
    }

    @Override
    public Page<SolicitudVehiculoDto> listarSolicitudesPaginado(int size, String sort, int numPage, int idSucursal) throws Exception {
        String url = String.format("%s/vehiculos/listarSoli?size=%d&sort=%s&numPage=%d&idSucursal=%d", urlBase, size, sort, numPage, idSucursal);
        log.info("Se envía solicitud a: {}", url);

        try {
            ResponseEntity<PaginadoDto<SolicitudVehiculoDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<PaginadoDto<SolicitudVehiculoDto>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                PaginadoDto<SolicitudVehiculoDto> paginadoDto = response.getBody();
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
    
    @Override
    public void eliminarSolicitud(int id) throws Exception {
        restTemplate.delete(urlBase + "/vehiculos//eliminarSoli/" + id);
    }

    @Override
    public SolicitudVehiculoDto actualizarEstadoSolicitud(int id, EstadoEnum nuevoEstado) throws Exception {
        String url = urlBase + "/vehiculos/actualizarEstadoSoli/" + id + "?nuevoEstado=" + nuevoEstado.name();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<SolicitudVehiculoDto> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, SolicitudVehiculoDto.class);
        return response.getBody();
    }
}
