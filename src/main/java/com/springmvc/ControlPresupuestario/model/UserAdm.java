package com.springmvc.ControlPresupuestario.model;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_usuarios")
@Entity
public class UserAdm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "us_id")
	private long id;
	
	@Column(name = "us_nombre_usuario")
	private String usernamee;
	
	@Column(name = "us_nombre_completo")
	private String fullname;

	 @Column(name = "us_email", unique = true)
	 private String email;

	 @Column(name = "us_password")
	 private String password;

	 @Column(name = "us_fecha_creacion")
	 private Timestamp dateCreation;

	 @Column(name = "us_fecha_actualizacion")
	 private Timestamp dateUpdate;
	 

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "us_rol_id")
	    private Perfil perfil;

		/**
		 * @return the id
		 */
		public long getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(long id) {
			this.id = id;
		}

		/**
		 * @return the username
		 */
		public String getUsernamee() {
			return usernamee;
		}

		/**
		 * @param username the username to set
		 */
		public void setUsernamee(String usernamee) {
			this.usernamee = usernamee;
		}

		/**
		 * @return the fullname
		 */
		public String getFullname() {
			return fullname;
		}

		/**
		 * @param fullname the fullname to set
		 */
		public void setFullname(String fullname) {
			this.fullname = fullname;
		}

		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * @param email the email to set
		 */
		public void setEmail(String email) {
			this.email = email;
		}

		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @param password the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		/**
		 * @return the dateCreation
		 */
		public Timestamp getDateCreation() {
			return dateCreation;
		}

		/**
		 * @param dateCreation the dateCreation to set
		 */
		public void setDateCreation(Timestamp dateCreation) {
			this.dateCreation = dateCreation;
		}

		/**
		 * @return the dateUpdate
		 */
		public Timestamp getDateUpdate() {
			return dateUpdate;
		}

		/**
		 * @param dateUpdate the dateUpdate to set
		 */
		public void setDateUpdate(Timestamp dateUpdate) {
			this.dateUpdate = dateUpdate;
		}

		/**
		 * @return the perfil
		 */
		public Perfil getPerfil() {
			return perfil;
		}

		/**
		 * @param perfil the perfil to set
		 */
		public void setPerfil(Perfil perfil) {
			this.perfil = perfil;
		}

		@ManyToOne(fetch = FetchType.EAGER, optional = true) // Permite que sea nulo
		@JoinColumn(name = "us_ben_id", nullable = true)
		private Beneficiary beneficiario;
		/*
		Relación opcional: Usamos optional = true para que el campo beneficiario pueda ser nulo.
				@JoinColumn con nullable = true: Esto permite que la columna us_ben_id sea opcional en la base de datos.
				Estrategia EAGER: Esta es una estrategia común en relaciones ManyToOne para cargar el beneficiario asociado junto con el usuario.
		*/
		/** @return the beneficiario
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

		public UserAdm orElseThrow(Object object) {
			// TODO Auto-generated method stub
			return null;
		}


		/*public UserAdm orElseThrow() {
			// TODO Auto-generated method stub
			return null;
		}

		public UserAdm orElseThrow(Object object) {
			// TODO Auto-generated method stub
			return null;
		}*/
		 @Column(name = "us_estado")
		 private String estado;

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
		
}
