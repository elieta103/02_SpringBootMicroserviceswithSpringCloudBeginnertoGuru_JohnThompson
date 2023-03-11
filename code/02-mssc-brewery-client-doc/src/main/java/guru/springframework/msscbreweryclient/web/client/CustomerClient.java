package guru.springframework.msscbreweryclient.web.client;


import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

/**
 * Created by jt on 2019-04-23.
 */
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Component
public class CustomerClient {

    public final String CUSTOMER_PATH_V1 = "/api/v1/customer";
    private String apihost;

    private final RestTemplate restTemplate;

    public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    public CustomerDto getCustomer(UUID uuid){
        return restTemplate.getForObject(apihost + CUSTOMER_PATH_V1 +"/"+ uuid.toString(), CustomerDto.class);
    }

    public URI saveNewCustomer(CustomerDto customerDto){
        return restTemplate.postForLocation(apihost + CUSTOMER_PATH_V1, customerDto);
    }

    public void updateCustomer(UUID uuid, CustomerDto customerDto){
        restTemplate.put(apihost + CUSTOMER_PATH_V1 +"/"+uuid.toString(), customerDto);
    }

    public void deleteCustomer(UUID uuid){
        restTemplate.delete(apihost + CUSTOMER_PATH_V1+ "/" +uuid.toString());
    }

    public void setApihost(String apihost) {
        this.apihost = apihost;
    }
}
