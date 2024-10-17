package org.dam.controllers;

import org.dam.dao.ReservasDAO;
import org.dam.dao.TipoAlergiaDAO;
import org.dam.dao.TipoMenuDAO;
import org.dam.models.ReservasModel;
import org.dam.models.TipoMenuModel;
import org.dam.services.WindowsServices;
import org.dam.views.FormDialog;
import org.dam.views.QueriesDialog;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;


public class FormDialogController implements ActionListener, WindowListener, ItemListener, MouseListener {
    // Comandos que se envían al MainFrameController para realizar acciones
    // Comandos que se envía al FormDialogController para realizar acciones
    // Declaración del cuadro de diálogo e instancia de WindowsServices para obtener las ventanas

    private FormDialog formDialog;
    private final WindowsServices windowsServices;
    private QueriesDialog queriesDialog;
    private ReservasDAO reservasDAO;
    private TipoMenuDAO tipoMenuDAO;
    private TipoMenuModel tipoMenuModel;
    private TipoAlergiaDAO tipoAlergiaDAO;
    public static final String CREATE_RESERVA = "CreateReserva";
    public static final String SHOW_TIPO_MENU = "ShowTipoMenu";
    public static final String EDIT_RESERVA = "EditReserva";




    // Constructor
    public FormDialogController(WindowsServices windowsServices, ReservasDAO reservasDAO, TipoMenuDAO tipoMenuDAO,TipoAlergiaDAO tipoAlergiaDAO) {
        this.windowsServices = windowsServices;
        this.formDialog = (FormDialog) windowsServices.getWindows("FormDialog");
        this.reservasDAO = reservasDAO;
        this.tipoMenuDAO = tipoMenuDAO;
        this.tipoAlergiaDAO = tipoAlergiaDAO;

    }

    private void handlGetTipoReserva() {
        try {
            formDialog.loadComboTipoMenu(tipoMenuDAO.getTipoMenu());
        } catch (Exception e) {
            formDialog.setWarningMessage(e.getMessage());
        }
    }

    private void handlGetTipoAlergia() {
        try {
            formDialog.loadComboTipoAlergia(tipoAlergiaDAO.getTipoAlergia());
        } catch (Exception e) {
            formDialog.setWarningMessage(e.getMessage());
        }
    }


    private void handleCreateReserva() {
        try {
            ReservasModel reserva = formDialog.getReservas();
            if (reserva != null) {

                reservasDAO.createReserva(reserva);
                formDialog.setDefaultData();

            }
            int response = JOptionPane.YES_OPTION;
            if (response == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Reserva creada correctamente");
                formDialog.dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());

        }
    }

    private void handleEditReserva() {
        try {

            boolean okEditar = reservasDAO.editReserva(formDialog.getReservas());
            if (okEditar) {
                JOptionPane.showMessageDialog(null, "Reserva actualizada correctamente");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case CREATE_RESERVA:
                handleCreateReserva();
                break;
            case EDIT_RESERVA:
                handleEditReserva();
                break;
            default:
                System.out.println("Comando no reconocido: " + command);
                break;
        }
    }


    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {
        handlGetTipoReserva();
        handlGetTipoAlergia();

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
