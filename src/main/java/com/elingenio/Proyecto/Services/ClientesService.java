// ClientesService.java
package com.elingenio.Proyecto.Services;

import com.elingenio.Proyecto.Modelo.Clientes;
import com.elingenio.Proyecto.Repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientesService {
    @Autowired
    private ClientesRepository clientesRepository;

    public List<Clientes> obtenerTodosLosClientes() {
        return clientesRepository.findAll();
    }

    public Optional<Clientes> obtenerClientePorId(Long id) {
        return clientesRepository.findById(id);
    }

    public Clientes guardarCliente(Clientes cliente) {
        return clientesRepository.save(cliente);
    }

    public void eliminarCliente(Long id) {
        clientesRepository.deleteById(id);
    }
}