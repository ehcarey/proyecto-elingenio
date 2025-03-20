package com.elingenio.Proyecto.Controller.accesos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @GetMapping("/dashboard")
    public String clienteDashboard() {
        return "clientes/cliente_dashboard"; // Thymeleaf template for cliente
    }
}