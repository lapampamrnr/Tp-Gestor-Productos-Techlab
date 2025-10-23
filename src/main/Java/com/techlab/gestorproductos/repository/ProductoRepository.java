package com.techlab.gestorproductos.repository;

import com.techlab.gestorproductos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombreIgnoreCase(String nombre);
    List<Producto> findByNombreContainingIgnoreCase(String fragment);
}
