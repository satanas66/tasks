package recurrent.domain.mapper;

import automation.factory.Utils;
import jpa.entity.phw_vac.Gestor_Oportunidades;
import recurrent.domain.business.GestorOportunidades;

import java.util.Date;

/**
 * @author Edwin Patricio Arévalo Angulo
 * <p>
 * Clase que permite la conversión de una proyección a una entidad de negocio o entidad de dominio
 */
public class GestorOportunidadesMapper {

    /**
     * Método que convierte una cadena a una clase de negocio GestorOportunidades
     *
     * @param line
     * @return clase de nogocio GestorOportunidades
     */
    public GestorOportunidades getGestorOportunidadesFromString(String line) {
        GestorOportunidades result = null;
        if (line.isEmpty()) {
            return null;
        }
        String[] values = line.split(";");

        result = new GestorOportunidades();
        if (Utils.evaluateString(values[0])) {
            Integer co_cliente = Utils.getIntegerByString(values[0]);
            result.setCo_cliente(co_cliente);
        } else {
            return null;
        }
        if (Utils.evaluateString(values[1])) {
            if ("SYSDATE".equalsIgnoreCase(values[1])) {
                result.setFe_inicio_reg(new Date());
            } else {
                Date fe_inicio_reg = Utils.getDateFromStringWithoutTime(values[1]);
                result.setFe_inicio_reg(fe_inicio_reg);
            }
        } else {
            return null;
        }
        if (Utils.evaluateString(values[2])) {
            result.setId_opp_serv(Integer.parseInt(values[2]));
        }
        if (Utils.evaluateString(values[3])) {
            if ("SYSDATE".equalsIgnoreCase(values[3])) {
                result.setFe_fin_reg(new Date());
            } else {
                Date fe_fin_reg = Utils.getDateFromStringWithoutTime(values[3]);
                result.setFe_fin_reg(fe_fin_reg);
            }
        } else {
            return null;
        }
        if (Utils.evaluateString(values[4])) {
            result.setDesc_oportunidad_larga(values[4]);
        }
        if (Utils.evaluateString(values[5])) {
            if ("SYSDATE".equalsIgnoreCase(values[5])) {
                result.setFe_baja_reg(new Date());
            } else {
                Date fe_fin_reg = Utils.getDateFromStringWithoutTime(values[5]);
                result.setFe_baja_reg(fe_fin_reg);
            }
        }
        if (Utils.evaluateString(values[6])) {
            result.setId_account(values[6]);
        } else {
            return null;
        }
        if (Utils.evaluateString(values[7])) {
            result.setOrigen_oportunidad(values[7]);
        }
        if (Utils.evaluateString(values[8])) {
            result.setTipo_oportunidad(values[8]);
        }
        if (Utils.evaluateString(values[9])) {
            result.setDesc_oportunidad(values[9]);
        }
        if (Utils.evaluateString(values[10])) {
            result.setAsig_oportunidad(values[10]);
        } else {
            return null;
        }
        if (Utils.evaluateString(values[11])) {
            result.setId_usuario(values[11]);
        }
        if (Utils.evaluateString(values[12])) {
            result.setId_camp_salesforce(values[12]);
        }
        if (Utils.evaluateString(values[13])) {
            result.setZoom(values[13]);
        }
        if (Utils.evaluateString(values[14])) {
            result.setConcurrencia(values[14]);
        }
        if (Utils.evaluateString(values[15])) {
            result.setEstado_reg(values[15]);
        }
        if (Utils.evaluateString(values[16])) {
            result.setEstado_reg_salesforce(values[16]);
        }
        if (Utils.evaluateString(values[17])) {
            result.setEstado_account(values[17]);
        }
        if (Utils.evaluateString(values[18])) {
            result.setCarga_de_trabajo(Integer.parseInt(values[18]));
        }
        if (Utils.evaluateString(values[19])) {
            result.setPrioridad(Integer.parseInt(values[19]));
        }
        if (Utils.evaluateString(values[20])) {
            result.setRanking(Integer.parseInt(values[20]));
        }
        if (Utils.evaluateString(values[21])) {
            result.setUser_crea_reg(values[21]);
        }
        if (Utils.evaluateString(values[22])) {
            result.setUser_modi_reg(values[22]);
        }
        if (Utils.evaluateString(values[23])) {
            if ("SYSDATE".equalsIgnoreCase(values[23])) {
                result.setFe_crea_reg(new Date());
            } else {
                Date fe_crea_reg = Utils.getDateFromStringWithoutTime(values[23]);
                result.setFe_crea_reg(fe_crea_reg);
            }
        }
        if (Utils.evaluateString(values[24])) {
            if ("SYSDATE".equalsIgnoreCase(values[24])) {
                result.setFe_modi_reg(new Date());
            } else {
                Date fe_modi_reg = Utils.getDateFromStringWithoutTime(values[24]);
                result.setFe_modi_reg(fe_modi_reg);
            }
        }
        if (Utils.evaluateString(values[25])) {
            result.setCo_camp(values[25]);
        }
        if (Utils.evaluateString(values[26])) {
            result.setCo_ambi_comer(values[26]);
        }
        if (Utils.evaluateString(values[27])) {
            result.setTerritorio(values[27]);
        }
        if (Utils.evaluateString(values[28])) {
            result.setValor_opp(Integer.parseInt(values[28]));
        }
        if (Utils.evaluateString(values[29])) {
            result.setTx_error(values[29]);
        }
        if (Utils.evaluateString(values[30])) {
            result.setId_opp(values[30]);
        }
        if (Utils.evaluateString(values[31])) {
            result.setTrimestre(values[31]);
        }
        if (Utils.evaluateString(values[32])) {
            result.setMes_obj(values[32]);
        }
        if (Utils.evaluateString(values[33])) {
            result.setOferta_valor(values[33]);
        }
        if (Utils.evaluateString(values[34])) {
            if ("SYSDATE".equalsIgnoreCase(values[34])) {
                result.setFe_limite_gestion(new Date());
            } else {
                Date fe_limite_gestion = Utils.getDateFromStringWithoutTime(values[34]);
                result.setFe_limite_gestion(fe_limite_gestion);
            }
        }
        if (Utils.evaluateString(values[35])) {
            result.setCierre_automatico(values[35]);
        }
        if (Utils.evaluateString(values[36])) {
            result.setRecarga_pre(values[36]);
        }
        if (Utils.evaluateString(values[37])) {
            result.setCanal_inicial(values[37]);
        }
        if (Utils.evaluateString(values[38])) {
            result.setProveedor_inicial(values[38]);
        }
        if (Utils.evaluateString(values[39])) {
            result.setCo_sector(values[39]);
        }
        if (Utils.evaluateString(values[40])) {
            result.setTx_sector(values[40]);
        }
        if (Utils.evaluateString(values[41])) {
            result.setCo_autonomia(values[41]);
        }
        if (Utils.evaluateString(values[42])) {
            result.setTx_autonomia(values[42]);
        }

        if (values.length > 43) {
            String municipio = values[42];
            for (int i = 43; i < values.length; i++) {
                if (Utils.evaluateString(values[i])) {
                    municipio = municipio + ", " + values[i];
                }
            }
            result.setTx_autonomia(municipio);
        }
        return result;
    }

