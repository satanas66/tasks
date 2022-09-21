package potencial.domain.mapper;

import potencial.domain.business.DatosContacto;

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
            datosContacto.setNc_nif(String.valueOf(projection[0]));
        }
        if (projection[1] != null) {
            datosContacto.setCc_nom_empre(String.valueOf(projection[1]));
        }
        if (projection[2] != null) {
            datosContacto.setNo_comer(String.valueOf(projection[2]));
        }
        if (projection[3] != null) {
            datosContacto.setTx_actvad(String.valueOf(projection[3]));
        }
        if (projection[4] != null) {
            datosContacto.setDe_prov(String.valueOf(projection[4]));
        }
        if (projection[5] != null) {
            datosContacto.setTx_loca_apa(String.valueOf(projection[5]));
        }
        if (projection[6] != null) {
            datosContacto.setDireccion(String.valueOf(projection[6]));
        }
        if (projection[7] != null) {
            datosContacto.setCo_post_cto(String.valueOf(projection[7]));
        }
        if (projection[8] != null) {
            datosContacto.setTx_ccaa(String.valueOf(projection[8]));
        }
        if (projection[9] != null) {
            datosContacto.setTelefono1(String.valueOf(projection[9]));
        }
        if (projection[10] != null) {
            datosContacto.setTelefono2(String.valueOf(projection[10]));
        }
        if (projection[11] != null) {
            datosContacto.setTelefono3(String.valueOf(projection[11]));
        }
        if (projection[12] != null) {
            datosContacto.setTelefono4(String.valueOf(projection[12]));
        }
        if (projection[13] != null) {
            datosContacto.setTelefono5(String.valueOf(projection[13]));
        }
        if (projection[14] != null) {
            datosContacto.setCo_actvad_pral((Integer)(projection[14]));
        }

        return datosContacto;
    }
}
