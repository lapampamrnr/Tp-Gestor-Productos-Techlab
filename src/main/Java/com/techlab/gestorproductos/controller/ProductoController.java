package com.techlab.gestorproductos.controller;

import com.techlab.gestorproductos.model.Producto;
import com.techlab.gestorproductos.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST encargado de gestionar las operaciones relacionadas
 * con los productos dentro del sistema de E-commerce TechLab.
 *
 * Funcionalidades:
 * - Listar productos
 * - Buscar productos por nombre
 * - Obtener producto por ID
 * - Crear nuevos productos
 * - Actualizar productos existentes
 * - Eliminar productos
 * - Descontar stock (usado al crear pedidos)
 *
 * Este controlador cumple con los requisitos de la Entrega Final (Clase 16).
 *
 * @author Adriana Serrano
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    /**
     * Lista todos los productos o realiza una búsqueda por nombre.
     *
     * @param q parámetro opcional para buscar productos cuyo nombre coincida
     * @return Lista de productos encontrados
     */
    @GetMapping
    public ResponseEntity<List<Producto>> listar(
            @RequestParam(value = "q", required = false) String q) {

        List<Producto> productos = (q == null || q.isBlank())
                ? service.listarTodos()
                : service.buscarPorNombre(q.trim());

        return ResponseEntity.ok(productos);
    }

    /**
     * Obtiene un producto según su ID.
     *
     * @param id identificador del producto
     * @return Producto encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        Producto producto = service.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    /**
     * Crea un nuevo producto a partir del JSON enviado.
     *
     * @param producto datos validados del nuevo producto
     * @return Producto creado junto con su URI
     */
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        Producto creado = service.crear(producto);

        return ResponseEntity.created(
                URI.create("/api/productos/" + creado.getId())
        ).body(creado);
    }

    /**
     * Actualiza un producto existente.
     *
     * @param id       ID del producto a actualizar
     * @param producto datos actualizados
     * @return Producto actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {

        Producto actualizado = service.actualizar(id, producto);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id identificador del producto a eliminar
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Descuenta stock de un producto (se usa durante la creación de pedidos).
     *
     * @param id       ID del producto
     * @param cantidad cantidad a descontar del stock
     * @return Producto con stock actualizado
     */
    @PostMapping("/{id}/descontar")
    public ResponseEntity<Producto> descontarStock(
            @PathVariable Long id,
            @RequestParam int cantidad) {

        if (cantidad <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        Producto actualizado = service.disminuirStock(id, cantidad);
        return ResponseEntity.ok(actualizado);
    }
}
