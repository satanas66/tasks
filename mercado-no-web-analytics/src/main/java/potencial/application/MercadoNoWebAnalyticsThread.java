package potencial.application;

import automation.factory.Logger;
import automation.factory.xlsx.Excel;
import potencial.domain.business.DatosContacto;
import potencial.domain.mapper.DatosContactoMapper;
import potencial.jpa.phw_vac.F_Datos_ContactoProjection;
import potencial.jpa.sisora.Tsi_ActvadProjection;
import potencial.jpa.sisora.Tsi_F_WebProjection;
import potencial.jpa.sisora.Tsi_SectorProjection;

import java.util.*;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class MercadoNoWebAnalyticsThread extends Thread {

    private static Logger LOG = Logger.getLogger(MercadoNoWebAnalyticsThread.class);

    private F_Datos_ContactoProjection f_datos_contactoProjection;

    private Tsi_ActvadProjection tsi_actvadProjection;

    private Tsi_F_WebProjection tsi_f_webProjection;

    private Tsi_SectorProjection tsi_sectorProjection;

    private String path;

    private String nameFile;

    private List<Integer> clientCodes;

    public MercadoNoWebAnalyticsThread(String nombreHilo) {
        super(nombreHilo);
    }

    public void instancePatameters(String path, String nameFile) {
        this.path = path;
        this.nameFile = nameFile;
    }

    public void instanceListClientCodes(List<Integer> clientCodes) {
        this.clientCodes = clientCodes;
    }

    public void instanceOracleProjection(F_Datos_ContactoProjection f_datos_contactoProjection,
                                         Tsi_ActvadProjection tsi_actvadProjection,
                                         Tsi_F_WebProjection tsi_f_webProjection,
                                         Tsi_SectorProjection tsi_sectorProjection) {

        this.f_datos_contactoProjection = f_datos_contactoProjection;

        this.tsi_actvadProjection = tsi_actvadProjection;
        this.tsi_f_webProjection = tsi_f_webProjection;
        this.tsi_sectorProjection = tsi_sectorProjection;
    }

    @Override
    public void run() {
        constructProjection();
    }

    private void constructProjection() {
        LOG.info("Ejecutando el " + this.getName());
        List<List<Object>> total = new ArrayList<>();
        int i = 0;
        for (Integer clientCode : clientCodes) {
            try {

                Object[] datosContactoValues = f_datos_contactoProjection.getProjectionFromF_Datos_ContactoToMuestraMadreByClientCode(clientCode);
                if (datosContactoValues != null) {
                    /********** Datos Contacto **********/
                    DatosContactoMapper datosContactoMapper = new DatosContactoMapper();
                    DatosContacto datosContacto = datosContactoMapper.getDatosContactoFromProjection(new DatosContacto(), datosContactoValues);
                    datosContacto.setCo_cliente(clientCode);
                    String di_url = tsi_f_webProjection.findProjectionEWeb(clientCode);
                    datosContacto.setDi_url(di_url);
                    String co_sector = tsi_actvadProjection.findSectorCodeFromTsiActvad(datosContacto.getCo_actvad_pral());
                    String tx_sector = tsi_sectorProjection.getSectorTextBySectorCode(co_sector);
                    datosContacto.setTx_sector(tx_sector);

                    List<Object> kpisValues = datosContacto.getKpisDatosContacto();
                    total.add(kpisValues);

                    i++;
                    if (i == 1000) {
                        LOG.info("Escribiendo " + i + " registros...");
                        Excel.writeKPIsAllValues(path, nameFile, total, getKPIsMuestraMadre());
                        LOG.info("Registros escritos correctamente");
                        total = new ArrayList<>();
                        i = 0;
                    }
                }

            } catch (Exception e) {
                LOG.error("Ha ocurrido un error durante la consulta del cliente: " + clientCode);
            }
        }
        if (total.size() > 0) {
            LOG.info("Escribiendo últimos " + i + " registros...");
            Excel.writeKPIsAllValues(path, nameFile, total, getKPIsMuestraMadre());
            LOG.info("Registros escritos correctamente");
        }
    }

    /**
     * Método que genera el listado completo de los KPIs de la muestra madre versión 1
     *
     * @return
     */
    private List<String> getKPIsMuestraMadre() {
        return Arrays.asList(
                "NIF", "CO_CLIENTE", "CC_NOM_EMPRE", "NO_COMER", "TX_ACTVAD", "TX_SECTOR",
                "DE_PROV", "TX_LOCA_APA", "DIRECCION", "CO_POST_CTO", "TX_CCAA",
                "TELEFONO1", "TELEFONO2", "TELEFONO3", "TELEFONO4", "TELEFONO5", "DI_URL");

    }
}
