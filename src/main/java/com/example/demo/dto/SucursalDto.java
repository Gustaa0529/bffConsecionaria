package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class SucursalDto {
	
	 private int idSucursal;
	 private String direccion;
	
	 public SucursalDto() {}

	 public SucursalDto(int idSucursal, String direccion) {
	        this.idSucursal = idSucursal;
	        this.direccion = direccion;
	        
	  }

}