    /**
     * Método que convierte la clase de negocio GestorOportunidades a la clase entidad
     *
     * @param gestorOportunidades
     * @return entidad Gestor_Oportunidades
     */
    public Gestor_Oportunidades getGestor_OportunidadesFromString(GestorOportunidades gestorOportunidades) {
        if (gestorOportunidades == null) {
            return null;
        }
        Gestor_Oportunidades result = Gestor_Oportunidades.builder()
                .co_cliente(gestorOportunidades.getCo_cliente())
                .id_account(gestorOportunidades.getId_account())
                .origen_oportunidad(gestorOportunidades.getOrigen_oportunidad())
                .tipo_oportunidad(gestorOportunidades.getTipo_oportunidad())
                .desc_oportunidad(gestorOportunidades.getDesc_oportunidad())
                .asig_oportunidad(gestorOportunidades.getAsig_oportunidad())
                .id_usuario(gestorOportunidades.getId_usuario())
                .id_camp_salesforce(gestorOportunidades.getId_camp_salesforce())
                .fe_inicio_reg(gestorOportunidades.getFe_inicio_reg())
                .fe_fin_reg(gestorOportunidades.getFe_inicio_reg())
                .zoom(gestorOportunidades.getZoom())
                .concurrencia(gestorOportunidades.getConcurrencia())
                .estado_reg(gestorOportunidades.getEstado_reg())
                .estado_reg_salesforce(gestorOportunidades.getEstado_reg_salesforce())
                .estado_account(gestorOportunidades.getEstado_account())
                .carga_de_trabajo(gestorOportunidades.getCarga_de_trabajo())
                .prioridad(gestorOportunidades.getPrioridad())
                .ranking(gestorOportunidades.getRanking())
                .user_crea_reg(gestorOportunidades.getUser_crea_reg())
                .user_modi_reg(gestorOportunidades.getUser_modi_reg())
                .fe_crea_reg(gestorOportunidades.getFe_crea_reg())
                .fe_modi_reg(gestorOportunidades.getFe_modi_reg())
                .fe_baja_reg(gestorOportunidades.getFe_baja_reg())
                .desc_oportunidad_larga(gestorOportunidades.getDesc_oportunidad_larga())
                .co_camp(gestorOportunidades.getCo_camp())
                .co_ambi_comer(gestorOportunidades.getCo_ambi_comer())
                .territorio(gestorOportunidades.getTerritorio())
                .valor_opp(gestorOportunidades.getValor_opp())
                .tx_error(gestorOportunidades.getTx_error())
                .id_opp(gestorOportunidades.getId_opp())
                .id_opp_serv(gestorOportunidades.getId_opp_serv())
                .trimestre(gestorOportunidades.getTrimestre())
                .mes_obj(gestorOportunidades.getMes_obj())
                .oferta_valor(gestorOportunidades.getOferta_valor())
                .fe_limite_gestion(gestorOportunidades.getFe_limite_gestion())
                .cierre_automatico(gestorOportunidades.getCierre_automatico())
                .recarga_pre(gestorOportunidades.getRecarga_pre())
                .canal_inicial(gestorOportunidades.getCanal_inicial())
                .proveedor_inicial(gestorOportunidades.getProveedor_inicial())
                .asignado_por(gestorOportunidades.getAsignado_por())
                .co_sector(gestorOportunidades.getCo_sector())
                .tx_sector(gestorOportunidades.getTx_sector())
                .tx_asignacion(gestorOportunidades.getTx_asignacion())
                .co_autonomia(gestorOportunidades.getCo_autonomia())
                .tx_autonomia(gestorOportunidades.getTx_autonomia())
                .build();
        return result;
    }
}
