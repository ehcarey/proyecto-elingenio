package com.elingenio.Proyecto.Services;

// Asegúrate de que la clase se llame "Ventas" y no "ventas"
import com.elingenio.Proyecto.Modelo.ventas;
import com.elingenio.Proyecto.Repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentasService {

    @Autowired
    private VentasRepository ventasRepository;

    public List<ventas> listarVentas() {
        return ventasRepository.findAll();
    }

    public Optional<ventas> obtenerVentaPorId(Long id) {
        return ventasRepository.findById(id);
    }

    public void guardarVenta(ventas venta) {
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        ventasRepository.save(venta);
    }

    public void eliminarVenta(Long id) {
        ventasRepository.deleteById(id);
    }
}