package urls.marketgoo.application;

import automation.factory.Logger;

public class ExecuteURLsMarketgoo {

    private static Logger LOG = Logger.getLogger(ExecuteURLsMarketgoo.class);

    public static void main(String[] arg){
        LOG.info("Proceso para la busqueda de URLS lanzado...");
        URLsMarketgoo urLsMarketgoo = new URLsMarketgoo();
        urLsMarketgoo.processGetUrls();
    }
}
