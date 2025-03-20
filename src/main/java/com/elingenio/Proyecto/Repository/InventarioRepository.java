package com.elingenio.Proyecto.Repository;

import com.elingenio.Proyecto.Modelo.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {


}
    
