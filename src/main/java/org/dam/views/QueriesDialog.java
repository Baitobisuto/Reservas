package org.dam.views;

import com.github.lgooddatepicker.components.DatePicker;
import org.dam.models.ReservasModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import java.util.ArrayList;
import static java.lang.Integer.parseInt;
import static org.dam.controllers.QueriesDialogController.*;

public class QueriesDialog extends JDialog implements InterfaceViews {
    private JPanel MainPanel;
    public JTable tb_reservas;
    private JTextField tx_id;
    private JTextField tx_nombre;
    private JButton btn_BuscarFecha;
    private JButton btn_BuscarID;
    private JButton btn_BuscarNombre;
    private DatePicker dp_desde;
    private DatePicker dp_hasta;
    private JButton btn_cancelar;
    private JButton btn_cargar;
    private JButton btn_back;
    private JButton btn_next;
    private JComboBox cb_page;
    private JLabel lb_page;
    private int page;
    private int totalPages;
    private int totalElements;
    private ActionListener actionListener;


    public QueriesDialog(JFrame parent, boolean modal) {
        super(parent, modal);
        initWindow();
    }

    public int getLimit() {
        return (int) cb_page.getSelectedItem();
    }

    public int getOffset() {
        int itemPerPage = (int) cb_page.getSelectedItem();
        if (page < 1 || itemPerPage <= 0) {
            return 0;
        }
        return (page - 1) * itemPerPage;
    }

    //Se ejecuta cada vez que pulsemos hacia delante/detrás
    public void updatePage(int value) {
        if (value + page > 0 && value + page <= totalPages) {
            page = value + page;
        } else if (value + page <= 0) {
            page = 1;
            lb_page.setText("Página: " + page + " de: " + totalPages);
        }
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalPages(int elements) {
        int elementsPages = (int) cb_page.getSelectedItem();
       totalPages = (int) Math.ceil((double) elements / elementsPages);
    }


    public void loadResultsPage() {
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        for (int i = 10; i <= 50; i += 10) {
            comboBoxModel.addElement(i);
        }
        cb_page.setModel(comboBoxModel);
        cb_page.setSelectedIndex(0);

    }

    public int getSelectPaginas() { // PARA SABER QUE HE SELECCIONADO DEL COMBO
        return (int) cb_page.getSelectedItem();

    }


    public int getSelectedReservaID() {
        TableModel model = tb_reservas.getModel();
        return Integer.valueOf(model.getValueAt(tb_reservas.getSelectedRow(), 0).toString());
    }

    @Override
    public void initWindow() {
        setContentPane(MainPanel);
        pack();
        setLocationRelativeTo(null);
        setCommands();
    }

    public void showReservas(ArrayList<ReservasModel> reservasList) {
        String[] encabezado = new String[]{"ID", "NOMBRE", "FECHA", "HORA", "NºPERS", "TIPO_MENU", "ALERGIAS", "NOTAS"};
        DefaultTableModel model = new DefaultTableModel(null, encabezado) {
            @Override
            public boolean isCellEditable(int row, int column) { // EVITA EDITAR EN LA TABLA
                return false;
            }

        };
        for (ReservasModel reserva : reservasList) {
            model.addRow(reserva.toArray());
        }
        tb_reservas.setModel(model);
    }

    public int getReservaID() {
        return parseInt(tx_id.getText().trim());// elimina espacios alrededor
    }


    public LocalDate getDp_desde() {
        return dp_desde.getDate();
    }

    public LocalDate getDp_hasta() {
        return dp_hasta.getDate();
    }

    public String getName() {
        return tx_nombre.getText();
    }


    @Override
    public void showWindow() {
        setVisible(true);
    }

    @Override
    public void closeWindow() {
        dispose();
    }

    @Override
    public void addListener(ActionListener listener) {
        this.addWindowListener((WindowListener) listener);//actualiza la tabla de reservas después de crearlas
        this.actionListener = listener;
        btn_BuscarNombre.addActionListener(listener);
        btn_cancelar.addActionListener(listener);
        btn_BuscarID.addActionListener(listener);
        btn_BuscarFecha.addActionListener(listener);
        btn_cargar.addActionListener(listener);
        btn_next.addActionListener(listener);
        btn_back.addActionListener(listener);
        cb_page.addItemListener((ItemListener) listener);


    }

    @Override
    public void setCommands() {
        btn_cancelar.setActionCommand(CLOSE_QUERIES_DIALOG);
        btn_BuscarID.setActionCommand(GET_QUERIES_DIALOG_BY_ID);
        btn_BuscarNombre.setActionCommand(GET_RESERVAS_BY_NAME);
        btn_BuscarFecha.setActionCommand(GET_RESERVAS_BY_DATE);
        btn_cargar.setActionCommand(CARGAR_TABLE);
        btn_next.setActionCommand(NEXT_PAGE);
        btn_back.setActionCommand(BACK_PAGE);
    }

    @Override
    public void initComponents() {
        tx_id.setText("");
        if (cb_page.getItemCount() > 0) {
            cb_page.setSelectedIndex(0);
            page = 1;
        }
        cb_page.addItemListener((ItemListener) actionListener);

    }


    public void resetPagination() {
        cb_page.setSelectedIndex(0);
        page = 1;
    }


}