package com.nahdaicue.login.logica;

import com.nahdaicue.login.persistecia.ControladoraPersistencia;
import java.util.List;

public class Controladora {

    ControladoraPersistencia controlPersis;
    
    public Controladora(){
        controlPersis = new ControladoraPersistencia();
    }

    public String validarUsuario(String usuario, String contrasenia) {

        String mensaje = "";

        List<Usuario> listaUsuario = controlPersis.traerUsuarios();

        for (Usuario usu : listaUsuario) {
            if (usu.getNombreUsuario().equals(usuario)) {
                if (usu.getContrasena().equals(contrasenia)) {
                    mensaje = "Bienvenid@ " + usu.getNombreUsuario() + "!";
                    return mensaje;
                } else {
                    mensaje = "Contraseña invalida!";
                    return mensaje;
                }
            } else {
                mensaje = "Usuario no encontrado!";
            }
        }
        return mensaje;
    }

}
