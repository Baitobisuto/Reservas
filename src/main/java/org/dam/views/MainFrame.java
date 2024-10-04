package org.dam.views;

import javax.swing.*;
import java.awt.event.ActionListener;

import static org.dam.controllers.MainFrameController.*;

public class MainFrame extends JFrame implements InterfaceViews {
    private JPanel MainPanel;
    private JButton btn_exit;
    private JButton btn_consultas;
    private JButton btn_borrar;
    private JButton btn_reservar;
    private JLabel tx_restaurante;

    public MainFrame() {
        initWindow();

    }

    @Override
    public void addListener(ActionListener listener) {
        btn_consultas.addActionListener(listener);
        btn_reservar.addActionListener(listener);
        btn_exit.addActionListener(listener);
        btn_borrar.addActionListener(listener);

    }

    @Override
    public void setCommands() {
        btn_reservar.setActionCommand(SHOW_FORM_DIALOG);// pasa la variable static del MainFrameController
        btn_consultas.setActionCommand(SHOW_QUERIES_DIALOG);
        btn_exit.setActionCommand(CLOSE_MAIN_FRAME);
        btn_borrar.setActionCommand(DELETE_RESERVA_BOTON);
    }

    @Override
    public void initComponents() {

    }

    @Override
    public void initWindow() { //VALORES BÁSICO PARA MOSTRAR LA VENTANA MAINFRAME(PRINCIPAL)
        setContentPane(MainPanel);//MUESTRA EL PANEL
        pack();//ESTABLECE EL TAMAÑO PUESTO EN PREFERED SIZE
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setCommands();

    }

    @Override
    public void showWindow() {
        setVisible(true);

    }

    @Override
    public void closeWindow() {
        dispose();
    }

}
