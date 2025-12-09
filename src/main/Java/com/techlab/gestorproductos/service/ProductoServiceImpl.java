package com.techlab.gestorproductos.service.impl;

import com.techlab.gestorproductos.dto.ProductoRequest;
import com.techlab.gestorproductos.exception.ResourceNotFoundException;
import com.techlab.gestorproductos.model.Producto;
import com.techlab.gestorproductos.repository.ProductoRepository;
import com.techlab.gestorproductos.service.ProductoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repo;

    public ProductoServiceImpl(ProductoRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Producto> listarTodos() {
        return repo.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
    }

    @Override
    @Transactional
    public Producto crear(ProductoRequest req) {
        Producto p = new Producto();
        p.setNombre(req.getNombre());
        p.setPrecio(req.getPrecio());
        p.setStock(req.getStock());
        p.setCategoria(req.getCategoria());
        // si tu entidad tiene campos descripcion/imagen, setealos:
        try {
            // reflect optional setters if exist
            p.getClass().getDeclaredField("descripcion");
            p.getClass().getDeclaredField("imagenUrl");
            // si existen, setear via reflection no es ideal; mejor adaptar entidad
        } catch (NoSuchFieldException ignored) {}
        return repo.save(p);
    }

    @Override
    @Transactional
    public Producto actualizar(Long id, ProductoRequest req) {
        Producto p = obtenerPorId(id);
        p.setNombre(req.getNombre());
        p.setPrecio(req.getPrecio());
        p.setStock(req.getStock());
        p.setCategoria(req.getCategoria());
        return repo.save(p);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Producto p = obtenerPorId(id);
        repo.delete(p);
    }

    @Override
    @Transactional
    public Producto disminuirStock(Long id, int cantidad) {
        Producto p = obtenerPorId(id);
        if (cantidad < 0) throw new IllegalArgumentException("Cantidad inválida");
        if (p.getStock() < cantidad) throw new IllegalArgumentException("Stock insuficiente");
        p.setStock(p.getStock() - cantidad);
        return repo.save(p);
    }

    @Override
    public List<Producto> buscarPorNombre(String q) {
        return repo.findByNombreContainingIgnoreCase(q);
    }
}
