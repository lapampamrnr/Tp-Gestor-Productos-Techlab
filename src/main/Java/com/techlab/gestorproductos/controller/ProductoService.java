package com.techlab.gestorproductos.service;

import com.techlab.gestorproductos.dto.ProductoRequest;
import com.techlab.gestorproductos.model.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> listarTodos();
    List<Producto> buscarPorNombre(String q);
    Producto obtenerPorId(Long id);
    Producto crear(ProductoRequest request);
    Producto actualizar(Long id, ProductoRequest request);
    void eliminar(Long id);
    Producto disminuirStock(Long id, int cantidad);
}
