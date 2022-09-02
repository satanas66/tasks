package mercado.candidato.domain.mapper;

import mercado.candidato.domain.business.PosicionamientoBuscadores;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class PosicinamientoBuscadoresMapper {

    public PosicionamientoBuscadores setWebVisibilityAnalyticsProjection(PosicionamientoBuscadores posicionamientoBuscadores, Object[] projection) {
        if(projection == null){
            return posicionamientoBuscadores;
        }
        if(projection[15] != null){
            posicionamientoBuscadores.setNum_kw_en_top_10((Integer) projection[15]);
        }
        if(projection[32] != null){
            posicionamientoBuscadores.setKeyword_1((String)projection[32]);
        }
        if(projection[33] != null){
            posicionamientoBuscadores.setPosition_kw_1((Integer)projection[33]);
        }
        if(projection[34] != null){
            posicionamientoBuscadores.setKeyword_2((String)projection[34]);
        }
        if(projection[35] != null){
            posicionamientoBuscadores.setPosition_kw_2((Integer)projection[35]);
        }
        if(projection[36] != null){
            posicionamientoBuscadores.setKeyword_3((String)projection[36]);
        }
        if(projection[37] != null){
            posicionamientoBuscadores.setPosition_kw_3((Integer)projection[37]);
        }
        if(projection[38] != null){
            posicionamientoBuscadores.setKeyword_4((String)projection[38]);
        }
        if(projection[39] != null){
            posicionamientoBuscadores.setPosition_kw_4((Integer)projection[39]);
        }
        if(projection[40] != null){
            posicionamientoBuscadores.setKeyword_5((String)projection[40]);
        }
        if(projection[41] != null){
            posicionamientoBuscadores.setPosition_kw_5((Integer)projection[41]);
        }
        if(projection[42] != null){
            posicionamientoBuscadores.setKeyword_6((String)projection[42]);
        }
        if(projection[43] != null){
            posicionamientoBuscadores.setPosition_kw_6((Integer)projection[43]);
        }
        if(projection[44] != null){
            posicionamientoBuscadores.setKeyword_7((String)projection[44]);
        }
        if(projection[45] != null){
            posicionamientoBuscadores.setPosition_kw_7((Integer)projection[45]);
        }
        if(projection[46] != null){
            posicionamientoBuscadores.setKeyword_8((String)projection[46]);
        }
        if(projection[47] != null){
            posicionamientoBuscadores.setPosition_kw_8((Integer)projection[47]);
        }
        if(projection[48] != null){
            posicionamientoBuscadores.setKeyword_9((String)projection[48]);
        }
        if(projection[49] != null){
            posicionamientoBuscadores.setPosition_kw_9((Integer)projection[49]);
        }
        if(projection[50] != null){
            posicionamientoBuscadores.setKeyword_10((String)projection[50]);
        }
        if(projection[51] != null){
            posicionamientoBuscadores.setPosition_kw_10((Integer)projection[51]);
        }
        return posicionamientoBuscadores;
    }

    public PosicionamientoBuscadores setRankingNumberFromWebVisibilityAnalyticsProjection(PosicionamientoBuscadores posicionamientoBuscadores, Object[] projection){
        if(projection == null){
          return posicionamientoBuscadores;
        }
        if(projection[18] != null){
            posicionamientoBuscadores.setRanking_number(9);
            if(projection[18] instanceof Integer){
                posicionamientoBuscadores.setRanking_number((Integer) projection[18]);
            }else if(projection[18] instanceof Double){
                Double value = (Double) projection[18];
                if(value > 10.0){
                    posicionamientoBuscadores.setRanking_number(10);
                }
            }else if(projection[1] instanceof Boolean){
                if((boolean)projection[18]){
                    posicionamientoBuscadores.setRanking_number(10);
                }
            }
        }
        return posicionamientoBuscadores;
    }
}
