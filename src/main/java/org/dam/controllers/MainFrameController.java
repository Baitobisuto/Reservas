package org.dam.controllers;

import org.dam.dao.ReservasDAO;
import org.dam.services.WindowsServices;
import org.dam.views.FormDialog;
import org.dam.views.MainFrame;
import org.dam.views.QueriesDialog;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import static org.dam.controllers.QueriesDialogController.CREATE_MODE;


public class MainFrameController implements ActionListener {
    public static final String SHOW_FORM_DIALOG = "showFormDialog";
    public static final String CLOSE_MAIN_FRAME = "closeMainFrame";
    public static final String SHOW_QUERIES_DIALOG = "showQueriesDialog";
    public static final String DELETE_RESERVA_BOTON = "deleteReservaBoton";

    private MainFrame mainFrame;
    private WindowsServices windowsServices;
    private ReservasDAO reservasDAO;


    public MainFrameController(WindowsServices windowsServices, ReservasDAO reservasDAO) {
        this.windowsServices = windowsServices;
        this.reservasDAO = reservasDAO;
        this.mainFrame = (MainFrame) windowsServices.getWindows("MainFrame");
    }

    private void handleShowFormDialog() {
       FormDialog formDialog = (FormDialog) windowsServices.getWindows("FormDialog");
        formDialog.setMode(CREATE_MODE);
        formDialog.showWindow();
    }

    private void handleShowQueriesDialog() {
        QueriesDialog queriesDialog = (QueriesDialog) windowsServices.getWindows("QueriesDialog");
        queriesDialog.showWindow();
    }


    private void handleCloseMainFrame() {
        int response = JOptionPane.showConfirmDialog(null, "Seguro que quieres salir?");
        if (response == JOptionPane.YES_OPTION) {
            mainFrame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Gracias por quedarte!!");
        }
    }

    private void handleDeleteReserva() {

        String idIngresado = JOptionPane.showInputDialog(null, "Ingrese el ID de la reserva: ");
        if (idIngresado == null || idIngresado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No has ingresado un ID.");
            return;
        }
        try {
            int reservaID = Integer.parseInt(idIngresado.trim());
            boolean isDeleted = reservasDAO.deleteReserva(reservaID);
            if (isDeleted) {
                JOptionPane.showMessageDialog(null, "Reserva eliminada correctamente: " + reservaID);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna reserva con ID: " + reservaID);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID ingresado no es válido. Por favor, ingresa un número.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la reserva: " + e.getMessage());
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case SHOW_FORM_DIALOG:
                handleShowFormDialog();
                break;
            case SHOW_QUERIES_DIALOG:
                handleShowQueriesDialog();
                break;

            case CLOSE_MAIN_FRAME:
                handleCloseMainFrame();
                break;
            case DELETE_RESERVA_BOTON:
                handleDeleteReserva();
                break;

            default:
                System.out.println("Comando no reconocido:" + command);
                break;
        }
    }
}
