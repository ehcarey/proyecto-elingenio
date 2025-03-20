package com.elingenio.Proyecto.Controller;

import com.elingenio.Proyecto.Modelo.Clientes;
import com.elingenio.Proyecto.Modelo.Producto;
import com.elingenio.Proyecto.Modelo.ventas;
import com.elingenio.Proyecto.Services.ClientesService;
import com.elingenio.Proyecto.Services.ProductosServicio;
import com.elingenio.Proyecto.Services.VentasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private VentasService ventasService;

    @Autowired
    private ProductosServicio productosServicio;

    @Autowired
    private ClientesService clientesService;

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        // Key Metrics - Real Data from Services
        List<ventas> ventasList = ventasService.listarVentas();
        int ventasTotal = ventasList.size();
        double ventasCrecimiento = calculateGrowthPercentage(ventasList);

        List<Producto> productosList = productosServicio.listarProductos();
        int productosTotal = productosList.size();
        double productosCrecimiento = calculateGrowthPercentage(productosList);

        List<Clientes> clientesList = clientesService.obtenerTodosLosClientes();
        int clientesTotal = clientesList.size();
        double clientesCrecimiento = calculateGrowthPercentage(clientesList);

        // Total Users
     
        // Add metrics to the model
        model.addAttribute("ventasTotal", ventasTotal);
        model.addAttribute("ventasCrecimiento", ventasCrecimiento);
        model.addAttribute("productosTotal", productosTotal);
        model.addAttribute("productosCrecimiento", productosCrecimiento);
        model.addAttribute("clientesTotal", clientesTotal);
        model.addAttribute("clientesCrecimiento", clientesCrecimiento);
   
        // Sales Trend Data (you'll need to adapt this based on your data structure)
        // Assuming you have a way to get sales by month (e.g., a method in VentasService)
        model.addAttribute("salesMonths", Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul")); // Static for now
        model.addAttribute("salesThisYear", getSalesByMonthThisYear()); // Implement this method
        model.addAttribute("salesLastYear", getSalesByMonthLastYear()); // Implement this method

        // Traffic by Device Data (you'll need to define how this data is collected, e.g., from a log or analytics service)
        model.addAttribute("trafficDevices", Arrays.asList("Linux", "Mac", "iOS", "Windows", "Android", "Other"));
        model.addAttribute("trafficData", Arrays.asList(1000, 1500, 2000, 1200, 1800, 800)); // Replace with real data

        // Portfolio Status Data (you'll need to define how this is calculated, e.g., based on ventas or another model)
        model.addAttribute("portfolioLabels", Arrays.asList("Deudas", "Ventas", "Pendientes"));
        model.addAttribute("portfolioData", calculatePortfolioStatus()); // Implement this method

        return "dashboard";
    }

    // Helper method to calculate growth percentage (example implementation)
    private double calculateGrowthPercentage(List<?> list) {
        // This is a placeholder. You need to implement logic based on your data.
        // For example, compare this year's count with last year's count.
        // If you don't have historical data, return 0.0 or a default value.
        return 0.0; // Replace with actual logic
    }

    // Helper method to get sales by month for this year (example implementation)
    private List<Integer> getSalesByMonthThisYear() {
        // This is a placeholder. You need to implement logic in VentasService to filter sales by month and year.
        return Arrays.asList(1000, 1500, 2000, 1800, 2500, 3000, 3200); // Replace with real data
    }

    // Helper method to get sales by month for last year (example implementation)
    private List<Integer> getSalesByMonthLastYear() {
        // This is a placeholder. You need to implement logic in VentasService to filter sales by month and year.
        return Arrays.asList(900, 1300, 1800, 1600, 2200, 2700, 2900); // Replace with real data
    }

    // Helper method to calculate portfolio status (example implementation)
    private List<Double> calculatePortfolioStatus() {
        // This is a placeholder. You need to implement logic to calculate percentages for Deudas, Ventas, and Pendientes.
        // For example, query your database or calculate based on ventas, clients, or other models.
        return Arrays.asList(52.1, 13.9, 11.2); // Replace with real data
    }
}