package com.elingenio.Proyecto.Controller;

import com.elingenio.Proyecto.Modelo.Clientes;
import com.elingenio.Proyecto.Services.ClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private ClientesService clientesService;

    @GetMapping
    public String listarClientes(Model model) {
        List<Clientes> clientes = clientesService.obtenerTodosLosClientes();
        model.addAttribute("clientes", clientes);
        model.addAttribute("cliente", new Clientes()); // Para el formulario
        return "clientes"; // Nombre de la plantilla Thymeleaf
    }

    @PostMapping
    public String crearCliente(@ModelAttribute Clientes cliente) {
        clientesService.guardarCliente(cliente);
        return "redirect:/clientes"; // Redirige a la lista de clientes
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCliente(@PathVariable Long id, Model model) {
        Clientes cliente = clientesService.obtenerClientePorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + id));
        model.addAttribute("cliente", cliente); // Cargar el cliente para editar
        model.addAttribute("clientes", clientesService.obtenerTodosLosClientes()); // Cargar la lista de clientes
        return "clientes"; // Redirige a la plantilla que maneja el formulario y la lista
    }

    @PostMapping("/editar/{id}")
    public String actualizarCliente(@PathVariable Long id, @ModelAttribute Clientes cliente) {
        cliente.setId(id);
        clientesService.guardarCliente(cliente);
        return "redirect:/clientes"; // Redirige a la lista de clientes
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clientesService.eliminarCliente(id);
        return "redirect:/clientes"; // Redirige a la lista de clientes
    }
}
