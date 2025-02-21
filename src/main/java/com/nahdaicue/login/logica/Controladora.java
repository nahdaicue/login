package com.nahdaicue.login.logica;

import com.nahdaicue.login.persistecia.ControladoraPersistencia;
import java.util.List;

public class Controladora {

    ControladoraPersistencia controlPersis;

    public Controladora() {
        controlPersis = new ControladoraPersistencia();
    }

    public Usuario validarUsuario(String usuario, String contrasenia) {

        //String mensaje = "";
        Usuario user = null;

        List<Usuario> listaUsuario = controlPersis.traerUsuarios();

        for (Usuario usu : listaUsuario) {
            if (usu.getNombreUsuario().equals(usuario)) {
                if (usu.getContrasenia().equals(contrasenia)) {
                    user = usu;

                    return user;
                } else {
                    //mensaje = "Contraseña invalida!";
                    user = null;
                    //return user;
                }
            } else {
                //mensaje = "Usuario no encontrado!";
                user = null;
                //return user;
            }
        }
        return user;
    }

    public List<Usuario> traerUsuarios() {
        return controlPersis.traerUsuarios();
    }

    public List<Rol> traerRoles() {
        return controlPersis.traerRoles();
    }

    public void crearUsuario(String usuario, String contra, String rolRecibido) {
        //Creo un usuario 
        Usuario usu = new Usuario();
        usu.setNombreUsuario(usuario);
        usu.setContrasenia(contra);

        //Llamo a un metodo para verificar que existe el rol solicitado en la persistencia
        //Esto es porque Rol es un objeto
        Rol rolEncontrado = new Rol();

        rolEncontrado = this.traerRol(rolRecibido);
        if (rolEncontrado != null) {
            usu.setUnRol(rolEncontrado);
        }
        //Esto es porque cargue id a mano en mi base de datos y no me funciona GeneratedValue x eso
        int id = this.buscarUltimaIdUsuarios();
        usu.setId(id + 1);
        controlPersis.crearUsuario(usu);
    }

    //Busca el rol en la lista de persistencia
    private Rol traerRol(String rolRecibido) {
        List<Rol> listaRoles = controlPersis.traerRoles(rolRecibido);

        for (Rol rol : listaRoles) {
            if (rol.getNombreRol().equals(rolRecibido)) {
                return rol;
            }
        }
        return null;
    }

    private int buscarUltimaIdUsuarios() {
        List<Usuario> listaUsuarios = this.traerUsuarios();
        //con size()-1 tengo el ultimo elemento de la lista que seria un int
        //luego se guarda el ultimo elemento en usu y luego se le extrae el id
        Usuario usu = listaUsuarios.get(listaUsuarios.size() - 1);
        return usu.getId();
    }

    public void borrarUsuario(int id_usuario) {
        controlPersis.borrarUsuario(id_usuario);
    }

    public Usuario traerUsuario(int id_usuario) {
        return controlPersis.traerUsuario(id_usuario);
    }

    public void editarUsuario(Usuario usu, String usuario, String contra, String rolRecibido) {

        usu.setNombreUsuario(usuario);
        usu.setContrasenia(contra);

        Rol rolEncontrado = new Rol();

        rolEncontrado = this.traerRol(rolRecibido);
        if (rolEncontrado != null) {
            usu.setUnRol(rolEncontrado);
        }

        controlPersis.editarUsuario(usu);
    }

}
