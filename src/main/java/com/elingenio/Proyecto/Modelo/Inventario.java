        package com.elingenio.Proyecto.Modelo;

        import java.time.LocalDate;

        import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;


        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Entity
        @Table(name = "inventario")
        public class Inventario {



            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;
            private  String tipo;
            private LocalDate fecha;
            private int   cantidad;
            private double costo;
            private double valor;
            @ManyToOne
            @JoinColumn(name = "producto_id", nullable = false)
            private Producto producto; // Relation with 'Producto' entity







        }
