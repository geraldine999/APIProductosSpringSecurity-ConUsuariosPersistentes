package com.example.APIProductosSpringSecurityConUsuariosPersistentes.producto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoEntity> findAll() {
        return productoRepository.findAll();
    }


    public ProductoEntity findById(Integer id_producto) {
        return productoRepository.findById(id_producto).orElse(null);
    }

    public List<ProductoEntity> findByNombreContaining(String nombre) {
        return productoRepository.findByNombreContaining(nombre);
    }

    public void save(ProductoEntity producto) {
        if (producto.getId_producto() != null) {
            ProductoEntity productoExistente = productoRepository.findById(producto.getId_producto()).orElse(null);
            if (productoExistente != null) {
                if (producto.getNombre() != null) productoExistente.setNombre(producto.getNombre());
                if (producto.getPrecio() != null) productoExistente.setPrecio(producto.getPrecio());
                if (producto.getStock() != null) productoExistente.setStock(producto.getStock());
                productoRepository.save(productoExistente);
                return;
            } else producto.setId_producto(null);
        }
        productoRepository.save(producto);
    }

    public void deleteById(Integer id_producto) {
        productoRepository.deleteById(id_producto);
    }
}
