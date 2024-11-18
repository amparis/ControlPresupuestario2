package com.springmvc.ControlPresupuestario.model;

import java.util.Objects;

public class EgresoKey {
    private Long id;
    private String nombreClaseCargoItem;

    public EgresoKey(Long id, String nombreClase, String cargoItem) {
        this.id = id;
        this.nombreClaseCargoItem = nombreClase + " - " + cargoItem;
    }

    public Long getId() {
        return id;
    }

    public String getNombreClaseCargoItem() {
        return nombreClaseCargoItem;
    }

    // Implementa equals() y hashCode() para que funcione correctamente como clave en el Map
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EgresoKey egresoKey = (EgresoKey) o;
        return Objects.equals(id, egresoKey.id) && Objects.equals(nombreClaseCargoItem, egresoKey.nombreClaseCargoItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreClaseCargoItem);
    }
}
