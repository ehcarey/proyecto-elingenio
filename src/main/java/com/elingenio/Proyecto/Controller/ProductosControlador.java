package com.elingenio.Proyecto.Controller;

import com.elingenio.Proyecto.Modelo.Producto;
import com.elingenio.Proyecto.Services.ProductosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductosControlador {

    @Autowired
    private ProductosServicio productosServicio;

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productosServicio.listarProductos());
        model.addAttribute("producto", new Producto()); // Producto vacío para el formulario de creación
        model.addAttribute("modo", "LISTA"); // Modo de lista por defecto
        return "productos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicion(@PathVariable Long id, Model model) {
        Producto producto = productosServicio.obtenerProductoPorId(id);
        if (producto != null) {
            model.addAttribute("producto", producto);
            model.addAttribute("productos", productosServicio.listarProductos());
            model.addAttribute("modo", "EDITAR");
        }
        return "productos";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productosServicio.guardarProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
public String eliminarProducto(@PathVariable Long id, Model model) {
    try {
        productosServicio.eliminarProducto(id);
        model.addAttribute("success", "Producto eliminado exitosamente.");
    } catch (RuntimeException e) {
        model.addAttribute("error", e.getMessage());
    }
    model.addAttribute("productos", productosServicio.listarProductos()); // Cargar productos para la vista
    return "productos"; // Vista donde se listan los productos
}
}