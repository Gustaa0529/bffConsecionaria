package com.example.demo.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ImagenDto {
	
    private String nombre;
    private String ruta;
    private int idVehiculo; 

    public ImagenDto() {
        super();
    }

    public ImagenDto(String nombre, String ruta, int idVehiculo) {
        super();
        this.nombre = nombre;
        this.ruta = ruta;
        this.idVehiculo = idVehiculo;
    }
    
}
