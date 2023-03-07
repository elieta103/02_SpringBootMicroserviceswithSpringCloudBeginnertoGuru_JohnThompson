package guru.springframework.msscbrewery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.msscbrewery.model.BeerDtoV2;
import guru.springframework.msscbrewery.model.BeerStyleEnum;
import guru.springframework.msscbrewery.services.BeerServiceV2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;


import java.time.OffsetDateTime;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// Imports para el Spring Rest Docs
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;


@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerControllerV2.class)
class BeerControllerTest {

    @MockBean
    BeerServiceV2 beerServiceV2;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDtoV2 validBeer;

    @BeforeEach
    public void setUp() {
        validBeer = BeerDtoV2.builder()
                .id(UUID.randomUUID())
                .beerName("Beer1")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(1234567890L)
                .build();
    }

    @Test
    void getBeerById() throws Exception {
        given(beerServiceV2.getBeerById(any(UUID.class))).willReturn(validBeer);

        ConstrainedFields fields = new ConstrainedFields(BeerDtoV2.class);

        mockMvc.perform(get("/api/v2/beer/{beerId}", validBeer.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is("Beer1")))
                .andDo(document("v2/beer-get",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        responseFields(
                                fields.withPath("id").description("Id of Beer").type(UUID.class),
                                fields.withPath("createdDate").description("Date Created").type(OffsetDateTime.class),
                                fields.withPath("lastUpdatedDate").description("Date Updated").type(OffsetDateTime.class),
                                fields.withPath("beerName").description("Beer Name"),
                                fields.withPath("beerStyle").description("Beer Style"),
                                fields.withPath("upc").description("UPC of Beer")
                        )));
    }

    @Test
    void saveNewBeer() throws Exception {

        //given
        BeerDtoV2 beerDto = validBeer;
        beerDto.setId(null);
        BeerDtoV2 savedDto = BeerDtoV2.builder().id(UUID.randomUUID()).beerName("New Beer").build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        given(beerServiceV2.saveNewBeer(any())).willReturn(savedDto);

        ConstrainedFields fields = new ConstrainedFields(BeerDtoV2.class);

        mockMvc.perform(post("/api/v2/beer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v2/beer-new",
                        requestFields(
                                fields.withPath("id").description("Id of Beer").ignored(),
                                fields.withPath("createdDate").description("Date Created").ignored(),
                                fields.withPath("lastUpdatedDate").description("Date Updated").ignored(),
                                fields.withPath("beerName").description("Beer Name"),
                                fields.withPath("beerStyle").description("Beer Style"),
                                fields.withPath("upc").description("UPC of Beer")
                        )));

        // Ejemplo POST
        /*mvc.perform( MockMvcRequestBuilders
	      .post("/employees")
	      .content(objectMapper.writeValueAsString(new EmployeeVO(null, "firstName4", "lastName4", "email4@mail.com")))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").exists());*/

    }

    @Test
    void updateBeerById() throws Exception {
        //given
        BeerDtoV2 beerDto = validBeer;
        beerDto.setId(null);
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        //when
        mockMvc.perform(put("/api/v2/beer/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isNoContent());

        then(beerServiceV2).should().updateBeer(any(), any());

        //Ejemplo PUT
        /*mockMvc.perform( MockMvcRequestBuilders
	      .put("/employees/{id}", 2)
	      .content(objectMapper.writeValueAsString(new EmployeeVO(2, "firstName2", "lastName2", "email2@mail.com")))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("firstName2"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("lastName2"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email2@mail.com"));*/
    }

    private static class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }

    }
}