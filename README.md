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
```

---

## Cómo ejecutar — Java (NetBeans)

1. Abrir NetBeans → **Open Project** → seleccionar la carpeta `java/`
2. Maven descarga el conector MySQL automáticamente (requiere internet)
3. Editar `db/Conexion.java`: ajustar `USUARIO` y `PASSWORD` de tu MySQL
4. Ejecutar `Main.java`

---

## Cómo ejecutar — Python (Google Colab)

1. Subir `python/rpg_manager.py` a Colab o pegar el contenido en una celda
2. Primera celda: `!pip install mysql-connector-python`
3. Editar `DB_CONFIG` con host, usuario y contraseña de tu MySQL
4. Ejecutar la celda → el menú interactivo aparece en la salida

> **Nota Colab:** si tu MySQL está en tu computador local, necesitas exponer el puerto con ngrok o usar una instancia en la nube.

---

## Base de datos

```bash
mysql -u root -p < db/schema.sql
```

Esto crea la base de datos `rpg_manager`, las tablas y carga 3 personajes de prueba.

---

## Convención de commits

```
[model][semana-11]      Clase Personaje con herencia Java
[model][semana-11]      Subclases Guerrero Mago Arquero Java
[db][semana-11]         schema.sql tablas personaje e inventario
[controller][semana-12] CRUD conectado a MySQL Java
[view][semana-12]       Menu consola con 6 opciones Java
[model][semana-12]      Clases MVC Python Colab
```
