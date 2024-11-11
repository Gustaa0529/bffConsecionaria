package com.example.demo.service;

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
	public Page<VehiculoDto> listarConPaginadoPorSucursal(Integer size, String sort, Integer numPage, int idSucursal) throws Exception {
		// TODO Auto-generated method stub
		return vehiculoRequester.listarConPaginadoPorSucursal(size, sort, numPage, idSucursal);
	}

	@Override
	public VehiculoDto agregarVehiculo(VehiculoDto vehiculoDto) throws Exception {
		// TODO Auto-generated method stub
		return vehiculoRequester.agregarVehiculo(vehiculoDto);
	}

	@Override
	public VehiculoDto actualizarPrecio(int id, int nuevoPrecio) throws Exception {
		// TODO Auto-generated method stub
		return vehiculoRequester.actualizarPrecio(id, nuevoPrecio);
	}
	
}
