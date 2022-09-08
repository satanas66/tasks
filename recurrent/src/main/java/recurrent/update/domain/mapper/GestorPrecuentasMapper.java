package recurrent.update.domain.mapper;

import automation.factory.Utils;
import recurrent.update.domain.business.GestorPrecuentas;

import java.util.Date;

public class GestorPrecuentasMapper {

    public GestorPrecuentas getGestorPrecuentasFromString(String line) {
        GestorPrecuentas gestorPrecuentas = null;
        if (line.isEmpty()) {
            return null;
        }
        String[] values = line.split(",");
        if(values.length == 8){
            gestorPrecuentas = getGestorPrecuentas(values);
            if(Utils.evaluateString(values[4])){
                Date fe_inicio;
                if("SYSDATE".equalsIgnoreCase(values[4])){
                    fe_inicio = new Date();
                }else{
                    fe_inicio = Utils.getDateFromString(values[4]);
                }
                gestorPrecuentas.setFeInicio(fe_inicio);
            }
            if(Utils.evaluateString(values[5])){
                Date fe_modi_reg;
                if("SYSDATE".equalsIgnoreCase(values[5])){
                    fe_modi_reg = new Date();
                }else{
                    fe_modi_reg = Utils.getDateFromString(values[5]);
                }
                gestorPrecuentas.setFeModiReg(fe_modi_reg);
            }
            if(Utils.evaluateString(values[6])){
                gestorPrecuentas.setClienteFicticio(Integer.parseInt(values[6]));
            }
            if(Utils.evaluateString(values[7])){
                gestorPrecuentas.setEstadoRegCurrent(values[7]);
            }
        }
        return gestorPrecuentas;
    }

    public GestorPrecuentas getGestorPrecuentasErrorFromString(String line) {
        GestorPrecuentas gestorPrecuentas = null;
        if (line.isEmpty()) {
            return null;
        }
        String[] values = line.split(",");
        if(values.length == 9){
            gestorPrecuentas = getGestorPrecuentas(values);
            if(Utils.evaluateString(values[4])){
                gestorPrecuentas.setTxError(values[4]);
            }
            if(Utils.evaluateString(values[5])){
                Date fe_inicio;
                if("SYSDATE".equalsIgnoreCase(values[5])){
                    fe_inicio = new Date();
                }else{
                    fe_inicio = Utils.getDateFromString(values[5]);
                }
                gestorPrecuentas.setFeInicio(fe_inicio);
            }
            if(Utils.evaluateString(values[6])){
                Date fe_modi_reg;
                if("SYSDATE".equalsIgnoreCase(values[6])){
                    fe_modi_reg = new Date();
                }else{
                    fe_modi_reg = Utils.getDateFromString(values[6]);
                }
                gestorPrecuentas.setFeModiReg(fe_modi_reg);
            }
            if(Utils.evaluateString(values[7])){
                gestorPrecuentas.setClienteFicticio(Integer.parseInt(values[7]));
            }
            if(Utils.evaluateString(values[8])){
                gestorPrecuentas.setEstadoRegCurrent(values[8]);
            }
        }
        return gestorPrecuentas;
    }

    private GestorPrecuentas getGestorPrecuentas(String[] values) {
        GestorPrecuentas gestorPrecuentas;
        gestorPrecuentas = new GestorPrecuentas();
        if(Utils.evaluateString(values[0])){
            gestorPrecuentas.setEstadoRegAfter(values[0]);
        }
        if(Utils.evaluateString(values[1])){
            gestorPrecuentas.setIdUsuario(values[1]);
        }
        if(Utils.evaluateString(values[2])){
            gestorPrecuentas.setIdCampSalesforce(values[2]);
        }
        if(Utils.evaluateString(values[3])){
            gestorPrecuentas.setTxObservacion(values[3]);
        }
        return gestorPrecuentas;
    }
}
