package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PaginadoDto;
import com.example.demo.dto.VehiculoDto;
import com.example.demo.service.VehiculoService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/bff")
public class VehiculoController {

	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	    public VehiculoController(VehiculoService vehiculoService) {
	        this.vehiculoService = vehiculoService;
	}

	@GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaginadoDto<VehiculoDto>> listarConPaginado(
	        @RequestParam Integer size,
	        @RequestParam String sort,
	        @RequestParam Integer numPage) throws Exception {

	    Page<VehiculoDto> pageResult = vehiculoService.listarConPaginadoV2(size, sort, numPage);
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
