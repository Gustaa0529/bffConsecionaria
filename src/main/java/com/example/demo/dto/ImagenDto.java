package com.example.demo.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ImagenDto {
	
    private String nombre;
    private byte[] contenido; // Almacena el contenido de la imagen en formato binario
    private int idVehiculo; // Referencia al vehículo asociado

    public ImagenDto() {
        super();
    }

    public ImagenDto(String nombre, byte[] contenido, int idVehiculo) {
        super();
        this.nombre = nombre;
        this.contenido = contenido;
        this.idVehiculo = idVehiculo;
    }
}
