package com.techlab.gestorproductos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "lineas_pedido")
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Un producto puede estar en muchas líneas de pedidos
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    @NotNull
    private Producto producto;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer cantidad;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Double subtotal;

    public LineaPedido() {}

    public LineaPedido(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        recalcularSubtotal();
    }

    // -----------------------------
    //  MÉTODOS DE NEGOCIO
    // -----------------------------

    public void recalcularSubtotal() {
        if (producto != null && cantidad != null) {
            this.subtotal = producto.getPrecio() * cantidad;
        }
    }

    public void cambiarCantidad(Integer nuevaCantidad) {
        this.cantidad = nuevaCantidad;
        recalcularSubtotal();
    }

    // -----------------------------
    //  GETTERS Y SETTERS
    // -----------------------------

    public Long getId() { return id; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) {
        this.producto = producto;
        recalcularSubtotal();
    }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        recalcularSubtotal();
    }

    public Double getSubtotal() { return subtotal; }

    @Override
    public String toString() {
        return "LineaPedido{" +
                "id=" + id +
                ", producto=" + (producto != null ? producto.getNombre() : "N/A") +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                '}';
    }
}
