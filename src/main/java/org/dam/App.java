package org.dam;
import com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme;
import org.dam.controllers.FormDialogController;
import org.dam.controllers.MainFrameController;
import org.dam.controllers.QueriesDialogController;
import org.dam.dao.ReservasDAO;
import org.dam.dao.TipoAlergiaDAO;
import org.dam.dao.TipoMenuDAO;
import org.dam.services.WindowsServices;
import org.dam.views.FormDialog;
import org.dam.views.MainFrame;
import org.dam.views.QueriesDialog;


public class App 
{
    public static void main( String[] args )

    {
        FlatCobalt2IJTheme.setup();

        WindowsServices windowsServices = new WindowsServices();

        //Ventana principal
        MainFrame mainFrame = new MainFrame();//instancia
        windowsServices.registerWindows("MainFrame", mainFrame); // registro la ventana

        //Ventana formulario
        FormDialog formDialog = new FormDialog(mainFrame, true);
        windowsServices.registerWindows("FormDialog", formDialog);

        //Ventana consultas
        QueriesDialog queriesDialog = new QueriesDialog(mainFrame,true);
        windowsServices.registerWindows("QueriesDialog", queriesDialog);

        //DAOS
        ReservasDAO reservasDAO = new ReservasDAO();
        TipoMenuDAO tipoMenuDAO = new TipoMenuDAO();
        TipoAlergiaDAO tipoAlergiaDAO = new TipoAlergiaDAO();

        //Controladores
        MainFrameController mainFrameController = new MainFrameController(windowsServices,reservasDAO);
        FormDialogController formDialogController = new FormDialogController(windowsServices, reservasDAO, tipoMenuDAO,tipoAlergiaDAO);
        QueriesDialogController queriesDialogController = new QueriesDialogController(windowsServices, reservasDAO,formDialog);

        //Listener
        mainFrame.addListener(mainFrameController);
        formDialog.addListener(formDialogController);
        queriesDialog.addListener(queriesDialogController);


        mainFrame.showWindow();

    }
}
