package com.elingenio.Proyecto.Services;

import com.elingenio.Proyecto.Modelo.Compra;
import com.elingenio.Proyecto.Repository.comprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private comprasRepository compraRepository;

    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    public Optional<Compra> findById(long id) {
        return compraRepository.findById(id);
    }

    public Compra save(Compra compra) {
        return compraRepository.save(compra);
    }

    public void deleteById(long id) {
        compraRepository.deleteById(id);
    }

    // Alias para mayor claridad
    public Compra guardarCompra(Compra compra) {
        return save(compra);
    }
}
