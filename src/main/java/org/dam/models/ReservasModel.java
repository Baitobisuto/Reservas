package org.dam.models;

import java.time.LocalDate;
import java.time.LocalTime;


public class ReservasModel {
    private int id;
    private String nombre_cliente;
    private LocalDate date;
    private LocalTime hora;
    private int num_personas;
    private String email_cliente;
    private int telefono_cliente;
    private String notas_adicionales;
    private int id_menu;
    private int id_alergia;
    private String tipo_menu;
    private String descripcion_alergia;


    public ReservasModel() {
        this.date = date;
        this.email_cliente = "";
        this.hora = hora;
        this.id = 0;
        this.nombre_cliente = "";
        this.notas_adicionales = "";
        this.num_personas = 0;
        this.telefono_cliente = 0;
        this.id_menu = 0;
        this.id_alergia = 0;
        this.tipo_menu = "";
        this.descripcion_alergia = "";

    }


    public String getDescripcion_alergia() {
        return descripcion_alergia;
    }

    public void setDescripcion_alergia(String descripcion_alergia) {
        this.descripcion_alergia = descripcion_alergia;
    }

    public ReservasModel(LocalDate date, String email_cliente, LocalTime hora, int id, String nombre_cliente, String notas_adicionales, int num_personas, int telefono_cliente, int id_menu, int id_alergia, String tipo_menu, String descripcion_alergia) {
        this.date = date;
        this.email_cliente = email_cliente;
        this.hora = hora;
        this.id = id;
        this.nombre_cliente = nombre_cliente;
        this.notas_adicionales = notas_adicionales;
        this.num_personas = num_personas;
        this.telefono_cliente = telefono_cliente;
        this.id_menu = id_menu;
        this.id_alergia = id_alergia;
        this.tipo_menu = tipo_menu;
        this.descripcion_alergia = descripcion_alergia;


    }

    public String getTipo_menu() {
        return tipo_menu;
    }

    public void setTipo_menu(String tipo_menu) {
        this.tipo_menu = tipo_menu;
    }

    public int getId_alergia() {
        return id_alergia;
    }

    public void setId_alergia(int id_alergia) {
        this.id_alergia = id_alergia;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEmail_cliente() {
        return email_cliente;
    }

    public void setEmail_cliente(String email_cliente) {
        this.email_cliente = email_cliente;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getNotas_adicionales() {
        return notas_adicionales;
    }

    public void setNotas_adicionales(String notas_adicionales) {
        this.notas_adicionales = notas_adicionales;
    }

    public int getNum_personas() {
        return num_personas;
    }

    public void setNum_personas(int num_personas) {
        this.num_personas = num_personas;
    }

    public int getTelefono_cliente() {
        return telefono_cliente;
    }

    public void setTelefono_cliente(int telefono_cliente) {
        this.telefono_cliente = telefono_cliente;
    }

    public String[] toArray() { // QUITAR TIPO_MENU Y DESCRIPCION_ALERGIA SI SOLO MUESTRA 0
        return new String[]{String.valueOf(id), nombre_cliente, String.valueOf(date), String.valueOf(hora), String.valueOf(num_personas), tipo_menu, descripcion_alergia, notas_adicionales};
    }


}


