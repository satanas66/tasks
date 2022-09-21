package potencial.domain.business;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS relacionados con los datos de contacto
 */
@Data
public class DatosContacto {

    private String nc_nif;

    private Integer co_cliente;

    private String cc_nom_empre;

    private String no_comer;

    private String tx_actvad;

    private String tx_sector;

    private String de_prov;

    private String tx_loca_apa;

    private String direccion;

    private String co_post_cto;

    private String tx_ccaa;

    private String telefono1;

    private String telefono2;

    private String telefono3;

    private String telefono4;

    private String telefono5;

    private String di_url;

    private Integer co_actvad_pral;

    public List<Object> getKpisDatosContacto() {
        List<Object> result = new ArrayList<>();
        result.add(getNc_nif());
        result.add(getCo_cliente());
        result.add(getCc_nom_empre());
        result.add(getNo_comer());
        result.add(getTx_actvad());
        result.add(getTx_sector());
        result.add(getDe_prov());
        result.add(getTx_loca_apa());
        result.add(getDireccion());
        result.add(getCo_post_cto());
        result.add(getTx_ccaa());
        result.add(getTelefono1());
        result.add(getTelefono2());
        result.add(getTelefono3());
        result.add(getTelefono4());
        result.add(getTelefono5());
        result.add(getDi_url());

        return result;
    }

}
