# ============================================================
# RPG Manager — Python MVC
# Programación Multinivel 2026-I
# Google Colab: ejecutar celda por celda
# ============================================================

# ── Celda 1: Instalar conector MySQL ────────────────────────
# !pip install mysql-connector-python


# ============================================================
# MODEL — Las clases del dominio (no imprimen nada)
# ============================================================

class Personaje:
    """Clase padre de la jerarquía de personajes."""

    def __init__(self, nombre: str, tipo: str, nivel: int, vida: int, ataque: int, id: int = None):
        self.__id     = id
        self.__nombre = nombre
        self.__tipo   = tipo
        self.nivel    = nivel   # usa el @property para validar
        self.vida     = vida
        self.ataque   = ataque

    # ---- propiedades con @property (encapsulamiento Python) ----

    @property
    def id(self):
        return self.__id

    @id.setter
    def id(self, value):
        self.__id = value

    @property
    def nombre(self):
        return self.__nombre

    @nombre.setter
    def nombre(self, value):
        if value and value.strip():
            self.__nombre = value
        else:
            raise ValueError("El nombre no puede estar vacío.")

    @property
    def tipo(self):
        return self.__tipo

    @property
    def nivel(self):
        return self.__nivel

    @nivel.setter
    def nivel(self, value):
        if 1 <= value <= 100:
            self.__nivel = value
        else:
            raise ValueError("El nivel debe estar entre 1 y 100.")

    @property
    def vida(self):
        return self.__vida

    @vida.setter
    def vida(self, value):
        if value >= 0:
            self.__vida = value
        else:
            raise ValueError("La vida no puede ser negativa.")

    @property
    def ataque(self):
        return self.__ataque

    @ataque.setter
    def ataque(self, value):
        if value >= 0:
            self.__ataque = value
        else:
            raise ValueError("El ataque no puede ser negativo.")

    # Método polimórfico — cada subclase lo sobreescribe
    def habilidad_especial(self) -> str:
        raise NotImplementedError("Cada subclase debe implementar habilidad_especial()")

    def __str__(self):
        return (f"[{self.__id}] {self.__nombre} ({self.__tipo}) | "
                f"Nivel: {self.__nivel} | Vida: {self.__vida} | Ataque: {self.__ataque}")


class Guerrero(Personaje):
    def __init__(self, nombre, nivel, vida=150, ataque=30, id=None):
        super().__init__(nombre, "Guerrero", nivel, vida, ataque, id)

    def habilidad_especial(self):
        return f"{self.nombre} usa ¡GOLPE BRUTAL! causando {self.ataque * 2} de daño."


class Mago(Personaje):
    def __init__(self, nombre, nivel, vida=90, ataque=50, id=None):
        super().__init__(nombre, "Mago", nivel, vida, ataque, id)

    def habilidad_especial(self):
        return f"{self.nombre} lanza ¡BOLA DE FUEGO! causando {self.ataque * 3} de daño mágico."


class Arquero(Personaje):
    def __init__(self, nombre, nivel, vida=110, ataque=40, id=None):
        super().__init__(nombre, "Arquero", nivel, vida, ataque, id)

    def habilidad_especial(self):
        return f"{self.nombre} dispara ¡LLUVIA DE FLECHAS! causando {self.ataque * 2} de daño a distancia."

# ============================================================
# DB — Conexión a MySQL
# ============================================================

import mysql.connector
from mysql.connector import Error

DB_CONFIG = {
    "host":     "localhost",   # en Colab con proxy: cambia por la IP de tu BD
    "port":     3306,
    "database": "rpg_manager",
    "user":     "root",
    "password": "1234"             # cambia según tu configuración
}

def get_conexion():
    """Retorna una conexión activa. Lanza Error si falla."""
    return mysql.connector.connect(**DB_CONFIG)


# ============================================================
# CONTROLLER — Orquesta Model y BD
# ============================================================

def crear_personaje(nombre: str, tipo: str, nivel: int):
    tipos = {"Guerrero": Guerrero, "Mago": Mago, "Arquero": Arquero}
    if tipo not in tipos:
        print("Tipo inválido.")
        return

    p = tipos[tipo](nombre, nivel)
    sql = "INSERT INTO personaje (nombre, tipo, nivel, vida, ataque) VALUES (%s, %s, %s, %s, %s)"
    try:
        con = get_conexion()
        cur = con.cursor()
        cur.execute(sql, (p.nombre, p.tipo, p.nivel, p.vida, p.ataque))
        con.commit()
        print(f"✔ Personaje creado: {p.nombre}")
    except Error as e:
        print(f"✗ Error al conectar a la BD: {e}")
    finally:
        if con.is_connected():
            cur.close(); con.close()


