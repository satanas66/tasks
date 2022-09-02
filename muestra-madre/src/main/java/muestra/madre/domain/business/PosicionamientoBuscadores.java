package muestra.madre.domain.business;

import automation.factory.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS relacionados con el posicionamiento en buscadores
 */
public class PosicionamientoBuscadores {

    private Integer num_kw_en_top_10;

    private String keyword_1;

    private Integer position_kw_1;

    private String keyword_2;

    private Integer position_kw_2;

    private String keyword_3;

    private Integer position_kw_3;

    private String keyword_4;

    private Integer position_kw_4;

    private String keyword_5;

    private Integer position_kw_5;

    private String keyword_6;

    private Integer position_kw_6;

    private String keyword_7;

    private Integer position_kw_7;

    private String keyword_8;

    private Integer position_kw_8;

    private String keyword_9;

    private Integer position_kw_9;

    private String keyword_10;

    private Integer position_kw_10;

    private Integer ranking_number;

    public Integer getNum_kw_en_top_10() {
        return num_kw_en_top_10;
    }

    public void setNum_kw_en_top_10(Integer num_kw_en_top_10) {
        this.num_kw_en_top_10 = num_kw_en_top_10;
    }

    public String getKeyword_1() {
        return keyword_1;
    }

    public void setKeyword_1(String keyword_1) {
        this.keyword_1 = (Utils.evaluateString(keyword_1))?keyword_1:"";
    }

    public Integer getPosition_kw_1() {
        return position_kw_1;
    }

    public void setPosition_kw_1(Integer position_kw_1) {
        this.position_kw_1 = position_kw_1;
    }

    public String getKeyword_2() {
        return keyword_2;
    }

    public void setKeyword_2(String keyword_2) {
        this.keyword_2 = (Utils.evaluateString(keyword_2))?keyword_2:"";
    }

    public Integer getPosition_kw_2() {
        return position_kw_2;
    }

    public void setPosition_kw_2(Integer position_kw_2) {
        this.position_kw_2 = position_kw_2;
    }

    public String getKeyword_3() {
        return keyword_3;
    }

    public void setKeyword_3(String keyword_3) {
        this.keyword_3 = (Utils.evaluateString(keyword_3))?keyword_3:"";
    }

    public Integer getPosition_kw_3() {
        return position_kw_3;
    }

    public void setPosition_kw_3(Integer position_kw_3) {
        this.position_kw_3 = position_kw_3;
    }

    public String getKeyword_4() {
        return keyword_4;
    }

    public void setKeyword_4(String keyword_4) {
        this.keyword_4 = (Utils.evaluateString(keyword_4))?keyword_4:"";
    }

    public Integer getPosition_kw_4() {
        return position_kw_4;
    }

    public void setPosition_kw_4(Integer position_kw_4) {
        this.position_kw_4 = position_kw_4;
    }

    public String getKeyword_5() {
        return keyword_5;
    }

    public void setKeyword_5(String keyword_5) {
        this.keyword_5 = (Utils.evaluateString(keyword_5))?keyword_5:"";
    }

    public Integer getPosition_kw_5() {
        return position_kw_5;
    }

    public void setPosition_kw_5(Integer position_kw_5) {
        this.position_kw_5 = position_kw_5;
    }

    public String getKeyword_6() {
        return keyword_6;
    }

    public void setKeyword_6(String keyword_6) {
        this.keyword_6 = (Utils.evaluateString(keyword_6))?keyword_6:"";
    }

    public Integer getPosition_kw_6() {
        return position_kw_6;
    }

    public void setPosition_kw_6(Integer position_kw_6) {
        this.position_kw_6 = position_kw_6;
    }

    public String getKeyword_7() {
        return keyword_7;
    }

    public void setKeyword_7(String keyword_7) {
        this.keyword_7 = (Utils.evaluateString(keyword_7))?keyword_7:"";
    }

    public Integer getPosition_kw_7() {
        return position_kw_7;
    }

    public void setPosition_kw_7(Integer position_kw_7) {
        this.position_kw_7 = position_kw_7;
    }

    public String getKeyword_8() {
        return keyword_8;
    }

    public void setKeyword_8(String keyword_8) {
        this.keyword_8 = (Utils.evaluateString(keyword_8))?keyword_8:"";
    }

    public Integer getPosition_kw_8() {
        return position_kw_8;
    }

    public void setPosition_kw_8(Integer position_kw_8) {
        this.position_kw_8 = position_kw_8;
    }

    public String getKeyword_9() {
        return keyword_9;
    }

    public void setKeyword_9(String keyword_9) {
        this.keyword_9 = (Utils.evaluateString(keyword_9))?keyword_9:"";
    }

    public Integer getPosition_kw_9() {
        return position_kw_9;
    }

    public void setPosition_kw_9(Integer position_kw_9) {
        this.position_kw_9 = position_kw_9;
    }

    public String getKeyword_10() {
        return keyword_10;
    }

    public void setKeyword_10(String keyword_10) {
        this.keyword_10 = (Utils.evaluateString(keyword_10))?keyword_10:"";
    }

    public Integer getPosition_kw_10() {
        return position_kw_10;
    }

    public void setPosition_kw_10(Integer position_kw_10) {
        this.position_kw_10 = position_kw_10;
    }

    public Integer getRanking_number() {
        return ranking_number;
    }

    public void setRanking_number(Integer ranking_number) {
        this.ranking_number = ranking_number;
    }

    public List<Object> getKpisPosicionamientoBuscadores(){
        List<Object> result = new ArrayList<>();
        result.add(getNum_kw_en_top_10());
        result.add(getKeyword_1());
        result.add(getPosition_kw_1());
        result.add(getKeyword_2());
        result.add(getPosition_kw_2());
        result.add(getKeyword_3());
        result.add(getPosition_kw_3());
        result.add(getKeyword_4());
        result.add(getPosition_kw_4());
        result.add(getKeyword_5());
        result.add(getPosition_kw_5());
        result.add(getKeyword_6());
        result.add(getPosition_kw_6());
        result.add(getKeyword_7());
        result.add(getPosition_kw_7());
        result.add(getKeyword_8());
        result.add(getPosition_kw_8());
        result.add(getKeyword_9());
        result.add(getPosition_kw_9());
        result.add(getKeyword_10());
        result.add(getPosition_kw_10());
        result.add(getRanking_number());
        return result;
    }
}
