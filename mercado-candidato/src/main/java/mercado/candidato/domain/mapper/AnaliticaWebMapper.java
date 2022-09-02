package mercado.candidato.domain.mapper;

import mercado.candidato.domain.business.AnaliticaWeb;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase que permite la conversión de proyecciones en entidades de negocio
 */
public class AnaliticaWebMapper {

    public AnaliticaWeb setZoomFromGestorOportunidadesProjection(AnaliticaWeb analiticaWeb, Object[] projection) {
        if (projection == null) {
            return analiticaWeb;
        }
        if (projection[0] != null) {
            String zoom = evaluateProjectionFromGestorOportunidadesProjection(projection);
            analiticaWeb.setZoom(zoom);
        }
        return analiticaWeb;
    }

    /**
     * Mátodo que evalua el valor del estado de un regitro y si tiene fecha de baja
     * @param projection
     * @return
     */
    private String evaluateProjectionFromGestorOportunidadesProjection(Object[] projection) {
        String result = "NO";
        String estado_reg = String.valueOf(projection[0]);
        if ((estado_reg.equalsIgnoreCase("DISPONIBLE") || estado_reg.equalsIgnoreCase("ZOOM TV")) &&
                projection[1] == null) {
            result = "SI";
        }
        return result;
    }

    public AnaliticaWeb setWebVisibilityAnalyticsProjection(AnaliticaWeb analiticaWeb, Object[] projection){
        if(projection == null){
            return analiticaWeb;
        }
        if(projection[2] != null){
            analiticaWeb.setExists_url(String.valueOf(projection[2]));
        }
        if(projection[3] != null){
            analiticaWeb.setNumero_de_paginas((Integer) projection[3]);
        }
        if(projection[4] != null){
            analiticaWeb.setCms((String) projection[4]);
        }
        if(projection[5] != null){
            analiticaWeb.setMas_5segs_carga_movil(String.valueOf(projection[5]));
        }
        if(projection[24] != null){
            analiticaWeb.setMobilewidth(Integer.parseInt(String.valueOf(projection[24])));
        }
        if(projection[25] != null){
            analiticaWeb.setMobileheigth(Integer.parseInt(String.valueOf(projection[25])));
        }
        if(projection[26] != null){
            analiticaWeb.setEcommerce_checkout_link((String) projection[26]);
        }
        if(projection[27] != null){
            analiticaWeb.setEcommerce_booking_type((String) projection[27]);
        }
        if(projection[28] != null){
            analiticaWeb.setSocial_link_facebook((String) projection[28]);
        }
        if(projection[29] != null){
            analiticaWeb.setSocial_link_twitter((String) projection[29]);
        }
        if(projection[30] != null){
            analiticaWeb.setSocial_link_instagram((String) projection[30]);
        }
        if(projection[31] != null){
            analiticaWeb.setSocial_link_linkedin((String) projection[31]);
        }
        return analiticaWeb;
    }
}
