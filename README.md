# Gestor Productos TechLab

Proyecto final integrador — API REST con Spring Boot + JPA + MySQL  
Autor: Marcela Adriana Serrano (reemplazá por tu nombre si querés)

## Resumen
API para gestionar productos y pedidos. Funcionalidad:
- CRUD productos (nombre, precio, stock)
- CRUD pedidos (pedido con muchas líneas; valida stock y descuenta)
- Persistencia con MySQL (Docker optional)
- Endpoints REST JSON

## Requisitos
- Java 17+
- Maven
- Docker (opcional, para MySQL) o MySQL instalado

## Archivos principales
- `src/main/java/com/techlab/gestorproductos/...` (código)
- `application.properties` (config DB)
- `docker-compose.yml` (levanta MySQL)
- `data.sql` (datos de ejemplo)

## Levantar con Docker (recomendado)
1. `docker-compose up -d`
2. Esperar a que MySQL arranque (unos segundos)
3. `mvn clean package`
4. `mvn spring-boot:run`
La API correrá en `http://localhost:8080`.

## Configuración sin Docker
- Creá la base `techlab_db` en MySQL.
- Editá `src/main/resources/application.properties` con tu usuario/clave.
- Ejecutá `mvn spring-boot:run`.

## Endpoints y ejemplos (cURL)

### Productos
- Listar:
```bash
curl http://localhost:8080/api/productos
Crear:

bash
Copiar código
curl -X POST http://localhost:8080/api/productos \
 -H "Content-Type: application/json" \
 -d '{"nombre":"Monitor 24","precio":120000,"stock":5}'
Obtener:

bash
Copiar código
curl http://localhost:8080/api/productos/1
Actualizar:

bash
Copiar código
curl -X PUT http://localhost:8080/api/productos/1 \
 -H "Content-Type: application/json" \
 -d '{"nombre":"Monitor 27","precio":150000,"stock":4}'
Eliminar:

bash
Copiar código
curl -X DELETE http://localhost:8080/api/productos/1
Descontar stock:

bash
Copiar código
curl -X POST "http://localhost:8080/api/productos/2/descontar?cantidad=2"
Pedidos
Crear pedido (body JSON PedidoRequest):

bash
Copiar código
curl -X POST http://localhost:8080/api/pedidos \
 -H "Content-Type: application/json" \
 -d '{"items":{"1":2,"2":1}}'
Listar pedidos:

bash
Copiar código
curl http://localhost:8080/api/pedidos
Obtener pedido:

bash
Copiar código
curl http://localhost:8080/api/pedidos/1
Notas
La creación de pedido es transaccional: valida stock y descuenta.

Los endpoints devuelven mensajes de error claros mediante GlobalExceptionHandler.

El proyecto se puede abrir e importar en IntelliJ/Eclipse como proyecto Maven.
