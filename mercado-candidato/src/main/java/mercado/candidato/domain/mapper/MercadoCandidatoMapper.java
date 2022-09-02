package mercado.candidato.domain.mapper;

import mercado.candidato.domain.business.KpisMercadoCandidato;

import java.util.Date;

public class MercadoCandidatoMapper {

    public KpisMercadoCandidato setKpis_ActividadProjection(KpisMercadoCandidato kpisMercadoCandidato, Object[] projection) {
        if (projection == null) {
            return kpisMercadoCandidato;
        }
        if (projection[0] != null) {
            kpisMercadoCandidato.setCt_merclie(String.valueOf(projection[0]));
        }
        if (projection[1] != null) {
            kpisMercadoCandidato.setTipo_bs(String.valueOf(projection[1]));
        }
        return kpisMercadoCandidato;
    }

    public KpisMercadoCandidato setKpis_CalculadosProjection(KpisMercadoCandidato kpisMercadoCandidato, Object[] projection) {
        if (projection == null) {
            return kpisMercadoCandidato;
        }
        if (projection[0] != null) {
            kpisMercadoCandidato.setNeg_est((Double) projection[0]);
        }
        if (projection[1] != null) {
            kpisMercadoCandidato.setSegmento(String.valueOf(projection[1]));
        }
        if (projection[2] != null) {
            kpisMercadoCandidato.setNew_segm(String.valueOf(projection[2]));
        }
        if (projection[3] != null) {
            kpisMercadoCandidato.setPr_cross_selling((Double) projection[3]);
        }
        if (projection[4] != null) {
            kpisMercadoCandidato.setPr_up_selling((Double) projection[4]);
        }
        if (projection[5] != null) {
            kpisMercadoCandidato.setIc_pred((Double) projection[5]);
        }
        return kpisMercadoCandidato;
    }

    public KpisMercadoCandidato setThv_Actividad_Comercial_Cons_v2Projection(KpisMercadoCandidato kpisMercadoCandidato, Object[] projection) {
        if (projection == null) {
            return kpisMercadoCandidato;
        }
        if (projection[0] != null) {
            kpisMercadoCandidato.setFe_hora_actividad((Date) projection[0]);
        }
        if (projection[1] != null) {
            kpisMercadoCandidato.setSubtipo_actividad(String.valueOf(projection[1]));
        }
        if (projection[2] != null) {
            kpisMercadoCandidato.setOrigen_llamada(String.valueOf(projection[2]));
        }
        if (projection[3] != null) {
            kpisMercadoCandidato.setId_usuario(String.valueOf(projection[3]));
        }
        if (projection[4] != null) {
            kpisMercadoCandidato.setIn_util((Integer) projection[4]);
        }
        if (projection[5] != null) {
            kpisMercadoCandidato.setDuracion_llamada((Integer) projection[5]);
        }
        return kpisMercadoCandidato;
    }

    public KpisMercadoCandidato setVdv_asignacion_cuenta_ecProjection(KpisMercadoCandidato kpisMercadoCandidato, Object[] projection) {
        if (projection == null) {
            return kpisMercadoCandidato;
        }
        if (projection[0] != null) {
            kpisMercadoCandidato.setCo_vend_cliente(String.valueOf(projection[0]));
        }
        if (projection[1] != null) {
            kpisMercadoCandidato.setCo_canal(String.valueOf(projection[1]));
        }
        return kpisMercadoCandidato;
    }

    public KpisMercadoCandidato setMercanVhv_cuotas_mes_v2Projection(KpisMercadoCandidato kpisMercadoCandidato, Object[] projection) {
        if (projection == null) {
            return kpisMercadoCandidato;
        }
        if (projection[0] != null) {
            kpisMercadoCandidato.setImporte((Double) projection[0]);
        }
        if (projection[1] != null) {
            kpisMercadoCandidato.setIdmes((Integer) projection[1]);
        }
        return kpisMercadoCandidato;
    }

    public KpisMercadoCandidato setTdv_UsuarioProjection(KpisMercadoCandidato kpisMercadoCandidato, Object[] projection) {
        if (projection == null) {
            return kpisMercadoCandidato;
        }
        if (projection[0] != null) {
            kpisMercadoCandidato.setNombre_vendedor((String) projection[0]);
        }
        if (projection[1] != null) {
            kpisMercadoCandidato.setRol_vendedor((String) projection[1]);
        }
        return kpisMercadoCandidato;
    }



}
