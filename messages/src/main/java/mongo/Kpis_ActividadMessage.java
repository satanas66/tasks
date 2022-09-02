package mongo;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase utilidad en la que se personalizan los mensajes para la colección KPIS_ACTIVIDAD
 */
public class Kpis_ActividadMessage {

    public static final String KPIS_ACTIVIDAD = "KPIS_ACTIVIDAD => ";

    public static final String NO_RESULT = KPIS_ACTIVIDAD+"No hay resultados para la consulta lanzada: ";

    public static final String GENERIC_ERROR = KPIS_ACTIVIDAD+"Ha ocurrido un error ejecutando la consulta: ";

    public static final String SEARCH_CLIENT = KPIS_ACTIVIDAD+"Buscando datos para el código de cliente ";

    public static final String CLIENT_FOUND = KPIS_ACTIVIDAD+"Se han encontrado resultados para el cliente: ";

    public static final String ERROR_CONNECTION_DB = KPIS_ACTIVIDAD+"Ha ocurrido un error en la conexión a la base de datos ";

    public static final String PROJECTION_BUILD = KPIS_ACTIVIDAD+"Constuyendo proyección";
}
