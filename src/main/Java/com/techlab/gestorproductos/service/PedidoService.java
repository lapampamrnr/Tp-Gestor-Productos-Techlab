package com.techlab.gestorproductos.service;

import com.techlab.gestorproductos.exception.ResourceNotFoundException;
import com.techlab.gestorproductos.model.LineaPedido;
import com.techlab.gestorproductos.model.Pedido;
import com.techlab.gestorproductos.model.Producto;
import com.techlab.gestorproductos.repository.PedidoRepository;
import com.techlab.gestorproductos.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final ProductoRepository productoRepo;

    public PedidoService(PedidoRepository pedidoRepo, ProductoRepository productoRepo) {
        this.pedidoRepo = pedidoRepo;
        this.productoRepo = productoRepo;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepo.findAll();
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado id=" + id));
    }

    /**
     * Crear pedido a partir de mapa <idProducto -> cantidad>.
     * Valida stock, descuenta y guarda pedido.
     */
    @Transactional
    public Pedido crearPedido(Map<Long, Integer> items) {
        Pedido pedido = new Pedido();
        for (Map.Entry<Long, Integer> e : items.entrySet()) {
            Long idProd = e.getKey();
            Integer cant = e.getValue();
            if (cant == null || cant <= 0) throw new IllegalArgumentException("Cantidad inválida para producto " + idProd);

            Producto prod = productoRepo.findById(idProd).orElseThrow(() -> new ResourceNotFoundException("Producto no existe id=" + idProd));
            if (prod.getStock() < cant) throw new IllegalStateException("Stock insuficiente para " + prod.getNombre());

            // Descontar stock y guardar producto actualizado
            prod.setStock(prod.getStock() - cant);
            productoRepo.save(prod);

            LineaPedido lp = new LineaPedido(prod, cant);
            pedido.agregarLinea(lp);
        }
        return pedidoRepo.save(pedido);
    }

    public void eliminarPedido(Long id) {
        Pedido p = obtenerPorId(id);
        pedidoRepo.delete(p);
    }
}
