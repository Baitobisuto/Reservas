package org.dam.dao;

import org.dam.database.SQLDatabaseManager;
import org.dam.models.TipoAlergiaModel;
import org.dam.models.TipoMenuModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TipoAlergiaDAO {
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

    public ArrayList<TipoAlergiaModel> getTipoAlergia() throws SQLException {
        ArrayList<TipoAlergiaModel> alergiasList = new ArrayList<>();
        if (!initDBConnection()) {
            System.out.println("Error al conectar con la base de datos");
        }
        try {
            String query = "SELECT * FROM alergias";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            {
                while (rs.next()) {
                    TipoAlergiaModel alergia = new TipoAlergiaModel();
                    alergia.setId_alergia(rs.getInt("id_alergia"));
                    alergia.setDescripcion(rs.getString("descripcion"));
                    alergiasList.add(alergia);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los datos");
        } finally {
            closeDBConnection();
        }
        return alergiasList;
    }


}
