package com.elingenio.Proyecto.Controller;

// Asegúrate de importar la clase Producto
import com.elingenio.Proyecto.Modelo.ventas;
import com.elingenio.Proyecto.Services.ClientesService; // Importa el servicio de Clientes
 // Importa el servicio de Producto
import com.elingenio.Proyecto.Services.ProductosServicio;
import com.elingenio.Proyecto.Services.VentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/ventas")
public class VentasController {

    @Autowired
    private VentasService ventasService;

    @Autowired
    private ClientesService clientesService; // Servicio para manejar clientes

    @Autowired
    private ProductosServicio productoService; // Servicio para manejar productos

    @GetMapping
    public String listarVentas(Model model) {
        model.addAttribute("ventas", ventasService.listarVentas());
        model.addAttribute("venta", new ventas()); // Agregar un objeto vacío para el formulario
        model.addAttribute("clientes", clientesService.obtenerTodosLosClientes()); // Cargar la lista de clientes
        model.addAttribute("productos", productoService.listarProductos()); // Cargar la lista de productos
        return "ventas"; // devuelve el mismo template para lista y formulario
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("venta", new ventas());
        model.addAttribute("clientes", clientesService.obtenerTodosLosClientes()); // Cargar la lista de clientes
        model.addAttribute("productos", productoService.listarProductos()); // Cargar la lista de productos
        return "ventas";
    }

    @PostMapping
    public String guardarVenta(@ModelAttribute ventas venta) {
        ventasService.guardarVenta(venta);
        return "redirect:/ventas";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        ventas venta = ventasService.obtenerVentaPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid venta id: " + id));
        model.addAttribute("venta", venta);
        model.addAttribute("ventas", ventasService.listarVentas()); // Mantener la lista de ventas
        model.addAttribute("clientes", clientesService.obtenerTodosLosClientes()); // Cargar la lista de clientes
        model.addAttribute("productos", productoService.listarProductos()); // Cargar la lista de productos
        return "ventas";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarVenta(@PathVariable Long id, @ModelAttribute ventas venta) {
        venta.setId(id);
        ventasService.guardarVenta(venta);
        return "redirect:/ventas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        ventasService.eliminarVenta(id);
        return "redirect:/ventas";
    }
}
