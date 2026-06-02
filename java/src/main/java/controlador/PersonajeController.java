package controller;

import db.Conexion;
import model.Arquero;
import model.Guerrero;
import model.Mago;
import model.Personaje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller — orquesta Model y View.
 * Es el único que habla con la BD y con las clases del Model.
 * La View solo muestra y pide datos; nunca toca la BD directamente.
 */
public class PersonajeController {

    // ------------------------------------------------------------------ CREATE
    public void crearPersonaje(String nombre, String tipo, int nivel) {
        // 1. Crear objeto del Model según el tipo
        Personaje p;
        switch (tipo) {
            case "Guerrero" -> p = new Guerrero(nombre, nivel);
            case "Mago"     -> p = new Mago(nombre, nivel);
            case "Arquero"  -> p = new Arquero(nombre, nivel);
            default -> {
                System.out.println("Tipo inválido.");
                return;
            }
        }

        // 2. Persistir en MySQL
        String sql = "INSERT INTO personaje (nombre, tipo, nivel, vida, ataque) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTipo());
            ps.setInt(3,    p.getNivel());
            ps.setInt(4,    p.getVida());
            ps.setInt(5,    p.getAtaque());
            ps.executeUpdate();
            System.out.println("✔ Personaje creado: " + p.getNombre());

        } catch (SQLException e) {
            System.out.println("✗ Error al conectar a la BD: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------ READ (listar todos)
    public List<Personaje> listarPersonajes() {
        List<Personaje> lista = new ArrayList<>();
        String sql = "SELECT * FROM personaje";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(construirPersonaje(rs));
            }

        } catch (SQLException e) {
            System.out.println("✗ Error al leer personajes: " + e.getMessage());
        }
        return lista;
    }

    // ------------------------------------------------------------------ READ (buscar por nombre)
    public Personaje buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM personaje WHERE nombre = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return construirPersonaje(rs);
            else System.out.println("Personaje '" + nombre + "' no encontrado.");

        } catch (SQLException e) {
            System.out.println("✗ Error al buscar: " + e.getMessage());
        }
        return null;
    }

    // ------------------------------------------------------------------ UPDATE
    public void actualizarNivel(int id, int nuevoNivel) {
        String sql = "UPDATE personaje SET nivel = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevoNivel);
            ps.setInt(2, id);
            int filas = ps.executeUpdate();
            if (filas > 0) System.out.println("✔ Nivel actualizado.");
            else           System.out.println("✗ ID " + id + " no existe.");

        } catch (SQLException e) {
            System.out.println("✗ Error al actualizar: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------ DELETE
    public void eliminarPersonaje(int id) {
        String sql = "DELETE FROM personaje WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) System.out.println("✔ Personaje eliminado.");
            else           System.out.println("✗ ID " + id + " no existe.");

        } catch (SQLException e) {
            System.out.println("✗ Error al eliminar: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------ helper privado
    private Personaje construirPersonaje(ResultSet rs) throws SQLException {
        int    id     = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String tipo   = rs.getString("tipo");
        int    nivel  = rs.getInt("nivel");
        int    vida   = rs.getInt("vida");
        int    ataque = rs.getInt("ataque");

        return switch (tipo) {
            case "Guerrero" -> new Guerrero(id, nombre, nivel, vida, ataque);
            case "Mago"     -> new Mago    (id, nombre, nivel, vida, ataque);
            case "Arquero"  -> new Arquero (id, nombre, nivel, vida, ataque);
            default         -> new Guerrero(id, nombre, nivel, vida, ataque);
        };
    }
}
