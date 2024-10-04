package org.dam.database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDatabaseManager {

    private static final String DB_HOST = "127.0.0.1";
    private static final int DB_PORT = 5433;
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_NAME = "reservas";
    /**
     * Establece una conexión a la base de datos PostgreSQL
     *
     * @return Una referencia a la conexión a la base de datos.
     * @throws SQLException Si ocurre un error durante la conexión.
     */
    public static Connection connect() throws SQLException {


        try {
            // Establecer la conexión a la base de datos PostgreSQL
            String jdbcUrl = "jdbc:postgresql://localhost:" + DB_PORT + "/" + DB_NAME;
            Connection connection = DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD);

            if (connection == null) {
                System.out.println("No se pudo conectar a la base de datos PostgreSQL. Asegúrate de que la URL y las credenciales sean correctas.");
            }
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Error al conectar con la base de datos PostgreSQL.");
        }
    }

    /**
     * Cierra la conexión a la base de datos PostgreSQL y la sesión SSH.
     *
     * @param connection La conexión que se debe cerrar.
     * @throws SQLException Si ocurre un error durante la desconexión.
     */
    public static void disconnect(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Método para comprobar la conexión
    public static boolean checkConnection() throws SQLException {
        boolean connectedOK = false;
        Connection connection = null;

        while (!connectedOK) {
            try {
                connection = connect(); // Conectar a la base de datos
                if (connection != null && !connection.isClosed()) {
                    connectedOK = true;  // Si la conexión es exitosa
                }
            } catch (SQLException e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "No se pudo conectar, ¿Reintentar conexión?",
                        "Error conexión", JOptionPane.YES_NO_OPTION);
                if (result != JOptionPane.YES_OPTION) {
                    break; // Salir si el usuario no quiere reintentar
                }
            } finally {
                if (connectedOK && connection != null) {
                    disconnect(connection); // Cerrar la conexión solo si fue exitosa
                }
            }
        }
        return connectedOK;
    }

}


