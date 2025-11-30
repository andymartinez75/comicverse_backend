# 🚀 ComicVerse CRUD
API REST desarrollada con Spring Boot para gestionar cómics y pedidos,
incluyendo operaciones CRUD completas: crear, listar, buscar, actualizar y eliminar.

🧪 Datos iniciales
Se cargan automáticamente desde data.sql al iniciar el proyecto:
✔️ Cómics de prueba
✔️ Datos mínimos necesarios para probar el CRUD

🔗 Endpoints principales
📘 Comics — /api/comics
🛒 Pedidos — /api/pedidos
(usa DTOs para no exponer la entidad completa)

🧩 Validaciones
El proyecto utiliza validaciones personalizadas, no Bean Validation.
Los validadores principales son:
✔️ ComicValidator
✔️ PedidoValidator

🧩 Uso de DTOs para evitar ciclo infinito con JSON 





👩‍💻 **Andrea Martínez** — 2025