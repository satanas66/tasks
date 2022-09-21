package indices.domain.mapper;

import indices.domain.business.PresenciaDigital;

public class PresenciaDigitalMapper {

    public PresenciaDigital setPresenciaDigitalFromProjection(PresenciaDigital presenciaDigital, Object[] projection) {
        if (projection == null) {
            return presenciaDigital;
        }

        Integer erroresWeb = calculateSeriousErrorsWeb(projection);
        presenciaDigital.setSeriousErrorsWeb(erroresWeb);

//        if(projection[11] != null){
//            presenciaDigital.setGoogleBusinessProfile((boolean) projection[11]);
//        }
//        if(projection[12] != null){
//            presenciaDigital.setReviewsGBP((Integer)projection[12]);
//        }

        Integer posicionGMB = null;
        Integer keywordTop10 = null;

        if (projection[13] != null) {
            posicionGMB = (Integer) projection[13];
        }
        if (projection[14] != null) {
            keywordTop10 = (Integer) projection[14];
        }
        String probability = evaluaProbabilidad(posicionGMB, keywordTop10);
        presenciaDigital.setProbabilityAppearFirstPage(probability);

        return presenciaDigital;
    }

    /**
     * Método que calcula el número de errores en la web
     *
     * @param projection, los errores son recogidos desde las posiciones 0 a la 9
     * @return número de errores
     */
    private Integer calculateSeriousErrorsWeb(Object[] projection) {
        Integer result = 0;
        if (projection != null && projection.length > 0) {
            int count = 0;
            for (int i = 0; i < projection.length - 2; i++) {
                if (projection[i] != null && (boolean) projection[i]) {
                    count++;
                }
            }
            result = count;
        }
        return result;
    }


    /**
     * Método que calcula la probabilidad en % que tiene una web de aparecer en una primera página de búsqueda
     *
     * @param posicionGMB  (KPISEG303)
     * @param keywordTop10 (KPISEG23)
     * @return % de probabilidad
     */
    private String evaluaProbabilidad(Integer posicionGMB, Integer keywordTop10) {
        String result = "SIN INFORMACION";
        if (posicionGMB == null && keywordTop10 != null && keywordTop10 < 2) {
            return "5%";
        }
        if (posicionGMB != null && keywordTop10 != null) {
            if (posicionGMB != null && keywordTop10 != null) {
                if (posicionGMB == 1 && keywordTop10 > 8) {
                    return "80%";
                }
                if (posicionGMB == 2 && keywordTop10 >= 7 && keywordTop10 <= 8) {
                    return "65%";
                }
                if (posicionGMB >= 3 && posicionGMB <= 4 && keywordTop10 >= 4 && keywordTop10 <= 6) {
                    return "45%";
                }
                if (posicionGMB >= 5 && posicionGMB <= 9 && keywordTop10 >= 2 && keywordTop10 <= 3) {
                    return "25%";
                }
                if (posicionGMB >= 10 && keywordTop10 < 2) {
                    return "10%";
                }

                if (posicionGMB == 1) {
                    if (keywordTop10 >= 7 && keywordTop10 <= 8) {
                        return "65%";
                    }
                    if (keywordTop10 >= 4 && keywordTop10 <= 6) {
                        return "45%";
                    }
                    if (keywordTop10 >= 2 && keywordTop10 <= 3) {
                        return "25%";
                    }
                    if (keywordTop10 < 2) {
                        return "10%";
                    }
                }

                if (posicionGMB == 2) {
                    if (keywordTop10 >= 4 && keywordTop10 <= 6) {
                        return "45%";
                    }
                    if (keywordTop10 >= 2 && keywordTop10 <= 3) {
                        return "25%";
                    }
                    if (keywordTop10 < 2) {
                        return "10%";
                    }
                }

                if (posicionGMB >= 3 && posicionGMB <= 4) {
                    if (keywordTop10 >= 2 && keywordTop10 <= 3) {
                        return "25%";
                    }
                    if (keywordTop10 < 2) {
                        return "10%";
                    }
                }

                if (posicionGMB >= 5 && posicionGMB <= 9) {
                    if (keywordTop10 < 2) {
                        return "10%";
                    }
                }
            }
        }
        return result;
    }

    private String getDigitalPresence(boolean claimBusiness, Integer reviews, String probabilidad, Integer errores) {
        String result = "SIN INFORMACION";
        if (claimBusiness) {
            return "15%";
        }
        if (probabilidad.equals("5%") && (reviews == null || reviews == 0) && errores == null) {
            return "10%";
        }
        if (probabilidad.equals("10%") && reviews >= 1 && reviews < 20 && errores > 6) {
            return "15%";
        }
        if (probabilidad.equals("25%") && reviews >= 20 && reviews <= 40 && errores >= 4 && errores <= 5) {
            return "30%";
        }
        if (probabilidad.equals("45%") && reviews >= 41 && reviews <= 80 && errores == 3) {
            return "45%";
        }
        if (probabilidad.equals("65%") && reviews >= 81 && reviews <= 90 && errores == 2) {
            return "60%";
        }
        if (probabilidad.equals("80%") && reviews > 90 && errores > 0 && errores <= 1) {
            return "80%";
        }

        if (probabilidad.equals("80%")) {
            if (reviews >= 81 && reviews <= 90) {
                if (errores == 2) {
                    return "60%";
                }
                if (errores == 3) {
                    return "45%";
                }
                if (errores >= 4 && errores <= 5) {
                    return "30%";
                }
                if (errores >= 6) {
                    return "15%";
                }
                if (errores == null) {
                    return "10%";
                }
            }













        }
        if (probabilidad.equals("65%")) {
            if (reviews >= 41 && reviews <= 80) {
                if (errores == 3) {
                    return "45%";
                }
                if (errores >= 4 && errores <= 5) {
                    return "30%";
                }
                if (errores >= 6) {
                    return "15%";
                }
                if (errores == null) {
                    return "10%";
                }
            }
        }
        if (probabilidad.equals("45%")) {
            if (reviews >= 20 && reviews <= 40) {
                if (errores >= 4 && errores <= 5) {
                    return "30%";
                }
                if (errores >= 6) {
                    return "15%";
                }
                if (errores == null) {
                    return "10%";
                }
            }
        }
        if (probabilidad.equals("25%")) {
            if (reviews >= 1 && reviews < 20) {
                if (errores >= 6) {
                    return "15%";
                }
                if (errores == null) {
                    return "10%";
                }
            }
        }
        if (probabilidad.equals("10%") && (reviews == null || reviews == 0) && errores == null) {
            return "10%";
        }

        if(probabilidad.equals("SIN INFORMACION")){
            if(reviews > 90 && errores >=0 || errores <= 1){
                return "80%";
            }
            if(reviews >= 81 && reviews <= 90 && errores == 2){
                return "60%";
            }
            if(reviews >= 41 && reviews <= 80 && errores == 3){
                return "45%";
            }
            if(reviews >= 20 && reviews <= 40 && errores >= 4 && errores <= 5){
                return "30%";
            }
            if(reviews >= 1 && reviews <= 20 && errores >= 6){
                return "15%";
            }
            if(reviews == null && reviews == 0 && errores == null){
                return "10%";
            }
        }























        return result;
    }

}
