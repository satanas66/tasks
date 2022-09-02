package indices.domain.business;

import java.util.ArrayList;
import java.util.List;

public class IndiceVisibilidad {

    private Integer co_cliente;

    private String co_sector;

    private Integer co_actividad;

    private String indiceVisibilidad;

    private Integer posicionGMB;

    private Integer keywordTop10;

    private Float mediaPosiciónGMB;

    private Float mediaKeywordTop10;

    public Integer getCo_cliente() {
        return co_cliente;
    }

    public void setCo_cliente(Integer co_cliente) {
        this.co_cliente = co_cliente;
    }

    public String getCo_sector() {
        return co_sector;
    }

    public void setCo_sector(String co_sector) {
        this.co_sector = co_sector;
    }

    public Integer getCo_actividad() {
        return co_actividad;
    }

    public void setCo_actividad(Integer co_actividad) {
        this.co_actividad = co_actividad;
    }

    public String getIndiceVisibilidad() {
        return evaluateVisibility(this.posicionGMB, this.keywordTop10);
    }

    public Integer getPosicionGMB() {
        return posicionGMB;
    }

    public void setPosicionGMB(Integer posicionGMB) {
        this.posicionGMB = posicionGMB;
    }

    public Integer getKeywordTop10() {
        return keywordTop10;
    }

    public void setKeywordTop10(Integer keywordTop10) {
        this.keywordTop10 = keywordTop10;
    }

    public Float getMediaPosiciónGMB() {
        return mediaPosiciónGMB;
    }

    public void setMediaPosiciónGMB(Float mediaPosiciónGMB) {
        this.mediaPosiciónGMB = mediaPosiciónGMB;
    }

    public Float getMediaKeywordTop10() {
        return mediaKeywordTop10;
    }

    public void setMediaKeywordTop10(Float mediaKeywordTop10) {
        this.mediaKeywordTop10 = mediaKeywordTop10;
    }

    /**
     * Método que evalua el índice de visibilidad para este objeto
     * @param kpiSeg303
     * @param kpiSeg23
     * @return
     */
    private String evaluateVisibility(Integer kpiSeg303, Integer kpiSeg23){

        if(kpiSeg303 != null && kpiSeg303 != 999999999){
            if(kpiSeg303 > 15){
                return "Invisible";
            }
            if(kpiSeg303 >= 10 && kpiSeg303 <= 15){
                return "Muy bajo";
            }
            if(kpiSeg303 >= 5 && kpiSeg303 <= 9){
                return "Bajo";
            }
            if(kpiSeg303 >= 3 && kpiSeg303 <= 4){
                return "Mejorable";
            }
            if(kpiSeg303 >= 1 && kpiSeg303 <= 2){
                return "Bueno";
            }
        }
        if(kpiSeg23 != null && kpiSeg23 != 999999999){
            if(kpiSeg23 < 2){
                return "Invisible";
            }
            if(kpiSeg23 >= 2 && kpiSeg23 <=3){
                return "Muy bajo";
            }
            if(kpiSeg23 >= 4 && kpiSeg23 <= 6){
                return "Bajo";
            }
            if(kpiSeg23 >= 5 && kpiSeg23 <= 8){
                return "Mejorable";
            }
            if(kpiSeg23 > 8){
                return "Bueno";
            }
        }
        return null;
    }

    public List<Object> getKpisIndicesVisibilidad(){
        List<Object> result = new ArrayList<>();
        result.add(getCo_cliente());
        result.add(getCo_sector());
        result.add(getCo_actividad());
        result.add(getKeywordTop10());
        result.add(getPosicionGMB());
        result.add(getIndiceVisibilidad());
        result.add(getMediaKeywordTop10());
        result.add(getMediaPosiciónGMB());
        return result;
    }

}