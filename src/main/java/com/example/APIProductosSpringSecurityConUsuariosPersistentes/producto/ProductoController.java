package com.example.APIProductosSpringSecurityConUsuariosPersistentes.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoEntity> listarProductos(){
        return productoService.findAll();
    }

    @GetMapping("id/{id_producto}")
    public ProductoEntity listarProductoPorId(@PathVariable Integer id_producto){
        return productoService.findById(id_producto);
    }

    @GetMapping("nombre/{nombre}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<ProductoEntity> listarProductosQueContenganElNombre(@PathVariable String nombre){
        return productoService.findByNombreContaining(nombre);
    }

    @PostMapping("guardar")
    public void modificarProductoExistente(@RequestBody ProductoEntity productoEntity){
        productoService.save(productoEntity);
    }

    @PutMapping(path = "guardar", consumes = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void crearProducto(@RequestBody ProductoEntity productoEntity){
        productoService.save(productoEntity);
    }

    @DeleteMapping("eliminar/id/{id_producto}")
    @PreAuthorize("hasAnyAuthority('producto:write')")
    public void eliminarProductoPorId(@PathVariable Integer id_producto){
        productoService.deleteById(id_producto);
    }





}
