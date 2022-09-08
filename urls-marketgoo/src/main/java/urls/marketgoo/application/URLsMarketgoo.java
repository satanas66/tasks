package urls.marketgoo.application;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import automation.factory.xlsx.Excel;
import com.mongodb.DBCollection;
import jpa.ConnectionJpa;
import urls.marketgoo.jpa.pa.Thv_Actividad_Comercial_ConsProjection;
import urls.marketgoo.jpa.pa.Vhv_Cuotas_Mes_V2Projection;
import urls.marketgoo.jpa.phw_vac.F_Datos_ContactoProjection;
import urls.marketgoo.jpa.sisora.Tsi_F_WebProjection;
import mongo.ConnectionMongo;
import urls.marketgoo.mongo.scrapping.ScrappingProjection;
import urls.marketgoo.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;

import javax.persistence.EntityManager;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class URLsMarketgoo {

    private static Logger LOG = Logger.getLogger(URLsMarketgoo.class);

    private static final String PATH = "C:/tasks/urls-marketgoo/src/main/resources/";

    private static final String FILENAME_DATOSCONTACTO = "datos_contacto_client_codes.txt";

    private static final String FILENAME_POTENTIALCLIENT = "potential_client_codes.txt";

    private static final String FILENAME_CLIENTSWITHWEB = "clients_with_web.txt";

    private static final String FILENAME_CLIENTSANDWEB = "clients_and_web.txt";

    private static final String FILENAME_CLIENTSANDWEB_NOTSEND= "clients_and_web_notsend.txt";

    private static final String FILENAME_OCTOPARSECLIENTS = "octoparse_clientCodes.txt";

    private static final String FILENAME_INTERSECTION = "vivosynovivos_origenoctoparse_clients.txt";

    private static final String FILENAME_DISJUNCTION = "vivosynovivos_origencualquiera_clients.txt";

    private static final String FILENAME_PILOTO = "client_codes_piloto_marketgoo.txt";

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    private static final String FILENAME_FINAL_OCTOPARSE = "Octoparse_File_Altas" + dateFormat.format(new Date()) + "_marketgoo.csv";

    private static final String FILENAME_FINAL_ANYONE = "No_Octoparse_File_Altas" + dateFormat.format(new Date()) + "_marketgoo.csv";

    private ConnectionJpa connectionJpa;

    private EntityManager entityManagerPHWVAC;

    private EntityManager entityManagerSISORA;

    private EntityManager entityManagerPA;

    private F_Datos_ContactoProjection f_datos_contactoProjection;

    private Vhv_Cuotas_Mes_V2Projection vhv_cuotas_mes_v2Projection;

    private Thv_Actividad_Comercial_ConsProjection thvActividadComercialConsProjection;

    private Tsi_F_WebProjection tsiFWebProjection;

    private ConnectionMongo connectionMongo;

    private DBCollection webVisibilityAnalyticsDbCollection;

    private DBCollection scrappingDbCollection;

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    private ScrappingProjection scrappingProjection;

    private boolean continuaProceso = true;

    public URLsMarketgoo(){
        relationalDBStart();
        mongoStart();
        initOracleProjections();
        initMySQLProjection();
        initMongoProjections();
    }

    /**
     * Método que establece las conexiones a las Bases de datos ORACLE
     */
    public void relationalDBStart() {
        LOG.info("Abriendo conexiones a OracleDB");
        connectionJpa = new ConnectionJpa();
        entityManagerPHWVAC = connectionJpa.getPHWVACEntityManager();
        entityManagerSISORA = connectionJpa.getSisoraEntityManager();
        entityManagerPA = connectionJpa.getPAEntityManager();
        LOG.info("Conexiones establecidas a OracleDB");
    }

    /**
     * Método que establece la conexión a la Base de datos MONGO
     */
    public void mongoStart() {
        if (continuaProceso) {
            LOG.info("Abriendo conexión a MONGODB");
            connectionMongo = new ConnectionMongo("PRO");
            if (connectionMongo.getMongoClient() != null) {
                webVisibilityAnalyticsDbCollection = connectionMongo.getDBCollection("webVisibilityAnalytics");
                scrappingDbCollection = connectionMongo.getDBCollection("scrapping");
            } else {
                continuaProceso = false;
            }
            LOG.info("Conexión establecida con MONGODB");
        }
    }

    /**
     * Método que cierra las conexiones a MONGO, ORACLE y MySql
     */
    public void closureOfConnections() {
        LOG.info("Cerrando conexión a MongoDB");
        if (connectionMongo != null) {
            connectionMongo.endConnection();
        }
        LOG.info("Las conexión a MongoDB ha sido cerrada");
    }

    /**
     * Método que instancia las clases de proyección para recoger, evaluar y devolver todos los
     * valores asociados a los KPIs desde OracleDB
     */
    public void initOracleProjections() {
        LOG.info("Instanciado clases projection para realizar las consultas a OracleDB");
        f_datos_contactoProjection = new F_Datos_ContactoProjection(entityManagerPHWVAC);
        tsiFWebProjection = new Tsi_F_WebProjection(entityManagerSISORA);
    }

    /**
     * Método que instancia las clases de proyección para recoger, evaluar y devolver todos los
     * valores asociados a los KPIs desde MySQL
     */
    public void initMySQLProjection() {
        LOG.info("Instanciado clases projection para realizar las consultas a MySQL");
        vhv_cuotas_mes_v2Projection = new Vhv_Cuotas_Mes_V2Projection(entityManagerPA);
        thvActividadComercialConsProjection = new Thv_Actividad_Comercial_ConsProjection(entityManagerPA);
    }

    /**
     * Método que instancia las colecciones para recoger, evaluar y devolver todos los
     * valores asociados a los KPIs desde MongoDB
     */
    public void initMongoProjections() {
        LOG.info("Instanciado clase projection para realizar las consultas a MongoDB");
        webVisibilityAnalyticsProjection = new WebVisibilityAnalyticsProjection(webVisibilityAnalyticsDbCollection);
        scrappingProjection = new ScrappingProjection(scrappingDbCollection);
    }

    private void generateClientCodesBDC() {
        LOG.info("Inicio la búsqueda en la tabla BDC...");
        File file = new File(PATH+FILENAME_DATOSCONTACTO);
        if(!file.exists()){
            List clientCodesBDC = f_datos_contactoProjection.findAllClientCodes();
            LOG.info("Finalizo la búsqueda en la tabla BDC.");
            LOG.info("Escribiedo resultados...");
            Text.generateTxtFile(clientCodesBDC, PATH, FILENAME_DATOSCONTACTO);
            LOG.info("Los resultados han sido escritos en "+PATH+FILENAME_DATOSCONTACTO);
        }
    }

    /**
     * Método que busca los clientes potenciales (no vivos), y vivos sin web
     */
    private void generateClientCodesPotentialAndLivingWithoutWeb(){
        LOG.info("Inicio la búsqueda de los clientes potenciales y vivos sin web...");
        List<String> clientCodesBDC = Utils.generateListFromFile(PATH, FILENAME_DATOSCONTACTO);
        if(clientCodesBDC != null && clientCodesBDC.size() > 0){
            List<String> clientCodesPotentialAndLivingWithoutWeb = new ArrayList<>();
            clientCodesBDC.forEach(clientCode->{
                String potentialClient = vhv_cuotas_mes_v2Projection.findPotentialClient(clientCode);
                if(Utils.evaluateString(potentialClient)){
                    LOG.info("Cliente potencial encontrado: "+potentialClient);
                    clientCodesPotentialAndLivingWithoutWeb.add(potentialClient);
                }
            });
            LOG.info("Finalizo la búsqueda de los clientes potenciales y vivos sin web...");
            LOG.info("Escribiendo resultados...");
            Text.generateTxtFile(clientCodesPotentialAndLivingWithoutWeb, PATH, FILENAME_POTENTIALCLIENT);
            LOG.info("Los resultados han sido escritos en "+PATH+FILENAME_POTENTIALCLIENT);
        }
    }

    /**
     * Método que busca las Urls de todos los clientes
     * @return
     */
    private void getAllClientsWithWeb(){
        LOG.info("Inicio la búsqueda de los clientes con web...");
        List<String> clientCodesPotentialAndLivingWithoutWeb = Utils.generateListFromFile(PATH, FILENAME_POTENTIALCLIENT);//1408553
        List<String> allClientsWithWeb = new ArrayList<>();
        if(clientCodesPotentialAndLivingWithoutWeb != null && clientCodesPotentialAndLivingWithoutWeb.size() > 0){
            clientCodesPotentialAndLivingWithoutWeb.forEach(clientCode -> {
                String web = tsiFWebProjection.findDiUrl(clientCode);
                if (Utils.evaluateString(web)) {
                    LOG.info("Cliente con web: "+clientCode+","+web);
                    allClientsWithWeb.add(clientCode+","+web);
                }
            });
            LOG.info("Finalizo la búsqueda de los clientes con web.");
            LOG.info("Escribiedo resultados...");
            Text.generateTxtFileWithStrings(allClientsWithWeb, PATH, FILENAME_CLIENTSANDWEB);
            LOG.info("Los resultados han sido escritos en "+PATH+FILENAME_CLIENTSANDWEB);
        }
    }

    /**
     * Método que evalua si se han enviado las Urls a Marketgoo una a una
     * Si el resultado de exit es true quiere decir que no se han enviado a MArketgoo
     * @return mapa con todas las URLs no enviadas a Marketgoo
     */
    private void getURLsNoSendToMarketgoo(){
        LOG.info("Inicio la búsqueda de los clientes con webs que no han sido enviadas a Marketgoo...");
        Map<String, String> clientsAndWeb = Utils.generateMapFromFile(PATH, FILENAME_CLIENTSANDWEB);//651264
        List<String> listAllClientsWithWebNotSend = new ArrayList<>();
        clientsAndWeb.keySet().forEach(clientCode -> {
            boolean exist = webVisibilityAnalyticsProjection.evaluateURLSendToMarketgoo(clientCode);
            if (!exist) {
                LOG.info("Cliente aún no ha enviado su URL: "+clientCode);
                listAllClientsWithWebNotSend.add(clientCode+","+clientsAndWeb.get(clientCode));
            }
        });
        LOG.info("Finalizo la búsqueda de los clientes con webs que no han sido enviadas a Marketgoo.");
        LOG.info("Escribiedo resultados...");
        Text.generateTxtFileWithStrings(listAllClientsWithWebNotSend, PATH, FILENAME_CLIENTSANDWEB_NOTSEND);
        LOG.info("Los resultados han sido escritos en "+PATH+FILENAME_CLIENTSANDWEB_NOTSEND);
    }

    private void generateClientCodesOctoparse(){
        LOG.info("Inicio la búsqueda de los clientes con origen Octoparse...");
        List<String> clientCodesOctoparse = scrappingProjection.findAllClientCodesWithoutRepetition();
        LOG.info("Finalizo la búsqueda de los clientes con origen Octoparse.");
        LOG.info("Escribiedo resultados...");
        Text.generateTxtFile(clientCodesOctoparse, PATH, FILENAME_OCTOPARSECLIENTS);//226892
        LOG.info("Los resultados han sido escritos en "+PATH+FILENAME_OCTOPARSECLIENTS);
    }

    private void urlsOctoparseClients(){
        LOG.info("Inicio de la intersección de los clientes con origen Octoparse y generación de fichero...");
        List<String> clientCodesOctoparse = Utils.generateListFromFile(PATH, FILENAME_OCTOPARSECLIENTS);
        Map<String, String > clientsAndWebNotSend = Utils.generateMapFromFile(PATH, FILENAME_CLIENTSANDWEB_NOTSEND);
        List<String> intersection = Utils.getIntersection(new ArrayList<>(clientsAndWebNotSend.keySet()), clientCodesOctoparse);
        LOG.info("Finalizo de la intersección de los clientes con origen Octoparse y generación de fichero.");
        LOG.info("Escribiedo resultados...");
        Text.generateTxtFile(intersection, PATH, FILENAME_INTERSECTION);
        LOG.info("Los resultados han sido escritos en "+PATH+FILENAME_INTERSECTION);
    }

    private void urlsAnyOneClients(){
        LOG.info("Inicio de la disyunción de los clientes con origen cualquiera y generación de fichero...");
        List<String> octoparseIntersection = Utils.generateListFromFile(PATH, FILENAME_INTERSECTION);
        Map<String, String > clientsAndWebNotSend = Utils.generateMapFromFile(PATH, FILENAME_CLIENTSANDWEB_NOTSEND);
        List<String> anyoneClients = Utils.getDisjunction(octoparseIntersection, new ArrayList<>(clientsAndWebNotSend.keySet()));
        LOG.info("Finalizo de la disyunción de los clientes con origen cualquiera y generación de fichero.");
        LOG.info("Escribiedo resultados...");
        Text.generateTxtFile(anyoneClients, PATH, FILENAME_DISJUNCTION);
        LOG.info("Los resultados han sido escritos en "+PATH+FILENAME_DISJUNCTION);
    }

    /**
     * Método que genera los ficheros csv para enviarlos a Marketgoo
     * @param clientCodes
     * @param fileName
     */
    private void generateFile(List<String> clientCodes, String fileName){
        Map<String, String > clientsAndWebNotSend = Utils.generateMapFromFile(PATH, FILENAME_CLIENTSANDWEB_NOTSEND);
        List<String> result = new ArrayList<>();
        clientCodes.forEach(clientCode -> {
            result.add(clientCode + "," + clientsAndWebNotSend.get(clientCode));
        });
        LOG.info("Generando fichero csv...");
        Excel.generateCSVWithUrls(PATH, fileName, result);
        LOG.info("Generando fichero csv ha sido escrito en "+PATH+fileName);
    }

    /**
     * Proceso principal para la obtención de URLs para en envío a Marketgoo
     */
    public void processGetUrls() {
        /**
         * PASO 1: Obtengo todos los códigos de clientes de la BDC, estos códigos son tipo Long, hay que pasarlos a String
         */
        generateClientCodesBDC();
        /**
         * PASO 2: Obtengo los clientes potenciales (no vivos) y los clientes vivos pero que no tengan contratado WEB
         */
        generateClientCodesPotentialAndLivingWithoutWeb();
        /**
         * PASO 3: Obtengo todos los clientes que tengan WEB de la lista obtenida en el paso Segundo
         */
        getAllClientsWithWeb();
        /**
         * PASO 4: Verifico que las URLs de los clientes obtenidos en el paso Tercero no hayan sido enviadas a Marketgoo
         */
        getURLsNoSendToMarketgoo();
        /**
         * PASO 5: Obtengo todos los códigos de clientes cuyo origen sea Octoparse
         */
        generateClientCodesOctoparse();
        /**
         * PASO 6: Intersección con los códigos de octoparse haciendo uso del mapa obtenido en el paso Cuarto
         */
        urlsOctoparseClients();
        /**
         * PASO 7: Disyunción con la lista clientesVivosNoVivosOrigenOctoparse haciendo uso del mapa obtenido en el paso Cuarto
         */
        urlsAnyOneClients();
        /**
         * PASO 8: Genero los ficheros para enviarlos a Marketgoo
         */
        List<String> intersection = Utils.generateListFromFile(PATH, FILENAME_INTERSECTION);
        generateFile(intersection, FILENAME_FINAL_OCTOPARSE);
        List<String> disjunction = Utils.generateListFromFile(PATH, FILENAME_DISJUNCTION);
        generateFile(disjunction, FILENAME_FINAL_ANYONE);
    }

    /**
     * Proceso secundario para la obtención de URLs en el caso de no encontrar suficientes
     */
    public void subProcessGetUrls() {
        /**
         * PASO 1: Obtener todos los códigos de clientes (identifier) de la colección webVisibilytiAnalytics
         */
        LOG.info("PASO 1: Obtener todos los códigos de clientes (identifier) de la colección webVisibilytiAnalytics");
        List<String> values = webVisibilityAnalyticsProjection.findAllClientCodesWithoutRepetition();//779565
        /**
         * PASO 2: Elimino todos los códigos de prueba
         */
        LOG.info("PASO 2: Elimino todos los códigos de prueba");
        List<String> codesPiloto = Utils.generateListFromFile("C:/knowledge-base/automation/src/main/resources/", FILENAME_PILOTO);//1856
        List<String> disyuncion = Utils.getDisjunction(values, codesPiloto);//589724
        /**
         * PASO 3: Elimino todos los códigos que no sean numéricos
         */
        LOG.info("PASO 3: Elimino todos los códigos que no sean numéricos");
        List<String> clientCodes = Utils.getClientCodes(disyuncion);//557917
        Text.generateTxtFileWithStrings(clientCodes, PATH, "codigos_validos.txt");
        clientCodes = Utils.generateListFromFile(PATH, "codigos_validos.txt");
        /**
         * PASO 4: Por cada código busco los que tengan y no tengan última llamada
         * Genero dos listas y las escribo en disco
         * Este paso se puede hacer manualmente cuando no son muchos códigos de clientes
         */
        LOG.info("PASO 4: Por cada código busco los que tengan y no tengan última llamada");
        List<String> haveLastCall = new ArrayList<>();
        List<String> notHaveLastCall = new ArrayList<>();
        for(String clientCode : clientCodes){
            LOG.info("Buscando cliente: "+clientCode);
            Date result = thvActividadComercialConsProjection.findUltimaAnotacionLlamada(clientCode);
            if(result != null){
                haveLastCall.add(clientCode);
            }else{
                notHaveLastCall.add(clientCode);
            }
        }
        Text.generateTxtFile(haveLastCall, PATH, "con_ultima_llamada.txt");
        Text.generateTxtFile(notHaveLastCall, PATH, "sin_ultima_llamada.txt");
        /**
         * PASO 5: Recopilar las URLs y asociarlas al código de cliente
         */
        LOG.info("PASO 5: Recopilar las URLs y asociarlas al código de cliente");
        haveLastCall = Utils.generateListFromFile(PATH, "con_ultima_llamada.txt");
        notHaveLastCall = Utils.generateListFromFile(PATH, "sin_ultima_llamada.txt");
        generateFileWithUrls(haveLastCall, PATH, "con_ultima_llamada_and_web.csv");
        generateFileWithUrls(notHaveLastCall, PATH, "sin_ultima_llamada_and_web.csv");

    }

    /**
     * Método que busca las urls de los clientes, asocia el cliente con la url, guarda en una lista cada asociación
     * y genera un fichero .txt con los resultados.
     * @param clientCodes
     * @param path
     * @param fileName
     */
    public void generateFileWithUrls(List<String> clientCodes, String path, String fileName){
        List<String> result = new ArrayList<>();
        for(String clientCode : clientCodes){
            String url = tsiFWebProjection.findDiUrl(clientCode);
            if(Utils.evaluateString(url)){
                result.add(clientCode+","+url);
            }
        }
        Text.generateTxtFileWithStrings(result, path, fileName);
    }

    /**
     * Método que genera una lista con los códigos de clientes concatenados para su posterior búsqueda manual
     * @param origin
     * @param destination
     */
    public void generateClientCodesConcat(String origin, String destination){
        List<String> codes = Utils.generateListFromFile(PATH, origin);
        List<String> result = new ArrayList<>();
        String cadena = "";
        int count = 0;
        for(int i=0; i < codes.size(); i++){
            cadena=cadena+"co_cliente="+codes.get(i)+" or ";
            count++;
            if(count==6){
                result.add(cadena);
                cadena="";
                count=0;
            }
            if(i==codes.size()-1){
                result.add(cadena);
            }
        }
        Text.generateTxtFileWithStrings(result, PATH, destination);
    }
}
