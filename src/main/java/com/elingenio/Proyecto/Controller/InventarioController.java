package com.elingenio.Proyecto.Controller;


import com.elingenio.Proyecto.Modelo.Inventario;

import com.elingenio.Proyecto.Repository.ProductosRepositorio;
import com.elingenio.Proyecto.Services.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ProductosRepositorio productoRepository; // Asegúrate de tener este repositorio

    @GetMapping
    public String listarInventario(Model model) {
        List<Inventario> inventarios = inventarioService.listarTodos();
        model.addAttribute("inventarios", inventarios);
        model.addAttribute("inventario", new Inventario()); // Para el formulario vacío
        model.addAttribute("productos", productoRepository.findAll()); // Para el dropdown de productos
        return "inventario"; // nombre de la plantilla Thymeleaf
    }

    @PostMapping
    public String guardarInventario(@ModelAttribute Inventario inventario) {
        inventarioService.guardar(inventario);
        return "redirect:/inventario"; // redirigir a la lista de inventarios
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarInventario(@PathVariable Long id, Model model) {
        Inventario inventario = inventarioService.obtenerPorId(id).orElseThrow(() -> new IllegalArgumentException("ID no válido: " + id));
        model.addAttribute("inventario", inventario);
        model.addAttribute("inventarios", inventarioService.listarTodos());
        model.addAttribute("productos", productoRepository.findAll());
        return "inventario"; // nombre de la plantilla Thymeleaf
    }

    @PostMapping("/editar/{id}")
    public String actualizarInventario(@PathVariable Long id, @ModelAttribute Inventario inventario) {
        inventario.setId(id);
        inventarioService.guardar(inventario);
        return "redirect:/inventario"; // redirigir a la lista de inventarios
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarInventario(@PathVariable Long id) {
        inventarioService.eliminar(id);
        return "redirect:/inventario"; // redirigir a la lista de inventarios
    }
}
