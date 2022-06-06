package it.com.ibm.websphere.daytrader.web;

import static org.junit.Assert.assertEquals;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.junit.Test;

public class HealthEndpointIT {

   @Test
   public void testHealthEndpoint() {
       Client client = ClientBuilder.newClient();
       client.register(JsrJsonpProvider.class);
       String healthUrl = "http://localhost:9082/health/";
       Response response = client.target(healthUrl).request().get();
       assertEquals("Incorrect response code from " + healthUrl, 200, response.getStatus());

       JsonObject healthJson = response.readEntity(JsonObject.class);
       String expectedOutcome = "UP";
       String actualOutcome = healthJson.getString("outcome");
       assertEquals("Application should be healthy", expectedOutcome, actualOutcome);

       response.close();
       client.close();
   }

    
}
