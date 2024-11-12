package com.example.demo.dto;

public enum EstadoEnum {

	VEHICULOSOLICITADO("Vehiculo solicitado"),
    VEHICULOENVIADO("Vehiculo enviado");

    private String displayName;

    EstadoEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
