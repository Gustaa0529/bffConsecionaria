package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EstadoEnum;
import com.example.demo.dto.FacturaDto;
import com.example.demo.dto.FacturaEstadoEnum;
import com.example.demo.dto.SolicitudVehiculoDto;
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

	@Override
	public Page<FacturaDto> listarFacturasPaginadas(int size, String sort, int numPage, int idSucursal)
			throws Exception {
		// TODO Auto-generated method stub
		return vehiculoRequester.listarFacturasPaginadas(size, sort, numPage, idSucursal);
	}

	@Override
	public FacturaDto crearFactura(FacturaDto facturaDto) throws Exception {
		// TODO Auto-generated method stub
		return vehiculoRequester.crearFactura(facturaDto);
	}

	@Override
	public FacturaDto obtenerFacturaPorId(int id) throws Exception {
		// TODO Auto-generated method stub
		return vehiculoRequester.obtenerFacturaPorId(id);
	}

	@Override
	public FacturaDto actualizarEstadoFactura(int id, FacturaEstadoEnum nuevoEstado) throws Exception {
		// TODO Auto-generated method stub
		return vehiculoRequester.actualizarEstadoFactura(id, nuevoEstado);
	}

	@Override
	public void eliminarFactura(int id) throws Exception {
		// TODO Auto-generated method stub
		vehiculoRequester.eliminarFactura(id);
	}

	@Override
	public SolicitudVehiculoDto guardarSolicitud(SolicitudVehiculoDto solicitudVehiculoDto) throws Exception {
		// TODO Auto-generated method stub
		return vehiculoRequester.guardarSolicitud(solicitudVehiculoDto);
	}

	@Override
	public Page<SolicitudVehiculoDto> listarSolicitudesPaginado(int size, String sort, int numPage, int idSucursal)
			throws Exception {
		// TODO Auto-generated method stub
		return vehiculoRequester.listarSolicitudesPaginado(size, sort, numPage, idSucursal);
	}

	@Override
	public void eliminarSolicitud(int id) throws Exception {
		// TODO Auto-generated method stub
		vehiculoRequester.eliminarSolicitud(id);
	}

	@Override
	public SolicitudVehiculoDto actualizarEstadoSolicitud(int id, EstadoEnum nuevoEstado) throws Exception {
		// TODO Auto-generated method stub
		return vehiculoRequester.actualizarEstadoSolicitud(id, nuevoEstado);
	}
		
}
