package mercado.candidato.jpa.gestcli_sisora;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Tcl_ClieProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Tcl_ClieProjection tcl_clieProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getGestcliSisoraEntityManager();
        tcl_clieProjection = new Tcl_ClieProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

//    @Test
//    public void getAllClientCodesSegmented() {
//        List<Integer> result = tcl_clieProjection.getClientCodesSegmented("0001", "0160");
//        assertThat(result).isNotNull();
//
//        result = tcl_clieProjection.getClientCodesSegmented("0004", "0160");
//        assertThat(result).isNotNull();
//
//        result = tcl_clieProjection.getClientCodesSegmented("0006", "0160");
//        assertThat(result).isNotNull();
//
//        result = tcl_clieProjection.getClientCodesSegmented("0007", "0160");
//        assertThat(result).isNotNull();
//
//        result = tcl_clieProjection.getClientCodesSegmented("0008", "0160");
//        assertThat(result).isNotNull();
//
//        result = tcl_clieProjection.getClientCodesSegmented("0009", "0160");
//        assertThat(result).isNotNull();
//    }

    @Test
    public void getRegistrationByScrappingProcess() {
        List<Object[]> result = tcl_clieProjection.getRegistrationByScrappingProcess();
        assertThat(result).isNotNull();
        assertThat(result.size()).isNotZero().isEqualTo(142995);

    }

    @Test
    public void getModificationByScrapping() {
        List<Object[]> result = tcl_clieProjection.getModificationByScrappingProcess();
        assertThat(result).isNotNull();
        assertThat(result.size()).isNotZero().isEqualTo(185263);
    }
}