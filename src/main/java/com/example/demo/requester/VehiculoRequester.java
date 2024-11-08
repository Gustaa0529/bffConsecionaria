package com.example.demo.requester;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.VehiculoDto;


public interface VehiculoRequester {

	public List<VehiculoDto> listar() throws Exception;
	public Page<VehiculoDto> listarConPaginadoPorSucursal( Integer size, String sort, Integer numPage, int idSucursal ) throws Exception;
}
