package com.elingenio.Proyecto.Controller.accesos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/portfolio")
    public String portfolio() {
        return "index";
    }

    @GetMapping("/pages")
    public String pages() {
        return "ventana-home/ventana-proveedores";
    }

    @GetMapping("/test-registro")
    public String testRegister() {
        return "seccion/registro";
    }

    @GetMapping("/blog")
    public String blog() {
        return "index";
    }

    @GetMapping("/contact")
    public String contact() {
        return "index";
    }
}