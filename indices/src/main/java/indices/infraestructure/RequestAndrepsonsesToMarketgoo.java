package indices.infraestructure;

import automation.factory.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestAndrepsonsesToMarketgoo {

    private static Logger LOG = Logger.getLogger(RequestAndrepsonsesToMarketgoo.class);

    public boolean updateCustomerInformation(int machine, String identifier, String domain) {
//        http://prlwscamp01.v.cp.inet:8200/webvisaws/findContactData?user=phe&system=100&reason=Consulta&identifier=3437282&domain=www.frankfurteriaurgell.es
        boolean res = true;
        try {
            String service_url = "http://prlwscamp0"+machine+".v.cp.inet:8200/webvisaws/findContactData";
            String params = "?user=phe&system=100&reason=carga_prueba_indices&identifier="+identifier+"&domain="+domain;
            String url = service_url+params;
            URI direccion = new URI(url);
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(direccion)
                    .build();
            LOG.info("Actualizando datos para el cliente: "+identifier);
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error actualizando los datos para el cliente: "+identifier);
            res = false;
        }
        LOG.info("Se ahn actualizando los datos para el cliente: "+identifier);
        return res;
    }

}
