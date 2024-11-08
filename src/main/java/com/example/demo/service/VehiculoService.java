package com.example.demo.service;

import org.springframework.data.domain.Page;

import com.example.demo.dto.VehiculoDto;

public interface VehiculoService {

	public Page<VehiculoDto> listarConPaginadoPorSucursal( Integer size, String sort, Integer numPage, int idSucursal ) throws Exception;
	
}
