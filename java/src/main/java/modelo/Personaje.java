package model;

// Clase padre - toda la jerarquía hereda de aquí
public abstract class Personaje {

    // private = encapsulamiento (no se accede directo desde fuera)
    private int    id;
    private String nombre;
    private String tipo;
    private int    nivel;
    private int    vida;
    private int    ataque;

    public Personaje(String nombre, String tipo, int nivel, int vida, int ataque) {
        this.nombre = nombre;
        this.tipo   = tipo;
        setNivel(nivel);   // usamos setter para aplicar validación
        setVida(vida);
        setAtaque(ataque);
    }

    // Constructor con id (para cuando se lee desde la BD)
    public Personaje(int id, String nombre, String tipo, int nivel, int vida, int ataque) {
        this(nombre, tipo, nivel, vida, ataque);
        this.id = id;
    }

    // --- Método polimórfico (cada subclase lo sobreescribe) ---
    public abstract String habilidadEspecial();

    // --- Getters ---
    public int    getId()     { return id; }
    public String getNombre() { return nombre; }
    public String getTipo()   { return tipo; }
    public int    getNivel()  { return nivel; }
    public int    getVida()   { return vida; }
    public int    getAtaque() { return ataque; }

    // --- Setters con validación ---
    public void setId(int id)         { this.id = id; }
    public void setNombre(String n)   { if (n != null && !n.isBlank()) this.nombre = n; }
    public void setTipo(String t)     { this.tipo = t; }

    public void setNivel(int nivel) {
        if (nivel >= 1 && nivel <= 100) this.nivel = nivel;
        else throw new IllegalArgumentException("Nivel debe estar entre 1 y 100.");
    }

    public void setVida(int vida) {
        if (vida >= 0) this.vida = vida;
        else throw new IllegalArgumentException("La vida no puede ser negativa.");
    }

    public void setAtaque(int ataque) {
        if (ataque >= 0) this.ataque = ataque;
        else throw new IllegalArgumentException("El ataque no puede ser negativo.");
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) | Nivel: %d | Vida: %d | Ataque: %d",
                id, nombre, tipo, nivel, vida, ataque);
    }
}
