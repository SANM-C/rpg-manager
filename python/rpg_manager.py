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