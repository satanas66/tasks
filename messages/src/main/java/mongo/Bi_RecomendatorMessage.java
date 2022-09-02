package mongo;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase utilidad en la que se personalizan los mensajes para la colección BI_RECOMENDADOR
 */
public class Bi_RecomendatorMessage {

    public static final String BI_RECOMENDATOR = "BI_RECOMENDATOR => ";

    public static final String NO_RESULT = BI_RECOMENDATOR+"No hay resultados para la consulta lanzada: ";

    public static final String GENERIC_ERROR = BI_RECOMENDATOR+"Ha ocurrido un error ejecutando la consulta: ";

    public static final String SEARCH_CLIENT = BI_RECOMENDATOR+"Buscando datos para el código de cliente ";

    public static final String CLIENT_FOUND = BI_RECOMENDATOR+"Se han encontrado resultados para el cliente: ";

    public static final String ERROR_CONNECTION_DB = BI_RECOMENDATOR+"Ha ocurrido un error en la conexión a la base de datos ";


}
