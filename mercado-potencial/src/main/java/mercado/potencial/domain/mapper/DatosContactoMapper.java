package mercado.potencial.domain.mapper;

import automation.factory.Utils;
import mercado.potencial.domain.business.DatosContacto;

import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase que permite la conversión de la proyección DatosContacto en una entidad de negocio
 */
public class DatosContactoMapper {

    public DatosContacto getDatosContactoFromProjection(DatosContacto datosContacto, Object[] projection) {
        if (projection == null) {
            return datosContacto;
        }
        if (projection[0] != null) {
            datosContacto.setCc_nom_empre(String.valueOf(projection[0]));
        }
        if (projection[1] != null) {
            datosContacto.setNo_comer(String.valueOf(projection[1]));
        }
        if (projection[2] != null) {
            datosContacto.setTx_actvad(String.valueOf(projection[2]));
        }
        if (projection[3] != null) {
            datosContacto.setDireccion(String.valueOf(projection[3]));
        }
        if (projection[4] != null) {
            datosContacto.setTx_loca_apa(String.valueOf(projection[4]));
        }
        if (projection[5] != null) {
            datosContacto.setDe_prov(String.valueOf(projection[5]));
        }
        if (projection[6] != null) {
            datosContacto.setCo_post_cto(String.valueOf(projection[6]));
        }
        if (projection[7] != null) {
            datosContacto.setTx_ccaa(String.valueOf(projection[7]));
        }
        if (projection[8] != null) {
            datosContacto.setTelefono1(String.valueOf(projection[8]));
        }
        if (projection[9] != null) {
            datosContacto.setEmail(String.valueOf(projection[9]));
        }
        if (projection[10] != null) {
            datosContacto.setNc_nif(String.valueOf(projection[10]));
        }
        if (projection[11] != null) {
            datosContacto.setCo_empresa((Integer) projection[11]);
        }
        if(projection[12] != null){
            datosContacto.setCo_actvad_pral((Integer) projection[12]);
        }
        if(projection[13] != null){
            datosContacto.setRating_crediticio((String) projection[13]);
        }
        return datosContacto;
    }

    public DatosContacto getDatosContactoFromTsiSectorProjection(DatosContacto datosContacto, List<Object[]> projections) {
        if(projections == null || projections.size() == 0){
            return datosContacto;
        }
        for (int i = 0; i < projections.size(); i++) {
            Object[] value = projections.get(i);
            if (Utils.evaluateString((String) value[1]) && (value[1]).equals("000")
                    && Utils.evaluateString((String) value[2]) && value[2].equals("000")) {
                String tx_sector = (String) value[0];
                datosContacto.setTx_sector(tx_sector);
                break;
            }
        }
        return datosContacto;
    }

    public DatosContacto setDomainFromWeVisibilityAnalyticsProjection(DatosContacto datosContacto, Object[] projection){
        if(projection == null){
            return datosContacto;
        }
        if(projection[0] != null){
            datosContacto.setDomain((String) projection[0]);
        }
        return datosContacto;
    }

    public DatosContacto setDatosContactoFromWebVisibilityAnalyticsProjection(DatosContacto datosContacto, Object[] projection){
        if(projection == null){
            return datosContacto;
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
