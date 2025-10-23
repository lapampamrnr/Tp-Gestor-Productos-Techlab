package com.techlab.gestorproductos.dto;

import java.util.Map;

/**
 * DTO simple para crear pedidos.
 * Ejemplo JSON:
 * {
 *   "items": {
 *     "1": 2,
 *     "2": 1
 *   }
 * }
 */
public class PedidoRequest {
    private Map<Long, Integer> items;

    public PedidoRequest() {}

    public Map<Long, Integer> getItems() { return items; }
    public void setItems(Map<Long, Integer> items) { this.items = items; }
}
