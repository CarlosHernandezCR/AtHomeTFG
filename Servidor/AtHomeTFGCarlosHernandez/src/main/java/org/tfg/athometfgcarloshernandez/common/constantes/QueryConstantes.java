package org.tfg.athometfgcarloshernandez.common.constantes;

public class QueryConstantes {
    public static final String FIND_USUARIOS_BY_CASA = "SELECT u.id , u.NOMBRE, u.correo, u.telefono, u.color " +
            "FROM usuario u " +
            "JOIN vive v ON u.id = v.id_usuario " +
            "WHERE v.id_casa = :idCasa";

    public static final String FIND_NUMERO_RESIDENTES = "SELECT COUNT(*) FROM usuario u JOIN vive v ON u.id = v.id_usuario WHERE v.id_casa = :idCasa";
    public static final String FIND_EVENTOS_EN_DIA = "SELECT e FROM EventoEntity e WHERE e.idCasaEntity.id = :idCasa AND DAY(e.fecha) = :dia AND MONTH(e.fecha) = :mes AND YEAR(e.fecha) = :anio";
    public static final String FIND_DIAS_CON_EVENTOS = "SELECT DISTINCT DAY(e.fecha) FROM EventoEntity e WHERE e.idCasaEntity.id = :idCasa AND MONTH(e.fecha) = :mes AND YEAR(e.fecha) = :anio";

    public static final String FIND_ESTADOS_USUARIO_POR_ID_USUARIO = "SELECT eu.estadoEntity.descripcion FROM EstadosUsuarioEntity eu WHERE eu.usuarioEntity.id= :idUsuario";

    public static final String FIND_CASAS_POR_USUARIO_ID = "SELECT c FROM CasaEntity c JOIN ViveEntity v ON c.id = v.casaEntity.id WHERE v.usuarioEntity.id = :idUsuario";
    public static final String FIND_BY_ID_WITH_CAJON_AND_PROPIETARIO = "SELECT a FROM AlmacenaEntity a JOIN FETCH a.idCajon c JOIN FETCH c.propietario WHERE a.id = :id";

    private QueryConstantes() {
    }
}
