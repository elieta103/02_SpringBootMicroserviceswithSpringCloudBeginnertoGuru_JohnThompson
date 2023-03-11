package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CustomerClientTest {

    @Autowired
    CustomerClient client;

    @Test
    void getCustomerById() {
        CustomerDto dto = client.getCustomer(UUID.randomUUID());
        System.out.println(dto.toString());
        assertNotNull(dto);
    }


    @Test
    void testSaveNewCustomer() {
        //given
        CustomerDto customerDto = CustomerDto.builder().name("Eliel Herrera").build();
        URI uri = client.saveNewCustomer(customerDto);
        assertNotNull(uri);

        System.out.println(uri.toString());
    }

    @Test
    void testUpdateCustomer() {
        //given
        CustomerDto customerDto = CustomerDto.builder().name("Juan Perez").build();
        client.updateCustomer(UUID.randomUUID(), customerDto);
    }

    @Test
    void testDeleteCustomer() {
        //given
        client.deleteCustomer(UUID.randomUUID());
    }
}