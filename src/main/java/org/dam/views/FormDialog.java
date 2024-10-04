package org.dam.views;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import org.dam.models.ReservasModel;
import org.dam.models.TipoAlergiaModel;
import org.dam.models.TipoMenuModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static org.dam.controllers.FormDialogController.*;
import static org.dam.controllers.QueriesDialogController.*;


public class FormDialog extends JDialog implements InterfaceViews{
    private JPanel mainPanel;
    private JTextField tx_name;
    private JComboBox cb_numPersonas;
    private JButton btn_hacerReservar;
    private JTextField tx_telefono;
    private DatePicker dp_fecha;
    private JTextArea ta_Notas;
    private JTextField tx_email;
    private JCheckBox ck_si;
    private TimePicker dt_hora;
    private JCheckBox ck_no;
    private JLabel tx_notas;
    private JLabel tx_DatosClie;
    private JComboBox cb_tipoMenu;
    private JLabel lb_warning;
    private JComboBox cb_TipoAlergia;
    private boolean isEditMode;
    private int reservaID;


    public FormDialog(JFrame parent, boolean modal) {
        super(parent, modal);
        initWindow();
        initComponents();
    }

    public void loadComboTipoMenu(ArrayList<TipoMenuModel> menuList) {
        DefaultComboBoxModel<TipoMenuModel> model = new DefaultComboBoxModel();
        //añadir un item al combo
        TipoMenuModel tp = new TipoMenuModel();
        tp.setNombre("Elige una opción");
        tp.setId_menu(0);
        menuList.add(0, tp);
        for (TipoMenuModel tipoMenu : menuList) {
            model.addElement(tipoMenu);
        }
        cb_tipoMenu.setModel(model);
    }

    public void loadComboTipoAlergia(ArrayList<TipoAlergiaModel> reservasList) {
        DefaultComboBoxModel<TipoAlergiaModel> model = new DefaultComboBoxModel();
        //añadir un item al combo
        TipoAlergiaModel tp = new TipoAlergiaModel();
        tp.setDescripcion("Elige una opción");
        tp.setId_alergia(0);
        reservasList.add(0, tp);
        for (TipoAlergiaModel tipoAlergia : reservasList) {
            model.addElement(tipoAlergia);
        }
        cb_TipoAlergia.setModel(model);
    }

 /*   public int getTipoMenu() {
        ReservasModel reservas = (ReservasModel) cb_tipoMenu.getSelectedItem();
        return reservas.getId_menu();
    }
*/

    @Override
    public void initWindow() {
        setContentPane(mainPanel);
        setCommands();
        pack();
        setLocationRelativeTo(null);
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
        cb_numPersonas.addActionListener(listener);
        btn_hacerReservar.addActionListener(listener);
        cb_tipoMenu.addActionListener(listener);

    }


    @Override
    public void setCommands() {
        cb_numPersonas.setActionCommand(NUMBER_PERSONS);
        btn_hacerReservar.setActionCommand(CREATE_RESERVA);
        cb_tipoMenu.setActionCommand(SHOW_TIPO_MENU);
    }

    @Override
    public void initComponents() {
        setNumberPerson();

    }

    public void setNumberPerson() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel();
        for (int i = 1; i <= 15; i++) {
            model.addElement(String.valueOf(i));
        }
        cb_numPersonas.setModel(model);

    }

    public void setWarningMessage(String warningMessage) {
        lb_warning.setText(warningMessage);
    }

/*
    public List<ReservasModel> getSelectedReservas() {
        return (List<ReservasModel>) getReservas();

    }*/

    private void setSelectedTipoAlergia(int id){
        ComboBoxModel<TipoAlergiaModel> model = cb_TipoAlergia.getModel();
        for (int i = 0; i < cb_TipoAlergia.getItemCount(); i++) {
            TipoAlergiaModel alergiaModel = model.getElementAt(i);
            if(alergiaModel.getId_alergia()== id){
                cb_TipoAlergia.setSelectedItem(alergiaModel);
                break;
            }
        }
    }

    public void setReservas(ReservasModel reservas) {
        ReservasModel reserva = new ReservasModel();
        tx_name.setText(reservas.getNombre_cliente());
        tx_telefono.setText(String.valueOf(reservas.getTelefono_cliente()));
        dp_fecha.setDate(reservas.getDate());
        ta_Notas.setText(reservas.getNotas_adicionales());
        tx_email.setText(reservas.getEmail_cliente());
        dt_hora.setText(reservas.getHora().toString());
        setSelectedTipoAlergia(reservas.getId_alergia()); // Y descripcionAlergia???
        cb_tipoMenu.setSelectedItem(reservas.getTipo_menu());
    }

    public ReservasModel getReservas() {
        ReservasModel reservas = new ReservasModel();
        reservas.setNombre_cliente(tx_name.getText());
        reservas.setTelefono_cliente(Integer.parseInt(tx_telefono.getText()));
        reservas.setDate(dp_fecha.getDate());
        reservas.setNotas_adicionales(ta_Notas.getText());
        reservas.setEmail_cliente(tx_email.getText());
        reservas.setHora(dt_hora.getTime());
        String numpersonas = (String) cb_numPersonas.getSelectedItem();
        reservas.setNum_personas(Integer.valueOf(numpersonas));
        if(isEditMode){
            reservas.setId(reservaID);
        }
        return reservas;
    }

    public void setDefaultData() {
        tx_name.setText("");
        tx_telefono.setText("");
        dp_fecha.setDate(null);
        ta_Notas.setText("");
        tx_email.setText("");
        dt_hora.setTime(null);
        cb_numPersonas.setSelectedIndex(0);

    }


    public void setMode(String mode) {
        if (mode.equals(EDIT_MODEL)) {
            btn_hacerReservar.setText("EDITAR");
            isEditMode = true;
            btn_hacerReservar.setActionCommand(EDIT_RESERVA);
        } else if (mode.equals(CREATE_MODE)) {
            btn_hacerReservar.setText("CREAR");
            isEditMode = false;
            btn_hacerReservar.setActionCommand(CREATE_RESERVA);
        }
    }

}
