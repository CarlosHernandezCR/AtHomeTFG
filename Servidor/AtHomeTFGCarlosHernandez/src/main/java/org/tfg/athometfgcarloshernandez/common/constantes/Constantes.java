package org.tfg.athometfgcarloshernandez.common.constantes;

public class Constantes {
    public static final String USUARIO_VALIDADO = "Usuario validado";
    public static final String EMAIL = "${spring.mail.username}";
    public static final String ACTIVACION_CODIGO = "<html>Haga <a href=\"http://localhost:8889/registro/validarUsuario?codigo=";
    public static final String ACTIVAR_SU_CUENTA_HTML = "\"> click aquí </a> para activar su cuenta</html>";
    public static final String SPRING = "spring";
    public static final String ARROBA = "@";
    public static final String VOTACION_ACEPTADA = "Votación aceptada";
    public static final String FORMATO_FECHA = "dd-MM-yyyy";
    public static final String ERROR_AL_ENVIAR_EMAIL_A = "Error al enviar email a {}: {}";

    private Constantes() {
    }
}
