package org.tfg.athometfgcarloshernandez.spring.common.constantes;

public class ConstantesSpring {
    public static final String ME = "me";
    public static final int CADUCIDAD_CODIGO = 300;
    public static final int CADUCIDAD_CODIGO_REFRESH = 3000;
    public static final String BEARER = "Bearer ";
    public static final String KEY_STORE_PATH = "${application.security.key-store-path}";
    public static final String PASSWORD_KEYSTORE = "${application.security.key-store-pass}";
    public static final String SERVER_ACCESS = "${application.security.server-access}";
    public static final String SERVER_PASSWORD = "${application.security.server-password}";
    public static final String PKCS_12 = "PKCS12";
    public static final String MAIL_HOST = "${spring.mail.host}";
    public static final String MAIL_PORT = "${spring.mail.port}";
    public static final String MAIL_USERNAME = "${spring.mail.username}";
    public static final String MAIL_PASSWORD = "${spring.mail.password}";
    public static final String MAIL_PROPERTY_AUTH = "${spring.mail.properties.mail.smtp.auth}";
    public static final String MAIL_PROPERTY_TRANSPORT_PROTOCOL = "${spring.mail.properties.mail.transport.protocol}";
    public static final String MAIL_PROPERTY_STARTTLS = "${spring.mail.properties.mail.smtp.starttls.enable}";
    public static final String MAIL_PROPERTY_DEBUG = "${spring.mail.properties.mail.debug}";
    public static final String BEAN_NAME = "getJavaMailSender";
    public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_DEBUG = "mail.debug";
    public static final String ACTIVACION_CODIGO = "<html>Haga <a href=\"http://localhost:8889/register/validacion?codigo=";
    public static final String ACTIVAR_SU_CUENTA_HTML = "\"> click aquí </a> para activar su cuenta</html>";
    public static final String HTML_SU_NUEVA_CONTRASENYA_TEMPORAL_ES = "<html>Su nueva contraseña temporal es: ";
    public static final String CONTRA_TEMPORAL = "Contra temporal";
    public static final String CLOSE_HTML = ". Úsela para loguearse y cambie la contraseña desde la app.</html>";
    public static final String IDUSUARIO= "idUsuario";
    public static final String IDCASA = "idCasa";

    private ConstantesSpring() {
    }
}
