package com.elingenio.Proyecto.config;

import com.elingenio.Proyecto.Modelo.Rol;
import com.elingenio.Proyecto.Modelo.Usuario;
import com.elingenio.Proyecto.Repository.RolRepository;
import com.elingenio.Proyecto.Repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Configuration
public class DataInitializer {

    // Ensure all database operations run in a single transaction
    @Bean
    @Transactional
    CommandLineRunner initDefaultUsers(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        return args -> {
            // Create Admin user
            createUserIfNotExists(usuarioRepository, rolRepository,
                    "admin@example.com", "admin123", "Admin", "User", "ROLE_ADMINISTRADOR");

            // Create Proveedor user
            createUserIfNotExists(usuarioRepository, rolRepository,
                    "proveedor@example.com", "prov123", "Proveedor", "User", "ROLE_PROVEEDOR");

            // Create Cliente user
            createUserIfNotExists(usuarioRepository, rolRepository,
                    "cliente@example.com", "cli123", "Cliente", "User", "ROLE_CLIENTE");
        };
    }

    private void createUserIfNotExists(UsuarioRepository usuarioRepository, RolRepository rolRepository,
                                       String email, String password, String nombre, String apellido, String roleName) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(email);
        if (usuarioExistente.isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password)); // Encriptar contraseña

            // Fetch the existing role from the database
            Optional<Rol> rol = rolRepository.findByNombre(roleName);
            if (rol.isPresent()) {
                usuario.setRoles(Collections.singletonList(rol.get())); // Assign managed Rol entity
            } else {
                throw new RuntimeException("Rol no encontrado en la base de datos: " + roleName + ". Asegúrate de que los roles estén pre-cargados.");
            }

            // Save the user (roles are already managed within the transaction)
            usuarioRepository.save(usuario);
            System.out.println("✅ Usuario creado con éxito: " + email + " con rol " + roleName);
        } else {
            System.out.println("⚠️ El usuario ya existe, no es necesario crearlo: " + email);
        }
    }
}