package org.dam.dao;

import org.dam.database.SQLDatabaseManager;
import org.dam.models.ReservasModel;
import org.dam.models.TipoMenuModel;
import org.postgresql.util.PSQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservasDAO {

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

    public boolean createReserva(ReservasModel reserva) throws SQLException {
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la base de datos");
        }
        try {
            String SQL = "SELECT COUNT(*) FROM reservas WHERE nombre_cliente = ?";
            PreparedStatement ps1 = connection.prepareStatement(SQL);
            ps1.setString(1, reserva.getNombre_cliente());
            ResultSet rs = ps1.executeQuery();
            if (rs.next() && rs.getInt(1) >= 1) {
                throw new SQLException("Ya existe una reserva con el nombre: " + reserva.getNombre_cliente());

            } //MIRAR, TENGO QUE CAMBIAR DE ESQUEMA PARA USAR LA BD
            String insertSQL = "INSERT INTO reservas (nombre_cliente,fecha_reserva,hora_reserva,num_personas,telefono_cliente,email_cliente,notas_adicionales) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(insertSQL);
            ps.setString(1, reserva.getNombre_cliente());
            ps.setDate(2, java.sql.Date.valueOf(reserva.getDate()));
            ps.setTime(3, java.sql.Time.valueOf(reserva.getHora()));
            ps.setInt(4, reserva.getNum_personas());
            ps.setInt(5, reserva.getTelefono_cliente());
            ps.setString(6, reserva.getEmail_cliente());
            ps.setString(7, reserva.getNotas_adicionales());


            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (PSQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new SQLException("Ya existe una reserva con el id: " + reserva.getId());
            } else {
                e.printStackTrace();
                throw new SQLException("Error al crear la reserva");

            }
        } finally {
            closeDBConnection();
        }
    }

    public ArrayList<ReservasModel> getReserva(int limit,int offset) throws SQLException {
        ArrayList<ReservasModel> reservas = new ArrayList<>();
        if (!initDBConnection()) {
            System.out.println("Error al conectar con la base de datos");
        }
        try {
            String query = "SELECT r.*, m.tipo_menu, a.descripcion AS alergia\n" +
                    "FROM reservas r\n" +
                    "LEFT JOIN menu_nuevo m ON r.id_menu = m.id_menu\n" +
                    "LEFT JOIN alergias a ON r.id_reserva = a.id_reserva\n" +
                    "LIMIT ? OFFSET ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            {
                while (rs.next()) {
                    ReservasModel reserva = new ReservasModel();
                    reserva.setId(rs.getInt("id_reserva"));
                    reserva.setNombre_cliente(rs.getString("nombre_cliente"));
                    reserva.setDate(rs.getDate("fecha_reserva").toLocalDate());
                    reserva.setHora(rs.getTime("hora_reserva").toLocalTime());
                    reserva.setNum_personas(rs.getInt("num_personas"));
                    reserva.setNotas_adicionales(rs.getString("notas_adicionales"));
                    reserva.setTipo_menu(rs.getString("tipo_menu"));
                    reserva.setDescripcion_alergia(rs.getString("alergia"));
                    //reserva.setTelefono_cliente(rs.getInt("telefono_cliente"));
                    //reserva.setEmail_cliente(rs.getString("email_cliente"));
                    //reserva.setId_menu(rs.getInt("id_menu"));
                    reservas.add(reserva);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error en el método getReserva de ReservasDAO");
        } finally {
            closeDBConnection();
        }
        return reservas;
    }


    public ArrayList<ReservasModel> getReservaById(int id) throws SQLException {
        ArrayList<ReservasModel> reservas = new ArrayList<>();
        if (!initDBConnection()) {
            System.out.println("Error al conectar con la base de datos");
        }
        try {
            String query = "SELECT r.*, m.tipo_menu, a.descripcion AS alergia\n" +
                    "FROM reservas r\n" +
                    "LEFT JOIN menu_nuevo m ON r.id_menu = m.id_menu\n" +
                    "LEFT JOIN alergias a ON r.id_reserva = a.id_reserva\n" +
                    "WHERE r.id_reserva = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            {
                while (rs.next()) {
                    ReservasModel reserva = new ReservasModel();
                    reserva.setId(rs.getInt("id_reserva"));
                    reserva.setNombre_cliente(rs.getString("nombre_cliente"));
                    reserva.setDate(rs.getDate("fecha_reserva").toLocalDate());
                    reserva.setHora(rs.getTime("hora_reserva").toLocalTime());
                    reserva.setNum_personas(rs.getInt("num_personas"));
                    reserva.setTelefono_cliente(rs.getInt("telefono_cliente"));
                    reserva.setEmail_cliente(rs.getString("email_cliente"));
                    reserva.setNotas_adicionales(rs.getString("notas_adicionales"));
                    reserva.setTipo_menu(rs.getString("tipo_menu"));
                    reserva.setDescripcion_alergia(rs.getString("alergia"));
                    reservas.add(reserva);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los datos");
        } finally {
            closeDBConnection();
        }
        return reservas;
    }


    public ArrayList<ReservasModel> getReservasByName(String name) throws SQLException {
        ArrayList<ReservasModel> reservasList = new ArrayList<>();
        if (!initDBConnection()) {
            throw new SQLException("ERROR AL CONECTAR CON LA BASE DE DATOS");
        }
        try {
            String query = "SELECT r.*, m.tipo_menu, a.descripcion AS alergia\n" +
                    "FROM reservas r\n" +
                    "LEFT JOIN menu_nuevo m ON r.id_menu = m.id_menu\n" +
                    "LEFT JOIN alergias a ON r.id_reserva = a.id_reserva\n" +
                    "WHERE LOWER(r.nombre_cliente) LIKE LOWER (?)\n";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + name + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ReservasModel r = new ReservasModel();
                r.setId(resultSet.getInt("id_reserva"));
                r.setNombre_cliente(resultSet.getString("nombre_cliente"));
                r.setDate(resultSet.getDate("fecha_reserva").toLocalDate());
                r.setHora(resultSet.getTime("hora_reserva").toLocalTime());
                r.setNum_personas(resultSet.getInt("num_personas"));
                r.setTelefono_cliente(resultSet.getInt("telefono_cliente"));
                r.setEmail_cliente(resultSet.getString("email_cliente"));
                r.setNotas_adicionales(resultSet.getString("notas_adicionales"));
                r.setTipo_menu(resultSet.getString("tipo_menu"));
                r.setDescripcion_alergia(resultSet.getString("alergia"));
                reservasList.add(r);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al consultar la reserva" + e.getMessage());
        } finally {
            closeDBConnection();
        }
        return reservasList;
    }


    public ArrayList<ReservasModel> getRersevasByDate(LocalDate fecha1, LocalDate fecha2) throws SQLException {
        ArrayList<ReservasModel> reservaList = new ArrayList<>();
        if (!initDBConnection()) {
            throw new SQLException("ERROR AL CONECTAR CON LA BASE DE DATOS");
        }
        try {
            String query = "SELECT r.*, m.tipo_menu, a.descripcion AS alergia\n" +
                    "FROM reservas r\n" +
                    "LEFT JOIN menu_nuevo m ON r.id_menu = m.id_menu\n" +
                    "LEFT JOIN alergias a ON r.id_reserva = a.id_reserva\n" +
                    "WHERE r.fecha_reserva BETWEEN ? AND ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(fecha1));
            preparedStatement.setDate(2, Date.valueOf(fecha2));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ReservasModel res = new ReservasModel();
                res.setId(resultSet.getInt("id_reserva"));
                res.setNombre_cliente(resultSet.getString("nombre_cliente"));
                res.setDate(resultSet.getDate("fecha_reserva").toLocalDate());
                res.setHora(resultSet.getTime("hora_reserva").toLocalTime());
                res.setNum_personas(resultSet.getInt("num_personas"));
                res.setTelefono_cliente(resultSet.getInt("telefono_cliente"));
                res.setEmail_cliente(resultSet.getString("email_cliente"));
                res.setNotas_adicionales(resultSet.getString("notas_adicionales"));
                res.setTipo_menu(resultSet.getString("tipo_menu"));
                res.setDescripcion_alergia(resultSet.getString("alergia"));

                reservaList.add(res);
            }
        } catch (SQLException e) {
            throw new SQLException("Error del método getReservasByDate" + e.getMessage());
        } finally {
            closeDBConnection();
        }
        return reservaList;
    }

    public boolean editReserva(ReservasModel reserva) throws SQLException {
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la base de datos");
        }
        try {
            String SQL = "UPDATE reservas \n" +
                    "SET nombre_cliente = ?, fecha_reserva = ?, hora_reserva = ?, num_personas = ?, telefono_cliente = ?, notas_adicionales = ?, id_menu = ?\n" +
                    "WHERE id_reserva = ?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, reserva.getNombre_cliente());
            ps.setDate(2, java.sql.Date.valueOf(reserva.getDate()));
            ps.setTime(3, java.sql.Time.valueOf(reserva.getHora()));
            ps.setInt(4, reserva.getNum_personas());
            ps.setInt(5, reserva.getTelefono_cliente());
            ps.setString(6, reserva.getNotas_adicionales());
            ps.setInt(7, reserva.getId_menu());
            ps.setInt(8, reserva.getId());

            int rowsUpdate = ps.executeUpdate();
            if (rowsUpdate > 0) {
                JOptionPane.showMessageDialog(null, "La reserva se ha actualizado correctamente", "Actualización exitosa", JOptionPane.INFORMATION_MESSAGE);
            }
            return rowsUpdate > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al actualizar la reserva desde el método editReseva");

        } finally {
            closeDBConnection();
        }
    }

    public boolean deleteReserva(int id) throws SQLException {
        if (!initDBConnection()) {

            throw new SQLException("Error al conectar con la base de datos");
        }
        try {
            String SQL = "DELETE FROM reservas WHERE id_reserva =?;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al eliminar la reserva desde el método DeleteReserva");
        } finally {
            closeDBConnection();
        }
    }

    public int getElementsNumber() throws SQLException { // Devuelve el total de elementos
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la base de datos");
        }
        try {
            String SQL = "SELECT COUNT(*) AS total FROM reservas";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException("Error al obtener la cantidad de elementos desde el método getElementsNumber");
        } finally {
            closeDBConnection();
        }return 0;
    }

}


