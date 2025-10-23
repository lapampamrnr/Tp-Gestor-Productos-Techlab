package com.techlab.gestorproductos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    private List<LineaPedido> lineas = new ArrayList<>();

    private Double total;

    public Pedido() {
        this.fecha = LocalDateTime.now();
        this.total = 0.0;
    }

    public Long getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public List<LineaPedido> getLineas() { return lineas; }
    public Double getTotal() { return total; }

    public void agregarLinea(LineaPedido lp) {
        lineas.add(lp);
        total += lp.getSubtotal();
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", fecha=" + fecha + ", total=" + total + '}';
    }
}
