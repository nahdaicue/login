package com.nahdaicue.login.logica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class Rol implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nombreRol;
    private String descripcion;

    @OneToMany(mappedBy = "unRol")
    private List<Usuario> listaUsuario;

    //Contructores--------------------------------------------------------------
    public Rol() {
    }

    public Rol(int id, String nombreRol, String descripcion, List<Usuario> listaUsuario) {
        this.id = id;
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
        this.listaUsuario = listaUsuario;
    }

    //Getters and Setters-------------------------------------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

}
