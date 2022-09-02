package mercado.candidato.domain.business;

import automation.factory.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS relacionados con el recomendador de paquetes
 */
public class Recomendador {

    private String paquete_recomendado;

    public String getPaquete_recomendado() {
        return paquete_recomendado;
    }

    public void setPaquete_recomendado(String paquete_recomendado) {
        this.paquete_recomendado = (Utils.evaluateString(paquete_recomendado))?paquete_recomendado:"";
    }

    public List<Object> getKpisRecomendador(){
        List<Object> result = new ArrayList<>();
        result.add(getPaquete_recomendado());
        return result;
    }
}
