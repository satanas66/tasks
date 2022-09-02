package jpa;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase utilidad en la que se personalizan los mensajes para el esquema SISORA
 *
 */
public class SisoraMessage {

    public static final String SISORA = "SISORA => ";

    public static final String NO_RESULT = SISORA+"No hay resultados para la consulta lanzada: ";

    public static final String GENERIC_ERROR = SISORA+"Ha ocurrido un error ejecutando la consulta: ";

    public static final String SEARCH_CLIENT = SISORA+"Buscando datos para el código de cliente ";

    public static final String CLIENT_FOUND = SISORA+"Se han encontrado resultados para el cliente: ";
}
