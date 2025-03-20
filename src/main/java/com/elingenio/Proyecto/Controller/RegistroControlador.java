package com.elingenio.Proyecto.Controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistroControlador {



    @GetMapping("/login")
    public String iniciarSesion() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Create a register.html template
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password"; // Create a forgot-password.html template
    }
}