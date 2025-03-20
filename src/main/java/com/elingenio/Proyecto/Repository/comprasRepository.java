package com.elingenio.Proyecto.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elingenio.Proyecto.Modelo.Compra;

@Repository
public interface comprasRepository extends JpaRepository<Compra, Long> {

}
