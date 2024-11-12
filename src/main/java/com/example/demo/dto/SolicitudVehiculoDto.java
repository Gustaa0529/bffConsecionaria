package com.example.demo.dto;


import lombok.Data;

@Data
public class SolicitudVehiculoDto {
	
	private int idSolicitud; 
    private VehiculoDto vehiculoDto;
    private SucursalDto Sucursal;
    private EstadoEnum estado; 


}
