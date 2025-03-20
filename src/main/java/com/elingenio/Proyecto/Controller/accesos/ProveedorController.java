package com.elingenio.Proyecto.Controller.accesos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("proveedorAccessController") // Unique bean name
@RequestMapping("/proveedor/accesos")
public class ProveedorController {

    @GetMapping("/dashboard")
    public String proveedorAccessDashboard() {
        return "proveedor_dashboard"; // Different template if needed
    }
}