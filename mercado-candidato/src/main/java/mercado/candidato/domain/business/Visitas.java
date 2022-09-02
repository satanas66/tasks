package mercado.candidato.domain.business;

public class Visitas {

    private Integer lastVisitBusinessArea;

    private Integer numberVisitsLastSixMonths;

    public Integer getLastVisitBusinessArea() {
        return lastVisitBusinessArea;
    }

    public void setLastVisitBusinessArea(Integer lastVisitBUsinessArea) {
        this.lastVisitBusinessArea = lastVisitBUsinessArea;
        if(lastVisitBUsinessArea.compareTo(this.lastVisitBusinessArea) > 0){
            this.lastVisitBusinessArea = lastVisitBUsinessArea;
        }
    }

    public Integer getNumberVisitsLastSixMonths() {
        return numberVisitsLastSixMonths;
    }

    public void setNumberVisitsLastSixMonths(Integer numberVisitsLastSixMonths) {
        if(this.numberVisitsLastSixMonths == null){
            this.numberVisitsLastSixMonths = 0;
        }
        if(this.numberVisitsLastSixMonths != null){
            this.numberVisitsLastSixMonths = this.numberVisitsLastSixMonths+numberVisitsLastSixMonths;
        }
    }
}
