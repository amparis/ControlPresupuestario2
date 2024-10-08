package com.springmvc.ControlPresupuestario.repository;

import com.springmvc.ControlPresupuestario.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
