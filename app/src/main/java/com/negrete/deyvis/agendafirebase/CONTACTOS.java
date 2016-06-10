package com.negrete.deyvis.agendafirebase;

/**
 * Created by usuario on 29/05/2016.
 */
public class CONTACTOS {
    private String id,nombre,telefono,valor;


    public CONTACTOS(String id, String nombre, String telefono,String valor) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getValor() {
        return valor;
    }
}
