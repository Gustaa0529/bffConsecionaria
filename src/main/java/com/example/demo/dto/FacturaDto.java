package com.example.demo.dto;

import lombok.Data;

@Data
public class FacturaDto {
	
	private int idFactura;

    private String nombre;
    private String correo;
    private String metodoPago;
    private int telefono;
    private FacturaEstadoEnum estado;

    private VehiculoDto vehiculoDto;
    

}
