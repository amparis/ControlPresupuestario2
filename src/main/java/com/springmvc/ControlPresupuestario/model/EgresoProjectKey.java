package com.springmvc.ControlPresupuestario.model;
import java.util.Objects;

public class EgresoProjectKey {
    private Integer beneficiarioId;
    private String beneficiarioFullName;
    private Long expenseId;
    private String nombreClaseCargoItem;

    public EgresoProjectKey(Integer beneficiarioId, String beneficiarioFullName,Long expenseId, 
    					String nombreClase, String cargoItem) {
        this.beneficiarioId = beneficiarioId;
        this.beneficiarioFullName = beneficiarioFullName;
        this.expenseId = expenseId;
        this.nombreClaseCargoItem = nombreClase + " - " + cargoItem;
    }

    public Integer getBeneficiarioId() {
        return beneficiarioId;
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public String getNombreClaseCargoItem() {
        return nombreClaseCargoItem;
    }

    // Implementa equals() y hashCode() para que funcione correctamente como clave en el Map
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EgresoProjectKey that = (EgresoProjectKey) o;
        return Objects.equals(beneficiarioId, that.beneficiarioId) &&
                Objects.equals(beneficiarioFullName, that.beneficiarioFullName) &&
                Objects.equals(expenseId, that.expenseId) &&
                Objects.equals(nombreClaseCargoItem, that.nombreClaseCargoItem);
    }
    public String getBeneficiarioFullName() {
        return beneficiarioFullName;
    }
    @Override
    public int hashCode() {
        return Objects.hash(beneficiarioId, beneficiarioFullName,expenseId, nombreClaseCargoItem);
    }
}
