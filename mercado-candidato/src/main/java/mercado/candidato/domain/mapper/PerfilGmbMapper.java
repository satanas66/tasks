package mercado.candidato.domain.mapper;

import automation.factory.Utils;
import mercado.candidato.domain.business.PerfilGmb;

import java.util.Date;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class PerfilGmbMapper {

    public PerfilGmb getPerfilGmbFromWebVisibilityAnalyticsProjection(PerfilGmb perfilGmb, Object[] projection){
        if(projection ==  null){
            return perfilGmb;
        }
        if(projection[19] != null){
            String page_url = getPageUrl((String) projection[19]);
            perfilGmb.setPage_url(page_url);
        }
        if(projection[20] != null && projection[20] instanceof String){
            perfilGmb.setNombre_google((String) projection[20]);
        }
        if(projection[21] != null){
            perfilGmb.setReviews((Integer) projection[21]);
        }
        if(projection[22] != null){
            perfilGmb.setTotal_rating(Double.valueOf((String.valueOf(projection[22]))));
        }
        if(projection[23] != null){
            perfilGmb.setClaim_business("No Claim this business y No Manage this listing");
            if((boolean)projection[23]){
                perfilGmb.setClaim_business("Is one of the two (Claim this business or Manage this listing)");
            }
        }
        if(projection[52] != null){
            perfilGmb.setTimestamp((Date) projection[52]);
        }

        return perfilGmb;
    }

    private String getPageUrl(String page_url){
        return Utils.deleteNomenclatureInEnglish(page_url);
    }
}
