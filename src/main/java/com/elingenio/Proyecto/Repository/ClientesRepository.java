package com.elingenio.Proyecto.Repository;


import com.elingenio.Proyecto.Modelo.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {
}
