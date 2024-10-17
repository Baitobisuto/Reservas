package org.dam.controllers;


import org.dam.dao.ReservasDAO;
import org.dam.dao.TipoAlergiaDAO;
import org.dam.models.ReservasModel;
import org.dam.models.TipoAlergiaModel;
import org.dam.services.WindowsServices;
import org.dam.views.FormDialog;
import org.dam.views.QueriesDialog;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueriesDialogController implements ActionListener, WindowListener, ItemListener, MouseListener, KeyListener {

    private WindowsServices windowsServices;
    private QueriesDialog queriesDialog;
    private ReservasDAO reservasDAO;
    private FormDialog formDialog;
    private TipoAlergiaDAO tipoAlergiaDAO;


    public static final String CLOSE_QUERIES_DIALOG = "closeQueriesDialog";
    public static final String GET_QUERIES_DIALOG_BY_ID = "getQueriesDialogByID";
    public static final String GET_RESERVAS_BY_NAME = "GetReservasByName";
    public static final String GET_RESERVAS_BY_DATE = "GetReservasByDate";
    public static final String CARGAR_TABLE = "CargarTable";
    public static final String EDIT_MODEL = "EDIT_MODE";
    public static final String CREATE_MODE = "CREATE_MODE";
    public static final String NEXT_PAGE = "NextPage";
    public static final String BACK_PAGE = "BackPage";
    public static final String CK_ALERGIAS = "CK_ALERGIAS";


    public QueriesDialogController(WindowsServices windowsServices, ReservasDAO reservasDAO, FormDialog formDialog) {
        this.windowsServices = windowsServices;
        this.queriesDialog = (QueriesDialog) windowsServices.getWindows("QueriesDialog");
        this.reservasDAO = reservasDAO;
        this.formDialog = formDialog;
        this.tipoAlergiaDAO = new TipoAlergiaDAO();

    }

    private void handleLoadComboPage() {
        queriesDialog.loadResultsPage();
    }


    private void handleCloseQueriesDialog() {
        queriesDialog.closeWindow();
    }

    private void handleGetRerservas() {
        try {
            queriesDialog.updatePage(0);
            ArrayList<ReservasModel> reservasList = reservasDAO.getReserva(queriesDialog.getLimit(), queriesDialog.getOffset());
            queriesDialog.showReservas(reservasList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleGetReservarById() {
        try {
            ArrayList<ReservasModel> reservasList = reservasDAO.getReservaById(queriesDialog.getReservaID());
            queriesDialog.showReservas(reservasList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleGetReservasByName() {
        try {
            ArrayList<ReservasModel> reservasList = reservasDAO.getReservasByName(queriesDialog.getName());
            queriesDialog.showReservas(reservasList);
        } catch (SQLException e) {
            System.out.println("Error al obtener la reserva desde el handlGetReservasByName");
        }
    }

    private void handleGetReservasByDate() {
        try {
            ArrayList<ReservasModel> resList = reservasDAO.getRersevasByDate(queriesDialog.getDp_desde(), queriesDialog.getDp_hasta());
            queriesDialog.showReservas(resList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void openShowDialog(ReservasModel reserva) {
        formDialog = (FormDialog) windowsServices.getWindows("FormDialog");
        formDialog.setMode(EDIT_MODEL);
        formDialog.setReservas(reserva);
        formDialog.closeWindow();
        formDialog.showWindow();
    }

    private void handleUpdatePage(int value) {
        queriesDialog.updatePage(value);

    }

    private void handleGetTotalElements() {
        try {
            int elements = reservasDAO.getElementsNumber();
            queriesDialog.setTotalPages(elements);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

   private void handleReservaDobleClic() {
        int reservaID = queriesDialog.getSelectedReservaID();
        System.out.println("La ID seleccionada es: " + reservaID);
        try {
            ArrayList<ReservasModel> reservasList = reservasDAO.getReservaById(reservaID);
            ReservasModel reserva = reservasList.get(0);
            openShowDialog(reserva);
            //**********************************
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
        //17/10
    /*private void handleReservaDobleClic() {
        int reservaID = queriesDialog.getSelectedReservaID();
        System.out.println("La ID seleccionada es: " + reservaID);
        try {
            ArrayList<ReservasModel> reservasList = reservasDAO.getReservaById(reservaID);
            if (reservasList.isEmpty()) {
                System.out.println("No se encontró la reserva con ID: " + reservaID);
                return; // Salir si no hay reservas
            }

            ReservasModel reserva = reservasList.get(0);

            // Obtener el tipo de alergia asociado a la reserva
            TipoAlergiaModel alergia = tipoAlergiaDAO.getTipoAlergiaById(reserva.getId_alergia());

            // Abrir el diálogo de edición y pasar la reserva y la alergia
            openShowDialog(reserva, alergia);
        } catch (SQLException ex) {
            throw new RuntimeException("Error al obtener la reserva: " + ex.getMessage());
        }
    }
*/

    private void handleGetReservasByAlergia() {
        try {
            ArrayList<TipoAlergiaModel> reservasList = tipoAlergiaDAO.getTipoAlergia();
            queriesDialog.loadComboTipoAlergia(reservasList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case CLOSE_QUERIES_DIALOG:
                handleCloseQueriesDialog();
                break;
            case GET_QUERIES_DIALOG_BY_ID:
                handleGetReservarById();
                break;
            case GET_RESERVAS_BY_NAME:
                handleGetReservasByName();
                break;
            case GET_RESERVAS_BY_DATE:
                handleGetReservasByDate();
                break;
            case CARGAR_TABLE:
                queriesDialog.resetPagination();
                handleGetRerservas();
                break;
            case NEXT_PAGE:
                handleUpdatePage(1);
                handleGetRerservas();
                break;
            case BACK_PAGE:
                handleUpdatePage(-1);
                handleGetRerservas();
                break;
            case CK_ALERGIAS:
                queriesDialog.updatePage(0);
                handleGetReservasByAlergia(); //17/10/24
                break;
            default:
                System.out.println("Acción desconocida: " + command);
                break;
        }

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
        handleLoadComboPage();
        handleGetTotalElements();
        handleGetReservasByAlergia(); // 17/10/24
        handleGetRerservas();
        handleUpdatePage(1); // el 1 es para que inicie en la página 1
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) { // se lanza cuando cambio de elemento en el combo de las paginas
        if (e.getStateChange() == ItemEvent.SELECTED) {
            handleGetTotalElements();
            handleGetRerservas();
            handleUpdatePage(1); // el 1 es para que inicie en 1
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            handleReservaDobleClic();
        }

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
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() != -1) {
            handleGetReservasByName();
        }

    }
}
