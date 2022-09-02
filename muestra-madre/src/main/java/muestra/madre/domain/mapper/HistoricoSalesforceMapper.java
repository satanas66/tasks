package muestra.madre.domain.mapper;

import muestra.madre.domain.business.HistoricoSalesforce;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase que permite la conversión de la proyección HistoricoSalesforce en una entidad de negocio
 */
public class HistoricoSalesforceMapper {

    public HistoricoSalesforce setTdv_Oportunidades_Por_Cliente_Projection(HistoricoSalesforce historicoSalesforce, Object[] projection){
        if(projection == null){
            return historicoSalesforce;
        }
        if(projection[0] != null){
            historicoSalesforce.setLw((String) projection[0]);
        }
        if(projection[1] != null){
            historicoSalesforce.setLo((String)projection[1]);
        }
        return historicoSalesforce;
    }

    public HistoricoSalesforce setListingVersionFromWeVisibilityAnalyticsProjection(HistoricoSalesforce historicoSalesforce, Object[] projection){
        if(projection == null){
            return historicoSalesforce;
        }
        if(projection[1] != null){
            historicoSalesforce.setListingVersion(Integer.parseInt((String)projection[1]));
        }
        return historicoSalesforce;
    }
}
