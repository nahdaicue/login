package com.nahdaicue.login.logica;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Usuario implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String nombreUsuario;
    private String contrasenia;

    @ManyToOne
    @JoinColumn(name="fk_rol")
    private Rol unRol;

    // Constructores------------------------------------------------------------
    public Usuario() {
    }

    public Usuario(String nombreUsuario, String contrasenia, Rol unRol) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.unRol = unRol;
    }

    // Getters and Setters------------------------------------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getUnRol() {
        return unRol;
    }

    public void setUnRol(Rol unRol) {
        this.unRol = unRol;
    }

}
