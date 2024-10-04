package org.dam.models;

public class TipoAlergiaModel {

    private int id_alergia;
    private String descripcion;



    public TipoAlergiaModel() {
    }

    public TipoAlergiaModel(String descripcion, int id_alergia) {
        this.descripcion = descripcion;
        this.id_alergia = id_alergia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_alergia() {
        return id_alergia;
    }

    @Override
    public String toString() {
        return  descripcion;
    }

    public void setId_alergia(int id_alergia) {
        this.id_alergia = id_alergia;
    }
}
