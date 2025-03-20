package com.elingenio.Proyecto.Services;

import com.elingenio.Proyecto.Modelo.Producto;
import com.elingenio.Proyecto.Repository.ProductosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductosServicio {

    @Autowired
    private ProductosRepositorio productosRepositorio;

    public List<Producto> listarProductos() {
        return productosRepositorio.findAll();
    }

    public Producto obtenerProductoPorId(Long id) {
        return productosRepositorio.findById(id).orElse(null);
    }

    public Producto guardarProducto(Producto producto) {
        return productosRepositorio.save(producto);
    }

    public void eliminarProducto(Long id) {
        try {
            productosRepositorio.deleteById(id);
        } catch (Exception e) {
            // Lanza una excepción personalizada si el producto no se puede eliminar
            throw new RuntimeException("No se puede eliminar el producto porque está relacionado con otros registros.");
        }
    }
}
