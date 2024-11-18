package com.springmvc.ControlPresupuestario.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tbl_fase_proyecto")
@Entity
public class ProjectPhase {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "fp_id")
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "proy_id", nullable = false)
	    private Project proyecto;

	    @ManyToOne
	    @JoinColumn(name = "fase_id")
	    private Phase fase;

	    @Column(name = "fp_presupuesto", precision = 19, scale = 2)
	    private double presupuesto;

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
		 * @return the proyecto
		 */
		public Project getProyecto() {
			return proyecto;
		}

		/**
		 * @param proyecto the proyecto to set
		 */
		public void setProyecto(Project proyecto) {
			this.proyecto = proyecto;
		}

		/**
		 * @return the fase
		 */
		public Phase getFase() {
			return fase;
		}

		/**
		 * @param fase the fase to set
		 */
		public void setFase(Phase fase) {
			this.fase = fase;
		}

		/**
		 * @return the presupuesto
		 */
		public double getPresupuesto() {
			return presupuesto;
		}

		/**
		 * @param presupuesto the presupuesto to set
		 */
		public void setPresupuesto(double presupuesto) {
			this.presupuesto = presupuesto;
		}
	    
	    

}
