package com.elingenio.Proyecto.Services;

import com.elingenio.Proyecto.Modelo.Proveedor;
import com.elingenio.Proyecto.Repository.proveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private proveedoresRepository proveedorRepositorio;

    public List<Proveedor> listarTodos() {
        return proveedorRepositorio.findAll();
    }

    public Optional<Proveedor> obtenerPorId(Long id) {
        return proveedorRepositorio.findById(id);
    }

    public Proveedor guardar(Proveedor proveedor) {
        return proveedorRepositorio.save(proveedor);
    }

    public void eliminar(Long id) {
        proveedorRepositorio.deleteById(id);
    }
}
