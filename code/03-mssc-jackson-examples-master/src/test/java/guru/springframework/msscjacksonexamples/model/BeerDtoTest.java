package guru.springframework.msscjacksonexamples.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.*;


@JsonTest
class BeerDtoTest extends BaseTest{

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void testSerializeDto() throws JsonProcessingException {
        BeerDto beerDto = getDto();

        String jsonString = objectMapper.writeValueAsString(beerDto);

        System.out.println(jsonString);
    }


    @Test
    void testDeserializeDto() throws JsonProcessingException {
        String json =  "{\"beerName\":\"BeerName\",\"beerStyle\":\"Ale\",\"upc\":123123123,\"price\":\"12.29\",\"createdDate\":\"2023-03-10T20:-0600\",\"lastUpdatedDate\":null,\"myLocalDate\":\"20230310\",\"beerId\":\"9206385e-4a20-476b-bf2c-0379485c90fa\"}\n";
        BeerDto beerDto = objectMapper.readValue(json, BeerDto.class);

        System.out.println(beerDto.toString());
    }
}