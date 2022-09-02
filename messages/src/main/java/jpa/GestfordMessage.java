package jpa;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase utilidad en la que se personalizan los mensajes para el esquema GESTFORD
 *
 */
public class GestfordMessage {

    public static final String GESTFORD = "GESTFORD => ";

    public static final String NO_RESULT = GESTFORD+"No hay resultados para la consulta lanzada: ";

    public static final String GENERIC_ERROR = GESTFORD+"Ha ocurrido un error ejecutando la consulta: ";

    public static final String SEARCH_CLIENT = GESTFORD+"Buscando datos para el código de cliente ";

    public static final String CLIENT_FOUND = GESTFORD+"Se han encontrado resultados para el cliente: ";
}
