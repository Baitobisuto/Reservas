package org.dam.dao;

import org.dam.database.SQLDatabaseManager;
import org.dam.models.TipoMenuModel;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TipoMenuDAO {
    private Connection connection;


    private boolean initDBConnection() {
        try {
            connection = SQLDatabaseManager.connect();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos");
        }
        return false;
    }

    private boolean closeDBConnection() {
        try {
            SQLDatabaseManager.disconnect(connection);
            return true;
        } catch (SQLException e) {
            System.err.println("Error al desconectar con la base de datos");
        }
        return false;
    }

    public ArrayList<TipoMenuModel> getTipoMenu() throws SQLException {
        ArrayList<TipoMenuModel> menuNuevo = new ArrayList<>();
        if (!initDBConnection()) {
            System.out.println("Error al conectar con la base de datos");
        }
        try {
            String query = "SELECT * FROM menu_nuevo";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            {
                while (rs.next()) {
                    TipoMenuModel menu = new TipoMenuModel();
                    menu.setId_menu(rs.getInt("id_menu"));
                    menu.setNombre(rs.getString("tipo_menu"));
                    menuNuevo.add(menu);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los datos");
        } finally {
            closeDBConnection();
        }
        return menuNuevo;
    }

}