def listar_personajes():
    sql = "SELECT * FROM personaje"
    personajes = []
    try:
        con = get_conexion()
        cur = con.cursor()
        cur.execute(sql)
        for row in cur.fetchall():
            personajes.append(_construir(row))
    except Error as e:
        print(f"✗ Error al listar: {e}")
    finally:
        if con.is_connected():
            cur.close(); con.close()
    return personajes


def buscar_por_nombre(nombre: str):
    sql = "SELECT * FROM personaje WHERE nombre = %s"
    try:
        con = get_conexion()
        cur = con.cursor()
        cur.execute(sql, (nombre,))
        row = cur.fetchone()
        if row:
            return _construir(row)
        else:
            print(f"Personaje '{nombre}' no encontrado.")
    except Error as e:
        print(f"✗ Error al buscar: {e}")
    finally:
        if con.is_connected():
            cur.close(); con.close()
    return None


def actualizar_nivel(id_personaje: int, nuevo_nivel: int):
    sql = "UPDATE personaje SET nivel = %s WHERE id = %s"
    try:
        con = get_conexion()
        cur = con.cursor()
        cur.execute(sql, (nuevo_nivel, id_personaje))
        con.commit()
        if cur.rowcount > 0:
            print("✔ Nivel actualizado.")
        else:
            print(f"✗ ID {id_personaje} no existe.")
    except Error as e:
        print(f"✗ Error al actualizar: {e}")
    finally:
        if con.is_connected():
            cur.close(); con.close()


def eliminar_personaje(id_personaje: int):
    sql = "DELETE FROM personaje WHERE id = %s"
    try:
        con = get_conexion()
        cur = con.cursor()
        cur.execute(sql, (id_personaje,))
        con.commit()
        if cur.rowcount > 0:
            print("✔ Personaje eliminado.")
        else:
            print(f"✗ ID {id_personaje} no existe.")
    except Error as e:
        print(f"✗ Error al eliminar: {e}")
    finally:
        if con.is_connected():
            cur.close(); con.close()


def _construir(row):
    """Helper: convierte una fila de BD en objeto del Model."""
    id_, nombre, tipo, nivel, vida, ataque = row
    tipos = {"Guerrero": Guerrero, "Mago": Mago, "Arquero": Arquero}
    cls = tipos.get(tipo, Guerrero)
    return cls(nombre, nivel, vida, ataque, id=id_)


# ============================================================
# VIEW — Menú de consola (no toca la BD, solo llama al controller)
# ============================================================

def mostrar_menu():
    while True:
        print("\n========== RPG Manager (Python) ==========")
        print("1. Crear personaje")
        print("2. Listar personajes")
        print("3. Buscar personaje por nombre")
        print("4. Actualizar nivel")
        print("5. Eliminar personaje")
        print("6. Ver habilidad especial")
        print("0. Salir")
        opcion = input("Elige una opción: ").strip()

        if opcion == "1":
            nombre = input("Nombre: ").strip()
            tipo   = input("Tipo (Guerrero / Mago / Arquero): ").strip().capitalize()
            nivel  = int(input("Nivel (1-100): "))
            crear_personaje(nombre, tipo, nivel)

        elif opcion == "2":
            lista = listar_personajes()
            if lista:
                for p in lista:
                    print(p)
            else:
                print("No hay personajes.")

        elif opcion == "3":
            nombre = input("Nombre a buscar: ").strip()
            p = buscar_por_nombre(nombre)
            if p:
                print("Encontrado:", p)

        elif opcion == "4":
            id_p  = int(input("ID del personaje: "))
            nivel = int(input("Nuevo nivel: "))
            actualizar_nivel(id_p, nivel)

        elif opcion == "5":
            id_p = int(input("ID a eliminar: "))
            eliminar_personaje(id_p)

        elif opcion == "6":
            nombre = input("Nombre del personaje: ").strip()
            p = buscar_por_nombre(nombre)
            if p:
                print(p.habilidad_especial())

        elif opcion == "0":
            print("¡Hasta luego!")
            break
        else:
            print("Opción inválida.")


# ============================================================
# EJECUTAR
# ============================================================
mostrar_menu()
