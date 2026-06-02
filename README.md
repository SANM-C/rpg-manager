# RPG Manager — Programación Multinivel 2026-I

Aplicación de consola con arquitectura **MVC** implementada en Java y Python, conectada a MySQL.

---

## Estructura del repositorio

```
rpg-manager/
├── java/
│   ├── pom.xml
│   └── src/main/java/
│       ├── Main.java
│       ├── model/         ← Personaje, Guerrero, Mago, Arquero
│       ├── view/          ← MenuView (solo UI)
│       ├── controller/    ← PersonajeController (lógica)
│       └── db/            ← Conexion.java
├── python/
│   └── rpg_manager.py     ← todo en un solo archivo para Colab
├── db/
│   └── schema.sql         ← CREATE TABLE + datos de prueba
└── README.md


## Cómo ejecutar — Java (NetBeans)

1. Abrir NetBeans → **Open Project** → seleccionar la carpeta `java/`
2. Maven descarga el conector MySQL automáticamente (requiere internet)
3. Editar `db/Conexion.java`: ajustar `USUARIO` y `PASSWORD` de tu MySQL
4. Ejecutar `Main.java`

> **Nota Colab:** si tu MySQL está en tu computador local, necesitas exponer el puerto con ngrok o usar una instancia en la nube.


## Base de datos

```bash
mysql -u root -p < db/schema.sql


Esto crea la base de datos `rpg_manager`, las tablas y carga 3 personajes de prueba.
