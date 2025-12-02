# 🚀 ComicVerse CRUD
API REST desarrollada con Spring Boot para gestionar cómics y pedidos,
incluyendo operaciones CRUD completas: crear, listar, buscar, actualizar y eliminar.

🧪 Datos iniciales
Se cargan automáticamente desde data.sql al iniciar el proyecto:
✔️ Cómics de prueba
✔️ Datos mínimos necesarios para probar el CRUD

🔗 Endpoints principales
📘 Comics — /api/comics
📘 Comics — /api/comics/buscar?nombre=Hulk(dato para probar la busqueda)
🛒 Pedidos — /api/pedidos
(usa DTOs para no exponer la entidad completa)

🧩 Validaciones
El proyecto utiliza validaciones personalizadas, no Bean Validation.
Los validadores principales son:
✔️ ComicValidator
✔️ PedidoValidator

🖼️ Imágenes / URLs de cómics
Las URL utilizadas para las portadas de los cómics se obtienen desde Postimg
https://postimg.cc/gallery/p29Gd5M

🧩 Datos de prueba:para probar en postman/h2 (POST) api/comics
{
"titulo": "Spider-Man",
"descripcion": "Comic de Spider-Man",
"editorial": "Marvel",
"autor": "Stan Lee",
"precio": 5200.0,
"stock": 12,
"oferta": true,
"imagen": "https://i.postimg.cc/pLVLDvQy/SPIDERMAN.jpg"
}
(POST)api/pedidos
{
"usuario": "andrea",
"detalles": [
{
"comic": { "id": 9 },
"cantidad": 2
}
]
}



👩‍💻 **Andrea Martínez** — 2025