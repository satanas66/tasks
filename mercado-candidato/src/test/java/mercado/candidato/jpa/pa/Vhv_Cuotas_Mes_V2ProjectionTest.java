package mercado.candidato.jpa.pa;

import automation.factory.Utils;
import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Vhv_Cuotas_Mes_V2ProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Vhv_Cuotas_Mes_V2Projection vhv_cuotas_mes_v2Projection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPAEntityManager();
        vhv_cuotas_mes_v2Projection = new Vhv_Cuotas_Mes_V2Projection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }


    @Test
    public void findIdProductByClientCode() {
        String result = vhv_cuotas_mes_v2Projection.findIdProductByClientCode("271252926");
        assertThat(result).isNotNull().isNotEmpty();
    }

    @Test
    public void findIdMesAndImporteByClientCode() {
        Object[] result = vhv_cuotas_mes_v2Projection.findImporteMaxAndIdMesByClientCode(265932285);
        assertThat(result).isNotNull();
        assertThat(result.length).isNotZero().isEqualTo(2);
    }

    @Test
    void findIdMesByClienCode() {
        Integer result = vhv_cuotas_mes_v2Projection.findIdMesToADWByClienCode(165765967);
        assertThat(result).isNotNull().isEqualTo(201711);
    }

    @Test
    public void findIdProductsByClientCodeAndMonths() {
        List<String> result = vhv_cuotas_mes_v2Projection.findCp3F_ByClientCode(265535443);
        assertThat(result).isNotNull();
        assertThat(result.size()).isNotZero();
    }

    @Test
    public void findAllCp3ByClientCode() {
        List<Integer> months = Utils.getCurrentMonthsAndPreviousMonth();
        List<String> result = vhv_cuotas_mes_v2Projection.findCp3ActualAndPreviousMonthByClientCode(265535443, months.get(0), months.get(1));
        assertThat(result).isNotNull();
        assertThat(result.size()).isNotZero();
    }

    @Test
    public void findProductsContractedInFiscalPastYear() {
        Double result = vhv_cuotas_mes_v2Projection.sumAmountsToCP3_F_BEEContractedInFiscalPastYear(40337);
        assertThat(result).isNotNull();
        assertThat(result).isNotZero().isEqualTo(429);

        result = vhv_cuotas_mes_v2Projection.sumAmountsToCP3_F_BEEContractedInFiscalPastYear(1982644);
        assertThat(result).isNotNull();
        assertThat(result).isNotZero().isEqualTo(839.7);
    }

    @Test
    public void findAllAliveClientCodes() {
        List<String> result = vhv_cuotas_mes_v2Projection.findAllAliveClientCodes();
        assertThat(result).isNotNull();
        assertThat(result.size()).isNotZero();
    }

    @Test
    public void findIdsProductServiceAndOportunidadByClientCode() {
        Object[] result = vhv_cuotas_mes_v2Projection.findIdsProductServiceAndOportunidadByClientCode("271308132");
        assertThat(result).isNotNull();
    }

    @Test
    public void sumAmountsCurrentsContractedProducts() {
        Double result = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsContractedProducts(152154761);
        assertThat(result).isEqualTo(105.75);
    }

    @Test
    public void sumAmountsCurrentsContractedCP3_F_WEB() {
        Double result = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsContractedCP3(152154761, "F_WEB");
        assertThat(result).isNull();
        result = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsContractedCP3(5564562, "F_WEB");
        assertThat(result).isEqualTo(32);

        result = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsContractedCP3(5564562, "F_BEE");
        assertThat(result).isEqualTo(88.32);
        result = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsContractedCP3(152154761, "F_BEE");
        assertThat(result).isEqualTo(105.75);

    }

    @Test
    public void sumAmountsCurrentsIdObjetoIsWeb() {
        Double result = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsIdObjetoIsWeb(267624260, 1);
        assertThat(result).isNotZero().isEqualTo(32.0);
        result = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsIdObjetoIsWeb(267624260, 2);
        assertThat(result).isNull();

        result = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsIdObjetoIsWeb(152154761, 2);
        assertThat(result).isNull();

    }
}