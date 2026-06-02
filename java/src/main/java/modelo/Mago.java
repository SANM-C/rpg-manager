package model;

public class Mago extends Personaje {

    public Mago(String nombre, int nivel) {
        super(nombre, "Mago", nivel, 90, 50);
    }

    public Mago(int id, String nombre, int nivel, int vida, int ataque) {
        super(id, nombre, "Mago", nivel, vida, ataque);
    }

    @Override
    public String habilidadEspecial() {
        return getNombre() + " lanza ¡BOLA DE FUEGO! causando " + (getAtaque() * 3) + " de daño mágico.";
    }
}
