package mercado.potencial.domain.mapper;

import mercado.potencial.domain.business.DatosContacto;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase que permite la conversión de la proyección DatosContacto en una entidad de negocio
 */
public class DatosContactoMapper {

    public DatosContacto setDatosContactoFromWebVisibilityAnalyticsProjection(DatosContacto datosContacto, Object[] projection){
        if(projection == null){
            return datosContacto;
        }
        if(projection[0] != null){
            datosContacto.setDomain((String) projection[0]);
        }
        if(projection[16] != null){
            datosContacto.setTelefono_num((String) projection[16]);
        }
        if(projection[17] != null){
            datosContacto.setWebsite((String) projection[17]);
        }
        return datosContacto;
    }
}
