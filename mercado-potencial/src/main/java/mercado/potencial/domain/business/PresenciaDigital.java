package mercado.potencial.domain.business;

import java.util.ArrayList;
import java.util.List;

public class PresenciaDigital {

    private Integer kpiSeg400;//índice de presencia

    private Boolean kpiSeg32;//claim business

    private Integer kpiSeg122;//reviews

    private Integer kpiSeg401;//probabilidad 1º pagina

    private Integer kpiSeg303;//ranking number

    private Integer kpiSeg23;//keyword top 10

    private Integer kpiSeg402;//errores graves

    private Boolean kpiSeg05;

    private Boolean kpiSeg07;

    private Boolean kpiSeg08;

    private Boolean kpiSeg09;

    private Boolean kpiSeg10;

    private Boolean kpiSeg11;

    private Boolean kpiSeg12;

    private Boolean kpiSeg13;

    private Boolean kpiSeg15;

    private Boolean kpiSeg16;

    private Double kpiSeg404;//Indice competencia


    public Integer getKpiSeg400() {
        return kpiSeg400;
    }

    public void setKpiSeg400(Integer kpiSeg400) {
        this.kpiSeg400 = kpiSeg400;
    }

    public Boolean getKpiSeg32() {
        return kpiSeg32;
    }

    public void setKpiSeg32(Boolean kpiSeg32) {
        this.kpiSeg32 = kpiSeg32;
    }

    public Integer getKpiSeg122() {
        return kpiSeg122;
    }

    public void setKpiSeg122(Integer kpiSeg122) {
        this.kpiSeg122 = kpiSeg122;
    }

    public Integer getKpiSeg401() {
        return kpiSeg401;
    }

    public void setKpiSeg401(Integer kpiSeg401) {
        this.kpiSeg401 = kpiSeg401;
    }

    public Integer getKpiSeg303() {
        return kpiSeg303;
    }

    public void setKpiSeg303(Integer kpiSeg303) {
        this.kpiSeg303 = kpiSeg303;
    }

    public Integer getKpiSeg23() {
        return kpiSeg23;
    }

    public void setKpiSeg23(Integer kpiSeg23) {
        this.kpiSeg23 = kpiSeg23;
    }

    public Integer getKpiSeg402() {
        return kpiSeg402;
    }

    public void setKpiSeg402(Integer kpiSeg402) {
        this.kpiSeg402 = kpiSeg402;
    }

    public Boolean getKpiSeg05() {
        return kpiSeg05;
    }

    public void setKpiSeg05(Boolean kpiSeg05) {
        this.kpiSeg05 = kpiSeg05;
    }

    public Boolean getKpiSeg07() {
        return kpiSeg07;
    }

    public void setKpiSeg07(Boolean kpiSeg07) {
        this.kpiSeg07 = kpiSeg07;
    }

    public Boolean getKpiSeg08() {
        return kpiSeg08;
    }

    public void setKpiSeg08(Boolean kpiSeg08) {
        this.kpiSeg08 = kpiSeg08;
    }

    public Boolean getKpiSeg09() {
        return kpiSeg09;
    }

    public void setKpiSeg09(Boolean kpiSeg09) {
        this.kpiSeg09 = kpiSeg09;
    }

    public Boolean getKpiSeg10() {
        return kpiSeg10;
    }

    public void setKpiSeg10(Boolean kpiSeg10) {
        this.kpiSeg10 = kpiSeg10;
    }

    public Boolean getKpiSeg11() {
        return kpiSeg11;
    }

    public void setKpiSeg11(Boolean kpiSeg11) {
        this.kpiSeg11 = kpiSeg11;
    }

    public Boolean getKpiSeg12() {
        return kpiSeg12;
    }

    public void setKpiSeg12(Boolean kpiSeg12) {
        this.kpiSeg12 = kpiSeg12;
    }

    public Boolean getKpiSeg13() {
        return kpiSeg13;
    }

    public void setKpiSeg13(Boolean kpiSeg13) {
        this.kpiSeg13 = kpiSeg13;
    }

    public Boolean getKpiSeg15() {
        return kpiSeg15;
    }

    public void setKpiSeg15(Boolean kpiSeg15) {
        this.kpiSeg15 = kpiSeg15;
    }

    public Boolean getKpiSeg16() {
        return kpiSeg16;
    }

    public void setKpiSeg16(Boolean kpiSeg16) {
        this.kpiSeg16 = kpiSeg16;
    }

    public Double getKpiSeg404() {
        return kpiSeg404;
    }

    public void setKpiSeg404(Double kpiSeg404) {
        this.kpiSeg404 = kpiSeg404;
    }

    public List<Object> getKpisPresenciaDigital(){
        List<Object> result = new ArrayList<>();
        result.add(getKpiSeg400());
        result.add(getKpiSeg32());
        result.add(getKpiSeg122());
        result.add(getKpiSeg401());
        result.add(getKpiSeg303());
        result.add(getKpiSeg23());
        result.add(getKpiSeg402());
        result.add(getKpiSeg05());
        result.add(getKpiSeg07());
        result.add(getKpiSeg08());
        result.add(getKpiSeg09());
        result.add(getKpiSeg10());
        result.add(getKpiSeg11());
        result.add(getKpiSeg12());
        result.add(getKpiSeg13());
        result.add(getKpiSeg15());
        result.add(getKpiSeg16());
        result.add(getKpiSeg404());
        return result;
    }

}
