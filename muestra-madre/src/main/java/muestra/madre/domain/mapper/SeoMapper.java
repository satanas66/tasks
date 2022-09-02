package muestra.madre.domain.mapper;

import muestra.madre.domain.business.Seo;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase que permite la conversión de la proyección Seo en una entidad de negocio
 */
public class SeoMapper {

    public Seo setWebVisibilityAnalyticsProjection(Seo seo, Object[] projection){
        if(projection == null){
            return seo;
        }
        if(projection[6] != null){
            seo.setMenos_2_links_entrantes(String.valueOf(projection[6]));
        }
        if(projection[7] != null){
            seo.setNo_responsive(String.valueOf(projection[7]));
        }
        if(projection[8] != null){
            seo.setPoco_contenido(String.valueOf(projection[8]));
        }
        if(projection[9] != null){
            seo.setErrores_en_alt(String.valueOf(projection[9]));
        }
        if(projection[10] != null){
            seo.setErrores_en_title(String.valueOf(projection[10]));
        }
        if(projection[11] != null){
            seo.setNo_https(String.valueOf(projection[11]));
        }
        if(projection[12] != null){
            seo.setNo_sitemaps(String.valueOf(projection[12]));
        }
        if(projection[13] != null){
            seo.setNo_robots_txt(String.valueOf(projection[13]));
        }
        if(projection[14] != null){
            seo.setNum_de_kw_detectadas((Integer) projection[14]);
        }
        return seo;
    }
}
