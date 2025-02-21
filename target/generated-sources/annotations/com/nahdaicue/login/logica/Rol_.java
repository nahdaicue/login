package com.nahdaicue.login.logica;

import com.nahdaicue.login.logica.Usuario;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-02-20T23:43:55", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(Rol.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Rol_ { 

    public static volatile SingularAttribute<Rol, String> descripcion;
    public static volatile ListAttribute<Rol, Usuario> listaUsuario;
    public static volatile SingularAttribute<Rol, String> nombreRol;
    public static volatile SingularAttribute<Rol, Integer> id;

}