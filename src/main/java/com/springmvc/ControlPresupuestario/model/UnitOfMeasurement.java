package com.springmvc.ControlPresupuestario.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_unidad_medida")
public class UnitOfMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uni_id")
    private Long id;
    
    @Column(name = "uni_descripcion", length = 100)
    private String descripcionUnidad;
    
    
    @Column(name = "uni_abreviatura", length = 100)
    private String abrevitura;
    
    
    @Column(name = "uni_estado", length = 100)
    private String estado;
}
