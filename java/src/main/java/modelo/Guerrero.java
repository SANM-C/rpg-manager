package model;

public class Guerrero extends Personaje {

    public Guerrero(String nombre, int nivel) {
        super(nombre, "Guerrero", nivel, 150, 30);
    }

    public Guerrero(int id, String nombre, int nivel, int vida, int ataque) {
        super(id, nombre, "Guerrero", nivel, vida, ataque);
    }

    @Override
    public String habilidadEspecial() {
        return getNombre() + " usa ¡GOLPE BRUTAL! causando " + (getAtaque() * 2) + " de daño.";
    }
}
