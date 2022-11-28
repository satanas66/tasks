package mercado.potencial;

import automation.factory.Logger;
import automation.factory.xlsx.Excel;

public class FileMix {

    private static Logger LOG = Logger.getLogger(FileMix.class);


    public static void main(String[] arg) {
//        combineFiles();
        combineSheets();
    }

    public static void combineFiles() {
        LOG.info("Inicio de la combinación de los ficheros MERCADO_POTENCIAL_X...");
        String path = "C:/tasks/mercado-potencial-mongo/src/main/resources/A/";
        Excel.mergeExcelDocuments(path, "MERCADO_POTENCIAL_", "MERCADO_POTENCIAL.xlsx");
        LOG.info("Final de la combinación de los ficheros MERCADO_POTENCIAL_X.");
    }

    public static void combineSheets() {
        LOG.info("Inicio de la combinación de las hojas del fichero MERCADO_POTENCIAL_X...");
        String path = "C:/tasks/mercado-potencial-mongo/src/main/resources/A/";
        Excel.combineSheets(path, "MERCADO_POTENCIAL.xlsx", "MERCADO_POTENCIAL_DEST.xlsx");
        LOG.info("Final de la combinación de las hojas del fichero MERCADO_POTENCIAL_X.");

    }
}
