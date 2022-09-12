package mercado.potencial.domain.mapper;

import mercado.potencial.domain.business.MorfologiaPyme;

import java.util.Date;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase que permite la conversión de la proyección MorfologiaPyme en una entidad de negocio
 */
public class MorfologiaPymeMapper {

    /**
     * Método que setea los valores obtenidos de la proyección Tsf_AccountProjection
     * @param morfologiaPyme
     * @param projection
     * @return
     */
    public MorfologiaPyme setTsf_AccountProjection(MorfologiaPyme morfologiaPyme, Object[] projection) {
        if (projection == null) {
            return morfologiaPyme;
        }
        if(projection[0] != null){
            morfologiaPyme.setCreateddate((Date) projection[0]);
        }
        if(projection[1] != null){
            morfologiaPyme.setAccountId((String) projection[1]);
        }
        String rgpd = evaluateRgpdFromTsf_AccountProjection(projection);
        morfologiaPyme.setRgpd(rgpd);
        return morfologiaPyme;
    }

    /**
     * Metodo que evalua los resultados rgpd, si alguno de ellos es true entonces S caso contrario N
     *
     * @param projection
     * @return
     */
    private String evaluateRgpdFromTsf_AccountProjection(Object[] projection) {
        String result = "N";
        if ((projection[2] != null && projection[2].equals("true")) ||
                (projection[3] != null && projection[3].equals("true")) ||
                (projection[4] != null && projection[4].equals("true"))) {
            result = "S";
        }
        return result;
    }
}
