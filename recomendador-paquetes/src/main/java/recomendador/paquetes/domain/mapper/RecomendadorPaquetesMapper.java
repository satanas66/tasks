package recomendador.paquetes.domain.mapper;

import recomendador.paquetes.domain.business.RecomendadorPaquetes;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase que permite la conversión de proyecciones en entidades de negocio
 */
public class RecomendadorPaquetesMapper {

    public RecomendadorPaquetes setKpisRecomendadorPaquetes(RecomendadorPaquetes recomendadorPaquetes, Object[] projection){
        if(projection == null){
            return recomendadorPaquetes;
        }

        if(projection[0] != null){
            recomendadorPaquetes.setCo_cliente((Integer) projection[0]);
        }
        if(projection[1] != null){

            if(projection[1] instanceof Integer){
                recomendadorPaquetes.setRanking_number((Integer) projection[1]);
            }else if(projection[1] instanceof Double){
                recomendadorPaquetes.setRanking_number(9);
                Double value = (Double) projection[1];
                if(value > 10.0){
                    recomendadorPaquetes.setRanking_number(10);
                }
            }else{
                recomendadorPaquetes.setRanking_number(9);

                boolean value = (boolean) projection[1];
                if(value){
                    recomendadorPaquetes.setRanking_number(10);
                }
            }
        }
        if(projection[2] != null){
            recomendadorPaquetes.setReviews((Integer) projection[2]);
        }
        if(projection[3] != null){
            recomendadorPaquetes.setWebsite((String) projection[3]);
        }
        if(projection[4] != null){
            recomendadorPaquetes.setClaim_business("No Claim this business y No Manage this listing");
            if((boolean)projection[4]){
                recomendadorPaquetes.setClaim_business("Is one of the two (Claim this business or Manage this listing)");
            }
        }
        if(projection[5] != null){
            recomendadorPaquetes.setNo_https((String) projection[5]);
        }
        if(projection[6] != null){
            recomendadorPaquetes.setNo_sitemaps((String) projection[6]);
        }
        if(projection[7] != null){
            recomendadorPaquetes.setNo_robots_txt((String) projection[7]);
        }
        if(projection[8] != null){
            recomendadorPaquetes.setMenos_2_links_entrantes((String) projection[8]);
        }
        if(projection[9] != null){
            recomendadorPaquetes.setNum_kw_en_top_10((Integer) projection[9]);
        }
        if(projection[10] != null){
            recomendadorPaquetes.setNum_de_kw_detectadas((Integer) projection[10]);
        }
        if(projection[11] != null){
            recomendadorPaquetes.setMas_5segs_carga_movil((String) projection[11]);
        }
        if(projection[12] != null){
            recomendadorPaquetes.setNo_responsive((String) projection[12]);
        }
        if(projection[13] != null){
            recomendadorPaquetes.setPoco_contenido((String) projection[13]);
        }
        if(projection[14] != null){
            recomendadorPaquetes.setErrores_en_alt((String) projection[14]);
        }
        if(projection[15] != null){
            recomendadorPaquetes.setErrores_en_title((String) projection[15]);
        }
        if(projection[16] != null){
            recomendadorPaquetes.setNumero_de_paginas((Integer) projection[16]);
        }
        if(projection[17] != null){
            recomendadorPaquetes.setEcommerce_checkout_link((String) projection[17]);
        }
        if(projection[18] != null){
            recomendadorPaquetes.setSocial_links_facebook((String) projection[18]);
        }
        return recomendadorPaquetes;
    }
}
