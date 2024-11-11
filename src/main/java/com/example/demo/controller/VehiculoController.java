package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PaginadoDto;
import com.example.demo.dto.VehiculoDto;
import com.example.demo.service.VehiculoService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/vehiculo")
public class VehiculoController {

	@Autowired
	private VehiculoService vehiculoService;

	@Autowired
	    public VehiculoController(VehiculoService vehiculoService) {
	        this.vehiculoService = vehiculoService;
	}

	@PutMapping("/actualizar-precio/{id}") 
	public ResponseEntity<VehiculoDto> actualizarPrecio(@PathVariable int id, @RequestParam int nuevoPrecio) { 
		try { 
			VehiculoDto vehiculoActualizado = vehiculoService.actualizarPrecio(id, nuevoPrecio); 
			return new ResponseEntity<>(vehiculoActualizado, HttpStatus.OK); 
			} catch (Exception e) { 
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
				} 
		} 
	
	@PostMapping("/agregar") 
	public ResponseEntity<VehiculoDto> agregarVehiculo(@RequestBody VehiculoDto vehiculoDto) { 
		try { 
			VehiculoDto nuevoVehiculo = vehiculoService.agregarVehiculo(vehiculoDto); 
			return new ResponseEntity<>(nuevoVehiculo, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
				} 
		}
	
	@GetMapping("/paginado")
	public ResponseEntity<PaginadoDto<VehiculoDto>> listarVehiculos(
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
	    Page<VehiculoDto> pageResult = vehiculoService.listarConPaginadoPorSucursal(size, sort, numPage, idSucursal);

	    PaginadoDto<VehiculoDto> response = new PaginadoDto<>(
	            pageResult.getContent(),
	            pageResult.getTotalElements(),
	            pageResult.getTotalPages(),
	            pageResult.getSize(),
	            pageResult.getNumber()
	    );

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
