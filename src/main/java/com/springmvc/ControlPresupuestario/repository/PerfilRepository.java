package com.springmvc.ControlPresupuestario.repository;

import com.springmvc.ControlPresupuestario.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
