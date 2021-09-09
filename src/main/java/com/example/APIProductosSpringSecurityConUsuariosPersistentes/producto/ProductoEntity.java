package com.example.APIProductosSpringSecurityConUsuariosPersistentes.producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="productos")
public class ProductoEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id_producto;
    private String nombre;
    private Double precio;
    private Integer stock;



    public ProductoEntity(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

}
