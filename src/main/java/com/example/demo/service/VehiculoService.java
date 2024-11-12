package com.example.demo.service;

import org.springframework.data.domain.Page;

import com.example.demo.dto.EstadoEnum;
import com.example.demo.dto.FacturaDto;
import com.example.demo.dto.FacturaEstadoEnum;
import com.example.demo.dto.SolicitudVehiculoDto;
import com.example.demo.dto.VehiculoDto;

public interface VehiculoService {

	public Page<VehiculoDto> listarConPaginadoPorSucursal( Integer size, String sort, Integer numPage, int idSucursal ) throws Exception;
	public VehiculoDto agregarVehiculo(VehiculoDto vehiculoDto) throws Exception;
	public VehiculoDto actualizarPrecio(int id, int nuevoPrecio) throws Exception;
	
	public Page<FacturaDto> listarFacturasPaginadas(int size, String sort, int numPage, int idSucursal) throws Exception;
	public FacturaDto crearFactura(FacturaDto facturaDto) throws Exception;
	public FacturaDto obtenerFacturaPorId(int id) throws Exception;
	public FacturaDto actualizarEstadoFactura(int id, FacturaEstadoEnum nuevoEstado) throws Exception;
	public void eliminarFactura(int id) throws Exception;
	
	public SolicitudVehiculoDto guardarSolicitud(SolicitudVehiculoDto solicitudVehiculoDto) throws Exception;
	public Page<SolicitudVehiculoDto> listarSolicitudesPaginado(int size, String sort, int numPage, int idSucursal) throws Exception;
	public void eliminarSolicitud(int id) throws Exception;
	public SolicitudVehiculoDto actualizarEstadoSolicitud(int id, EstadoEnum nuevoEstado) throws Exception;
	
}
