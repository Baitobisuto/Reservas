package org.dam.views;

import java.awt.event.ActionListener;

public interface InterfaceViews {


    void initWindow();
    /*Inicializa la ventana o componente gráfico, preparando su estructura o layout.
     Este método podría configurarse para definir el tamaño, la ubicación, el diseño, y otros parámetros iniciales de la ventana.*/

    void showWindow();

    void closeWindow();

    void addListener(ActionListener listener);
/*Añade un escuchador de eventos (de tipo ActionListener).
Esto es común en interfaces gráficas para responder a acciones del usuario, como hacer clic en botones o seleccionar opciones.
Se usa para gestionar las interacciones del usuario con la interfaz.*/

    void setCommands();
/*Define los comandos o acciones que se deben ejecutar cuando ocurren ciertos eventos en la interfaz gráfica.
Por ejemplo, puede asociar acciones específicas a botones u otros componentes interactivos.*/


    void initComponents();
/*Inicializa los componentes gráficos de la interfaz, como botones, etiquetas, cuadros de texto, etc.
Este método puede usarse para crear, configurar y añadir estos componentes a la ventana.*/
}
