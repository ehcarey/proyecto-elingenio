package com.elingenio.Proyecto.Modelo;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "ventas")
public class ventas{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Add an id field for the entity

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Clientes cliente; // Ensure the 'Clientes' entity is defined elsewhere

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto; // Relation with 'Producto' entity

    private Integer cantidad;

    private LocalDate fecha;

    private BigDecimal valorUnitario;

    private BigDecimal total;

    private String metodoPago;

    private BigDecimal subtotal;

    private BigDecimal iva;

    private String tipodocumento;

    private BigDecimal costo;
}
