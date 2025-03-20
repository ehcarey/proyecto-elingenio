package com.elingenio.Proyecto.Controller;

import com.elingenio.Proyecto.Modelo.Compra;
import com.elingenio.Proyecto.Services.CompraService;
import com.elingenio.Proyecto.Services.ProveedorService;
import com.elingenio.Proyecto.Services.ProductosServicio;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import org.springframework.http.HttpHeaders;
import java.io.ByteArrayOutputStream;

@Controller
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProductosServicio productoService;

    // Listar todas las compras
    @GetMapping
    public String listarCompras(Model model) {
        model.addAttribute("compras", compraService.findAll());
        model.addAttribute("compra", new Compra());
        model.addAttribute("proveedores", proveedorService.listarTodos());
        model.addAttribute("productos", productoService.listarProductos());
        return "compras";
    }

    // Mostrar formulario para registrar una nueva compra
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("compra", new Compra());
        model.addAttribute("proveedores", proveedorService.listarTodos());
        model.addAttribute("productos", productoService.listarProductos());
        return "compras";
    }

    // Guardar una nueva compra
    @PostMapping("/guardar")
    public String guardarCompra(@ModelAttribute Compra compra) {
        // Validación del método de pago
        if ("tarjeta".equalsIgnoreCase(compra.getMetododepagos())) {
            if (compra.getNombreTitular() == null || compra.getNumeroCuenta() == null || compra.getCvd() == null) {
                throw new IllegalArgumentException("Faltan datos para el pago con tarjeta.");
            }
        }
        compra.setCostototal(compra.getCantidad() * compra.getCostounitario());
        compraService.save(compra);
        return "redirect:/compras";
    }

    @GetMapping("/exportar-pdf/{id}")
    public ResponseEntity<byte[]> exportarFacturaPdf(@PathVariable Long id) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Compra compra = compraService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada con ID: " + id));

            PdfWriter writer = new PdfWriter(baos);
            Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));
            document.setMargins(20, 20, 20, 20);

            // Encabezado con logo y título estilizado
            String logoPath = "src/main/resources/static/img/_30f120d0-9dc5-4205-ac8d-e4b3143520c9.jpg";
            Image logo = new Image(ImageDataFactory.create(logoPath));
            logo.setWidth(100);

            Table headerTable = new Table(new float[]{1, 3});
            headerTable.setWidth(UnitValue.createPercentValue(100));
            headerTable.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell()
                    .add(new Paragraph("Factura de Compra")
                            .setBold()
                            .setFontSize(24)
                            .setTextAlignment(TextAlignment.RIGHT))
                    .setBorder(Border.NO_BORDER));
            document.add(headerTable);

            // Separador
            document.add(new Paragraph("\n").setHeight(10));

            // Información de la empresa con estilo
            Paragraph empresaInfo = new Paragraph()
                    .add(new Text("Ferretería El Ingenio\n").setBold().setFontSize(14))
                    .add("Dirección: Calle Principal 123, Ciudad\n")
                    .add("Teléfono: +123 456 789\n")
                    .add("Correo: contacto@elingenio.com\n");
            document.add(empresaInfo);

            document.add(new Paragraph("\n"));

            // Detalles de la factura
            document.add(new Paragraph("Factura Número: " + compra.getCodigo())
                    .setBold().setFontSize(12));
            document.add(new Paragraph("Fecha: " + java.time.LocalDate.now()));

            document.add(new Paragraph("\n"));

            // Tabla de detalles con bordes y espacio
            Table detailTable = new Table(new float[]{4, 8});
            detailTable.setWidth(UnitValue.createPercentValue(100));
            detailTable.setMarginBottom(20);

            detailTable.addCell(new Cell().add(new Paragraph("Proveedor:").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            detailTable.addCell(new Cell().add(new Paragraph(compra.getProveedor().getNombre())));
            detailTable.addCell(new Cell().add(new Paragraph("Producto:").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            detailTable.addCell(new Cell().add(new Paragraph(compra.getProducto().getDescripcion())));
            detailTable.addCell(new Cell().add(new Paragraph("Cantidad:").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            detailTable.addCell(new Cell().add(new Paragraph(String.valueOf(compra.getCantidad()))));
            detailTable.addCell(new Cell().add(new Paragraph("Costo Unitario:").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            detailTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", compra.getCostounitario()))));
            detailTable.addCell(new Cell().add(new Paragraph("Costo Total:").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            detailTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", compra.getCostototal()))));
            detailTable.addCell(new Cell().add(new Paragraph("Método de Pago:").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            detailTable.addCell(new Cell().add(new Paragraph(compra.getMetododepagos())));
            document.add(detailTable);

            // Información adicional
            if ("tarjeta".equalsIgnoreCase(compra.getMetododepagos())) {
                document.add(new Paragraph("Nombre del Titular: " + compra.getNombreTitular()));
            } else if ("transferencia".equalsIgnoreCase(compra.getMetododepagos())) {
                document.add(new Paragraph("Nombre del Titular: " + compra.getNombreTitular()));
                document.add(new Paragraph("Número de Cuenta: " + compra.getNumeroCuenta()));
            } else if ("cheque".equalsIgnoreCase(compra.getMetododepagos())) {
                document.add(new Paragraph("Nombre del Titular: " + compra.getNombreTitular()));
                document.add(new Paragraph("Número de Cheque: " + compra.getNumeroCheque()));
            }

            // Separador
            document.add(new Paragraph("\n").setHeight(20));

            // Pie de página centrado
            document.add(new Paragraph("Gracias por su compra")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(12));

            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=factura_" + id + ".pdf");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }





    // Mostrar formulario para editar una compra
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Compra compra = compraService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada con ID: " + id));
        model.addAttribute("compra", compra);
        model.addAttribute("proveedores", proveedorService.listarTodos());
        model.addAttribute("productos", productoService.listarProductos());
        return "compras";
    }

    // Actualizar una compra existente
    @PostMapping("/actualizar/{id}")
    public String actualizarCompra(@PathVariable Long id, @ModelAttribute Compra compra) {
        compra.setId(id);
        compra.setCostototal(compra.getCantidad() * compra.getCostounitario());
        compraService.save(compra);
        return "redirect:/compras";
    }

    // Eliminar una compra
    @GetMapping("/eliminar/{id}")
    public String eliminarCompra(@PathVariable Long id) {
        compraService.deleteById(id);
        return "redirect:/compras";
    }
}
