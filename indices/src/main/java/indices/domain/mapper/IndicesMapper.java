package indices.domain.mapper;

import indices.domain.business.IndiceVisibilidad;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase que permite la conversión de un aproyección a una entidad de negocio
 */
public class IndicesMapper {

    public IndiceVisibilidad getIndiceVisibilidadFromProjection(IndiceVisibilidad indiceVisibilidad, String[] projection){
        if(projection == null){
            return indiceVisibilidad;
        }
        if(projection[0] != null){
            indiceVisibilidad.setCo_cliente(Integer.parseInt(projection[0]));
        }
        if(projection[1] != null){
            indiceVisibilidad.setCo_actividad(Integer.parseInt(projection[1]));
        }
        if(projection[2] != null && !projection[2].equals("null")){
            indiceVisibilidad.setKeywordTop10(Integer.parseInt(projection[2]));
        }
        if(projection[3] != null && !projection[3].equals("null")){
            indiceVisibilidad.setPosicionGMB(Integer.parseInt(projection[3]));
        }
        if(projection[4] != null){
            indiceVisibilidad.setCo_sector(projection[4]);
        }
        return indiceVisibilidad;
    }
}
