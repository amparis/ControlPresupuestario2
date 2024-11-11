package com.springmvc.ControlPresupuestario.model;

public class LoanReturnDTO {
    private Long prestamoId;
    private Double devuelto;

    // Constructor, getters y setters
    public LoanReturnDTO(Long prestamoId, Double devuelto) {
        this.prestamoId = prestamoId;
        this.devuelto = devuelto;
    }

    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    }

    public Double getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(Double devuelto) {
        this.devuelto = devuelto;
    }
}
