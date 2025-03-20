package com.elingenio.Proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elingenio.Proyecto.Modelo.ventas;

@Repository
public interface VentasRepository extends JpaRepository<ventas, Long> {
}