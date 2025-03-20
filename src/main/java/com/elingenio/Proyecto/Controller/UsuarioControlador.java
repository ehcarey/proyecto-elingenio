package com.elingenio.Proyecto.Controller;

import com.elingenio.Proyecto.Modelo.Usuario;
import com.elingenio.Proyecto.Services.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    // Mostrar todos los usuarios y el formulario de creación/edición
    @GetMapping
    public String listarYFormulario(@RequestParam Optional<Long> id, Model model) {
        if (id.isPresent()) {
            Optional<Usuario> usuario = usuarioServicio.obtenerPorId(id.get());
            usuario.ifPresent(value -> model.addAttribute("usuario", value));
        } else {
            model.addAttribute("usuario", new Usuario()); // Formulario vacío para creación
        }
        model.addAttribute("usuarios", usuarioServicio.obtenerTodos());
        return "usuarios"; // Vista única
    }

    // Actualizar un usuario por su id
    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioServicio.actualizar(id, usuario);  // Actualizamos el usuario
            return "redirect:/usuarios";  // Redirigimos a la lista de usuarios
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", e.getMessage());  // Mostramos el error si el correo está duplicado
            model.addAttribute("usuario", usuario);  // Retornamos el formulario con los datos del usuario
            model.addAttribute("usuarios", usuarioServicio.obtenerTodos());
            return "usuarios";  // Mantenemos en la misma vista
        }
    }

    // Crear un usuario con rol
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario, 
                                 @RequestParam(required = false) String role, 
                                 Model model) {
        try {
            // Asignar un rol por defecto si no se proporciona uno
            String roleToAssign = (role != null && !role.isEmpty()) ? role : "ROLE_CLIENTE";
            usuarioServicio.guardar(usuario, roleToAssign); // Llamada correcta con dos argumentos
            return "redirect:/usuarios";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "El correo electrónico ya está registrado.");
            model.addAttribute("usuario", usuario); // Retornar al formulario con los datos del usuario
            model.addAttribute("usuarios", usuarioServicio.obtenerTodos());
            return "usuarios"; // Volver a la misma vista
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el usuario: " + e.getMessage());
            model.addAttribute("usuario", usuario);
            model.addAttribute("usuarios", usuarioServicio.obtenerTodos());
            return "usuarios";
        }
    }

    // Eliminar un usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioServicio.eliminar(id);
        return "redirect:/usuarios"; // Redirigir a la lista de usuarios después de eliminar
    }
}