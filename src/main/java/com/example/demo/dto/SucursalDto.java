package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class SucursalDto {
	
	 private int idSucursal;
	 private String direccion;
	 private List<VehiculoDto> vehiculos;

	 public SucursalDto() {}

	 public SucursalDto(int idSucursal, String direccion, List<VehiculoDto> vehiculos) {
	        this.idSucursal = idSucursal;
	        this.direccion = direccion;
	        this.vehiculos = vehiculos;
	  }

}
