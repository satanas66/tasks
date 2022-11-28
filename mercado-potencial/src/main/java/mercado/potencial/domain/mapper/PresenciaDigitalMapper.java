package mercado.potencial.domain.mapper;

import mercado.potencial.domain.business.PresenciaDigital;

public class PresenciaDigitalMapper {

    public PresenciaDigital getPresenciaDigitalFromWebVisibilityAnalyticsProjection(PresenciaDigital presenciaDigital, Object[] projection) {

        if(projection ==  null){
            return presenciaDigital;
        }

        if(projection[54] != null){
            presenciaDigital.setKpiSeg400((Integer) projection[54]);
        }
        if(projection[55] != null){
            presenciaDigital.setKpiSeg32((Boolean) projection[55]);
        }
        if(projection[56] != null){
            presenciaDigital.setKpiSeg122((Integer) projection[56]);
        }
        if(projection[57] != null){
            presenciaDigital.setKpiSeg401((Integer) projection[57]);
        }
        if(projection[58] != null){
            presenciaDigital.setKpiSeg303((Integer) projection[58]);
        }
        if(projection[59] != null){
            presenciaDigital.setKpiSeg23((Integer) projection[59]);
        }
        if(projection[60] != null){
            presenciaDigital.setKpiSeg402((Integer) projection[60]);
        }
        if(projection[61] != null){
            presenciaDigital.setKpiSeg05((Boolean) projection[61]);
        }
        if(projection[62] != null){
            presenciaDigital.setKpiSeg07((Boolean) projection[62]);
        }
        if(projection[63] != null){
            presenciaDigital.setKpiSeg08((Boolean) projection[63]);
        }
        if(projection[64] != null){
            presenciaDigital.setKpiSeg09((Boolean) projection[64]);
        }
        if(projection[65] != null){
            presenciaDigital.setKpiSeg10((Boolean) projection[65]);
        }
        if(projection[66] != null){
            presenciaDigital.setKpiSeg11((Boolean) projection[66]);
        }
        if(projection[67] != null){
            presenciaDigital.setKpiSeg12((Boolean) projection[67]);
        }
        if(projection[68] != null){
            presenciaDigital.setKpiSeg13((Boolean) projection[68]);
        }
        if(projection[69] != null){
            presenciaDigital.setKpiSeg15((Boolean) projection[69]);
        }
        if(projection[70] != null){
            presenciaDigital.setKpiSeg16((Boolean) projection[70]);
        }
        if(projection[71] != null){
            presenciaDigital.setKpiSeg404((Double) projection[71]);
        }
        return presenciaDigital;
    }
}