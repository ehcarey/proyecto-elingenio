package com.elingenio.Proyecto.Services;

import com.elingenio.Proyecto.Modelo.Rol;
import com.elingenio.Proyecto.Modelo.Usuario;
import com.elingenio.Proyecto.Repository.RolRepository;
import com.elingenio.Proyecto.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
@Autowired
    private RolRepository rolRepository;
    public Iterable<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario guardar(Usuario usuario, String roleName) {
        Optional<Usuario> usuarioExistente = obtenerPorEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new DataIntegrityViolationException("El correo electrónico ya está registrado.");
        }
        // Encriptar la contraseña
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        // Fetch existing role instead of creating a new one
        Optional<Rol> rol = rolRepository.findByNombre(roleName);
        if (rol.isPresent()) {
            usuario.setRoles(Collections.singletonList(rol.get()));
        } else {
            throw new RuntimeException("Rol no encontrado: " + roleName);
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario usuarioNuevo) {
        // Same as before, no role change needed here unless you want to allow role updates
        Optional<Usuario> usuarioActual = usuarioRepository.findById(id);
        if (usuarioActual.isPresent()) {
            Usuario usuario = usuarioActual.get();
            if (!usuario.getEmail().equals(usuarioNuevo.getEmail())) {
                Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuarioNuevo.getEmail());
                if (usuarioExistente.isPresent()) {
                    throw new DataIntegrityViolationException("El correo electrónico ya está registrado.");
                }
            }
            if (!usuario.getPassword().equals(usuarioNuevo.getPassword())) {
                String passwordEncriptada = new BCryptPasswordEncoder().encode(usuarioNuevo.getPassword());
                usuario.setPassword(passwordEncriptada);
            }
            usuario.setNombre(usuarioNuevo.getNombre());
            usuario.setApellido(usuarioNuevo.getApellido());
            usuario.setEmail(usuarioNuevo.getEmail());
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Usuario> usuarioOpt = obtenerPorEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Usuario usuario = usuarioOpt.get();
        List<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toList());
        return new User(usuario.getEmail(), usuario.getPassword(), authorities);
    }
}