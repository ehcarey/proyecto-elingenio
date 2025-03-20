package com.elingenio.Proyecto.Repository;

import com.elingenio.Proyecto.Modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepositorio extends JpaRepository<Producto, Long> {
}
