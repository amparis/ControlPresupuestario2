package com.springmvc.ControlPresupuestario.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_egresos")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "egre_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fp_id")
    private ProjectPhase proyectoFase;

    @ManyToOne
    @JoinColumn(name = "ben_id")
    private Beneficiary beneficiario;

    @ManyToOne
    @JoinColumn(name = "clase_id")
    private ExpenditureClassification clasificacionEgreso;

    @Column(name = "egre_tipo", nullable = false, length = 250)
    private String tipo;

    @Column(name = "egre_objeto", nullable = false, length = 250)
    private String objeto;

    @Column(name = "egre_cargo_item", nullable = false, length = 250)
    private String cargoItem;

    @Column(name = "egre_cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "egre_fecha_inicio")
    private Date fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "egre_fecha_fin")
    private Date fechaFin;

    @Column(name = "egre_tiempo", nullable = false, precision = 18, scale = 6)
    private double tiempo;

    @Column(name = "egre_costo_unitario", nullable = false, precision = 18, scale = 2)
    private double costoUnitario;

    @Column(name = "egre_aguinaldo", precision = 18, scale = 2)
    private double aguinaldo;

    @Column(name = "egre_monto_total", nullable = false, precision = 18, scale = 2)
    private double montoTotal;

    @Column(name = "egre_estado", nullable = false, length = 10)
    private String estado;

    @Column(name = "egre_us_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "egre_descargo")
    private Boolean descargo;

    @ManyToOne
    @JoinColumn(name = "uni_id")
    private UnitOfMeasurement unidadMedida;

    //@Column(name = "egre_pais", length = 250)
    //private String pais;
    
    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Country pais;

    @Column(name = "egre_fecha")
    private Date fecha;

    @Column(name = "egre_tipo_transferencia", length = 250)
    private String tipoTransferencia;

    @Column(name = "egre_fee", precision = 18, scale = 2)
    private double fee;

    @Column(name = "egre_cambio", precision = 18, scale = 6)
    private double cambio;

    @Column(name = "egre_total_lcu", precision = 18, scale = 2)
    private double totalLCU;

    @Column(name = "egre_attach", columnDefinition = "text")
    private String attach;

    @Column(name = "egre_fecha_registro")
    private Timestamp fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    private Account cuenta;
    
    @ManyToOne
    @JoinColumn(name = "divisa_id")
    private Currency divisa;
    
    @ManyToOne
    @JoinColumn(name = "fpag_id")
    private PaymentMethod paymentMethod;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the beneficiario
	 */
	public Beneficiary getBeneficiario() {
		return beneficiario;
	}

	/**
	 * @param beneficiario the beneficiario to set
	 */
	public void setBeneficiario(Beneficiary beneficiario) {
		this.beneficiario = beneficiario;
	}

	/**
	 * @return the clasificacionEgreso
	 */
	public ExpenditureClassification getClasificacionEgreso() {
		return clasificacionEgreso;
	}

	/**
	 * @param clasificacionEgreso the clasificacionEgreso to set
	 */
	public void setClasificacionEgreso(ExpenditureClassification clasificacionEgreso) {
		this.clasificacionEgreso = clasificacionEgreso;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the objeto
	 */
	public String getObjeto() {
		return objeto;
	}

	/**
	 * @param objeto the objeto to set
	 */
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	/**
	 * @return the cargoItem
	 */
	public String getCargoItem() {
		return cargoItem;
	}

	/**
	 * @param cargoItem the cargoItem to set
	 */
	public void setCargoItem(String cargoItem) {
		this.cargoItem = cargoItem;
	}

	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the tiempo
	 */
	public double getTiempo() {
		return tiempo;
	}

	/**
	 * @param tiempo the tiempo to set
	 */
	public void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}

	/**
	 * @return the costoUnitario
	 */
	public double getCostoUnitario() {
		return costoUnitario;
	}

	/**
	 * @param costoUnitario the costoUnitario to set
	 */
	public void setCostoUnitario(double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	/**
	 * @return the aguinaldo
	 */
	public double getAguinaldo() {
		return aguinaldo;
	}

	/**
	 * @param aguinaldo the aguinaldo to set
	 */
	public void setAguinaldo(double aguinaldo) {
		this.aguinaldo = aguinaldo;
	}

	/**
	 * @return the montoTotal
	 */
	public double getMontoTotal() {
		return montoTotal;
	}

	/**
	 * @param montoTotal the montoTotal to set
	 */
	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the usuarioId
	 */
	public Integer getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	/**
	 * @return the descargo
	 */
	public Boolean getDescargo() {
		return descargo;
	}

	/**
	 * @param descargo the descargo to set
	 */
	public void setDescargo(Boolean descargo) {
		this.descargo = descargo;
	}

	/**
	 * @return the unidadMedida
	 */
	public UnitOfMeasurement getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadMedida the unidadMedida to set
	 */
	public void setUnidadMedida(UnitOfMeasurement unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	/**
	 * @return the pais
	 
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}*/

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	public Country getPais() {
		return pais;
	}

	public void setPais(Country pais) {
		this.pais = pais;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the tipoTransferencia
	 */
	public String getTipoTransferencia() {
		return tipoTransferencia;
	}

	/**
	 * @param tipoTransferencia the tipoTransferencia to set
	 */
	public void setTipoTransferencia(String tipoTransferencia) {
		this.tipoTransferencia = tipoTransferencia;
	}

	/**
	 * @return the fee
	 */
	public double getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(double fee) {
		this.fee = fee;
	}

	/**
	 * @return the cambio
	 */
	public double getCambio() {
		return cambio;
	}

	/**
	 * @param cambio the cambio to set
	 */
	public void setCambio(double cambio) {
		this.cambio = cambio;
	}

	/**
	 * @return the totalLCU
	 */
	public double getTotalLCU() {
		return totalLCU;
	}

	/**
	 * @param totalLCU the totalLCU to set
	 */
	public void setTotalLCU(double totalLCU) {
		this.totalLCU = totalLCU;
	}

	/**
	 * @return the attach
	 */
	public String getAttach() {
		return attach;
	}

	/**
	 * @param attach the attach to set
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Timestamp getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the cuenta
	 */
	public Account getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(Account cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return the proyectoFase
	 */
	public ProjectPhase getProyectoFase() {
		return proyectoFase;
	}

	/**
	 * @param proyectoFase the proyectoFase to set
	 */
	public void setProyectoFase(ProjectPhase proyectoFase) {
		this.proyectoFase = proyectoFase;
	}

	public Currency getDivisa() {
		return divisa;
	}

	public void setDivisa(Currency divisa) {
		this.divisa = divisa;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

    // Getters and Setters
    // Constructor por defecto y otros necesarios


}
