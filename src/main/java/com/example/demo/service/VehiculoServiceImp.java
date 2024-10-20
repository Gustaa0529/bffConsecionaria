package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.dto.VehiculoDto;
import com.example.demo.requester.VehiculoRequester;

@Service
public class VehiculoServiceImp implements VehiculoService {

	
	@Autowired
	private VehiculoRequester vehiculoRequester;

	@Override
	public Page<VehiculoDto> listarConPaginadoV2(Integer size, String sort, Integer numPage) throws Exception {
		// TODO Auto-generated method stub
		return vehiculoRequester.listarConPaginadoV2(size, sort, numPage);
	}
	
}
