package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.VehiculoDto;

public interface VehiculoService {

	public Page<VehiculoDto> listarConPaginadoV2( Integer size, String sort, Integer numPage ) throws Exception;
	
}
