package com.elingenio.Proyecto.Modelo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int codigo;
    private int cantidad;
    private String metododepagos; // Efectivo, tarjeta, transferencia, cheque
    private Float costounitario;
    private double costototal;

    private String nombreTitular; // Para tarjeta y transferencia
    private String numeroCuenta;  // Para tarjeta y transferencia
    private String cvd;           // Para tarjeta
    private String numeroCheque;  // Para cheque

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
}
