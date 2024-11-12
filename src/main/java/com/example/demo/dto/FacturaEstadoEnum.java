package com.example.demo.dto;

public enum FacturaEstadoEnum {
	
	ORDENADO("Ordenado"),
    VENDIDO("Vendido");

    private String displayName;

    FacturaEstadoEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
