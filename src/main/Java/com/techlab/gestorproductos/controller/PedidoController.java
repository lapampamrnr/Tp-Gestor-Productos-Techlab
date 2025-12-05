package com.techlab.gestorproductos.controller;

import com.techlab.gestorproductos.dto.PedidoRequest;
import com.techlab.gestorproductos.model.Pedido;
import com.techlab.gestorproductos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    // ======================
    // LISTAR TODOS LOS PEDIDOS
    // ======================
    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = service.listarTodos();

        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pedidos);
    }

    // ======================
    // OBTENER PEDIDO POR ID
    // ======================
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtener(@PathVariable Long id) {
        Pedido pedido = service.obtenerPorId(id);

        return ResponseEntity.ok(pedido);
    }

    // ======================
    // CREAR UN NUEVO PEDIDO
    // ======================
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody PedidoRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("El pedido debe contener al menos un producto.");
        }

        Pedido creado = service.crearPedido(request.getItems());

        return ResponseEntity.created(
                URI.create("/api/pedidos/" + creado.getId())
        ).body(creado);
    }

    // ======================
    // ELIMINAR UN PEDIDO POR ID
    // ======================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }

    // ======================
    // CAMBIAR ESTADO DE PEDIDO
    // ======================
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado
    ) {
        Pedido actualizado = service.actualizarEstado(id, estado);
        return ResponseEntity.ok(actualizado);
    }
}

