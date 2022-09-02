package mongo;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase utilidad en la que se personalizan los mensajes para la colección WEBVISIBILITYANALYTICS
 */
public class WebVisibilityAnalyticsMessage {

    public static final String WEBVISIBILITYANALYTICS = "WEBVISIBILITYANALYTICS => ";

    public static final String NO_RESULT = WEBVISIBILITYANALYTICS+"No hay resultados para la consulta lanzada: ";

    public static final String GENERIC_ERROR = WEBVISIBILITYANALYTICS+"Ha ocurrido un error ejecutando la consulta: ";

    public static final String SEARCH_CLIENT = WEBVISIBILITYANALYTICS+"Buscando datos para el código de cliente ";

    public static final String CLIENT_FOUND = WEBVISIBILITYANALYTICS+"Se han encontrado resultados para el cliente: ";

    public static final String ERROR_CONNECTION_DB = WEBVISIBILITYANALYTICS+"Ha ocurrido un error en la conexión a la base de datos ";
}
