package com.techlab.gestorproductos.controller;

import com.techlab.gestorproductos.dto.ProductoRequest;
import com.techlab.gestorproductos.model.Producto;
import com.techlab.gestorproductos.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar(@RequestParam(value = "q", required = false) String q) {
        List<Producto> resultado = (q == null || q.isBlank())
                ? productoService.listarTodos()
                : productoService.buscarPorNombre(q);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoRequest request) {
        Producto creado = productoService.crear(request);
        return ResponseEntity.created(URI.create("/api/productos/" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id,
                                               @Valid @RequestBody ProductoRequest request) {
        Producto actualizado = productoService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/descontar")
    public ResponseEntity<Producto> descontarStock(@PathVariable Long id,
                                                   @RequestParam int cantidad) {
        Producto actualizado = productoService.disminuirStock(id, cantidad);
        return ResponseEntity.ok(actualizado);
    }
}
