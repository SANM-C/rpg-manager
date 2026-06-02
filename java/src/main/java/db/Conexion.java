package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para obtener la conexión a MySQL.
 * No pertenece ni al Model ni al View — es la capa de infraestructura.
 */
public class Conexion {

    // Parámetros de la cadena de conexión
    private static final String URL      = "jdbc:mysql://localhost:3306/rpg_manager";
    private static final String USUARIO  = "root";       // cambia según tu configuración
    private static final String PASSWORD = "1234";            // cambia según tu configuración

    /**
     * Retorna una conexión activa a la base de datos.
     * Si falla, lanza SQLException para que el Controller la maneje.
     */
    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
