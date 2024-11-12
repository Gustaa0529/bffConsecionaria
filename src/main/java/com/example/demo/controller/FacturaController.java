package com.example.demo.controller;

import com.example.demo.dto.FacturaDto;
import com.example.demo.dto.FacturaEstadoEnum;
import com.example.demo.dto.PaginadoDto;
import com.example.demo.service.VehiculoService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping("/crear")
    public ResponseEntity<FacturaDto> crearFactura(@RequestBody FacturaDto facturaDto) throws Exception {
        FacturaDto nuevaFactura = vehiculoService.crearFactura(facturaDto);
        return new ResponseEntity<>(nuevaFactura, HttpStatus.CREATED);
    }

    @GetMapping("/paginado")
    public ResponseEntity<PaginadoDto<FacturaDto>> listarFacturasPaginadas (
            @RequestParam Integer size,
            @RequestParam String sort,
            @RequestParam Integer numPage,
            @RequestParam (required = false) Integer  idSucursal,
	        HttpSession session) throws Exception {
		if(session.getAttribute("usuario") != null) {
		if (idSucursal == null) { 
			idSucursal = (int) session.getAttribute("idSucursal"); 
			}
		}
        Page<FacturaDto> facturasPaginadas = vehiculoService.listarFacturasPaginadas(size, sort, numPage, idSucursal);
        
        PaginadoDto<FacturaDto> response = new PaginadoDto<>(
        		facturasPaginadas.getContent(),
        		facturasPaginadas.getTotalElements(),
        		facturasPaginadas.getTotalPages(),
        		facturasPaginadas.getSize(),
        		facturasPaginadas.getNumber()
	    );

	    return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDto> obtenerFacturaPorId(@PathVariable int id) throws Exception {
        FacturaDto factura = vehiculoService.obtenerFacturaPorId(id);
        return new ResponseEntity<>(factura, HttpStatus.OK);
    }

    @PutMapping("/actualizarEstado/{id}")
    public ResponseEntity<FacturaDto> actualizarEstadoFactura(@PathVariable int id, @RequestBody FacturaEstadoEnum nuevoEstado) throws Exception {
        FacturaDto facturaActualizada = vehiculoService.actualizarEstadoFactura(id, nuevoEstado);
        return new ResponseEntity<>(facturaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarFactura(@PathVariable int id) throws Exception {
    	vehiculoService.eliminarFactura(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
