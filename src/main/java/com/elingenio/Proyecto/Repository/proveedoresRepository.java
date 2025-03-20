package com.elingenio.Proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.elingenio.Proyecto.Modelo.Proveedor;

@Repository
public interface proveedoresRepository extends JpaRepository<Proveedor, Long> {
}
