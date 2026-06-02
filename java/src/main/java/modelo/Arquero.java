package model;

public class Arquero extends Personaje {

    public Arquero(String nombre, int nivel) {
        super(nombre, "Arquero", nivel, 110, 40);
    }

    public Arquero(int id, String nombre, int nivel, int vida, int ataque) {
        super(id, nombre, "Arquero", nivel, vida, ataque);
    }

    @Override
    public String habilidadEspecial() {
        return getNombre() + " dispara ¡LLUVIA DE FLECHAS! causando " + (getAtaque() * 2) + " de daño a distancia.";
    }
}
