package mongo;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase utilidad en la que se personalizan los mensajes para la colección KPIS_TIPOL_ACTIVIDAD
 */
public class Kpis_Tipol_ActividadMessage {

    public static final String KPIS_TIPOL_ACTIVIDAD = "KPIS_TIPOL_ACTIVIDAD => ";

    public static final String NO_RESULT = KPIS_TIPOL_ACTIVIDAD+"No hay resultados para la consulta lanzada: ";

    public static final String GENERIC_ERROR = KPIS_TIPOL_ACTIVIDAD+"Ha ocurrido un error ejecutando la consulta: ";

    public static final String SEARCH_CLIENT = KPIS_TIPOL_ACTIVIDAD+"Buscando datos para el código de cliente ";

    public static final String CLIENT_FOUND = KPIS_TIPOL_ACTIVIDAD+"Se han encontrado resultados para el cliente: ";

    public static final String ERROR_CONNECTION_DB = KPIS_TIPOL_ACTIVIDAD+"Ha ocurrido un error en la conexión a la base de datos ";

    public static final String PROJECTION_BUILD = KPIS_TIPOL_ACTIVIDAD+"Constuyendo proyección";
}
