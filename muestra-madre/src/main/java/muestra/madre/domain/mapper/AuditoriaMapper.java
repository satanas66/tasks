package muestra.madre.domain.mapper;

import automation.factory.Utils;
import muestra.madre.domain.business.Auditoria;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase que permite la conversión de la proyección Auditoria en una entidad de negocio
 */
public class AuditoriaMapper {

    public Auditoria setAuditoriaProjection(Auditoria auditoria, Object[] projection){
        if(projection == null){
            return auditoria;
        }
        if(projection[0] != null){
            auditoria.setCo_cliente((Integer) projection[0]);
        }
        if(projection[1] != null && !"null".equalsIgnoreCase((String)projection[1])){
            auditoria.setFe_creacion(Utils.getDateFromString((String) projection[1]));
        }
        if(projection[2] != null && !"null".equalsIgnoreCase((String)projection[2])){
            auditoria.setFe_modificacion(Utils.getDateFromString((String) projection[2]));
        }
        if(projection[3] != null){
           auditoria.setOrigen_dato((String) projection[3]);
        }
        if(projection[4] != null){
            auditoria.setTipo_operacion((String) projection[4]);
        }

        return auditoria;
    }
}
