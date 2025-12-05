package com.techlab.gestorproductos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;

    // Estados reales de un e-commerce: pendiente, confirmado, enviado, entregado, cancelado
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "pedido_id")
    private List<LineaPedido> lineas = new ArrayList<>();

    @NotNull
    private Double total;

    public Pedido() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.estado = EstadoPedido.PENDIENTE;
        this.total = 0.0;
    }

    // -----------------------------
    //  MÉTODOS DE NEGOCIO
    // -----------------------------

    public void agregarLinea(LineaPedido lp) {
        if (lp == null) return;
        lineas.add(lp);
        recalcularTotal();
    }

    public void eliminarLinea(LineaPedido lp) {
        if (lp == null) return;
        lineas.remove(lp);
        recalcularTotal();
    }

    public void cambiarEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void recalcularTotal() {
        this.total = lineas.stream()
                .mapToDouble(LineaPedido::getSubtotal)
                .sum();
        this.fechaActualizacion = LocalDateTime.now();
    }

    // -----------------------------
    //  GETTERS Y SETTERS
    // -----------------------------

    public Long getId() { return id; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public EstadoPedido getEstado() { return estado; }
    public List<LineaPedido> getLineas() { return lineas; }
    public Double getTotal() { return total; }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", total=" + total +
                '}';
    }
}
