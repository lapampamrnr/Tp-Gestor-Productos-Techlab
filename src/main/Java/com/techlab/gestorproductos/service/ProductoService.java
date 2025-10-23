package com.techlab.gestorproductos.service;

import com.techlab.gestorproductos.exception.ResourceNotFoundException;
import com.techlab.gestorproductos.model.Producto;
import com.techlab.gestorproductos.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<Producto> listarTodos() {
        return repo.findAll();
    }

    public Producto crear(Producto p) {
        return repo.save(p);
    }

    public Producto obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado id=" + id));
    }

    public Producto actualizar(Long id, Producto datos) {
        Producto p = obtenerPorId(id);
        p.setNombre(datos.getNombre());
        p.setPrecio(datos.getPrecio());
        p.setStock(datos.getStock());
        return repo.save(p);
    }

    public void eliminar(Long id) {
        Producto p = obtenerPorId(id);
        repo.delete(p);
    }

    @Transactional
    public Producto disminuirStock(Long id, int cantidad) {
        Producto p = obtenerPorId(id);
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser mayor a 0");
        if (p.getStock() < cantidad) throw new IllegalStateException("Stock insuficiente para producto id=" + id);
        p.setStock(p.getStock() - cantidad);
        return repo.save(p);
    }

    public List<Producto> buscarPorNombre(String q) {
        return repo.findByNombreContainingIgnoreCase(q);
    }
}
