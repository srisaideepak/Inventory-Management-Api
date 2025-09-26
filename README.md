# Inventory Management System API (Spring Boot + Swagger)

A backend-heavy API to track products in a warehouse. Implemented using **Java 17** and **Spring Boot 3.5.x**. Uses **MySQL** (or H2 in-memory DB for quick testing). Fully documented using **Swagger (springdoc-openapi)**.


###Working Video Link for Project
- https://www.loom.com/share/ef034ad0f5fa4e3fa54072b1e096fd4d?sid=e34d0126-8a04-4561-bf9c-200ba5d0d672


### Product Management
- Full CRUD for products:
    - `id`, `name`, `description`, `price`, `stockQuantity`, `lowStockThreshold`
- Validation:
    - Cannot create/update product with invalid fields (empty name, negative price, negative stock)
    - Proper error messages for missing or invalid data

### Stock Management
- Two endpoints to manage stock:
    - Increase stock: `POST /products/{id}/stock/increase`
    - Decrease stock: `POST /products/{id}/stock/decrease`
        - Returns **400 Bad Request** if insufficient stock

### Bonus
- `lowStockThreshold` field to monitor products with low inventory
- Endpoint `GET /products/low-stock` to list products below threshold

### Swagger API Documentation
- Accessible at: `http://localhost:8080/swagger-ui.html`
- Explore all endpoints with request/response examples
- Interactive testing without using Postman or curl

---

## Tech Stack

- Java 17
- Spring Boot (Web, Data JPA)
- MySQL / H2 in-memory DB
- Lombok
- Springdoc OpenAPI (Swagger)
- JUnit 5 + Spring Boot Test


## Endpoints

### Product Management
- `POST /products` — create product
- `GET /products` — list all products
- `GET /products/{id}` — get product by ID
- `PUT /products/{id}` — update product (partial updates via `ProductUpdateDTO`)
- `DELETE /products/{id}` — delete product

### Stock
- `POST /products/{id}/stock/increase` — body: `{ "amount": number }`
- `POST /products/{id}/stock/decrease` — body: `{ "amount": number }`
    - Returns 400 if trying to remove more stock than available

### Bonus
- `GET /products/low-stock` — list products where `lowStockThreshold` is set and `stockQuantity < lowStockThreshold`

