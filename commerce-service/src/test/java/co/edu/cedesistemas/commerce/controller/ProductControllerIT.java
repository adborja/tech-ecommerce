package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.CommerceApp;
import co.edu.cedesistemas.commerce.commons.TestUtils;
import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.common.BaseIT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CommerceApp.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductControllerIT extends BaseIT<Product> {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    public void testCreateProduct() throws Exception {
        String name = RandomStringUtils.randomAlphabetic(10);
        String description = RandomStringUtils.randomAlphabetic(20);

        Product created = createProduct(mvc, objectMapper, name, description);

        mvc.perform(get("/products/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._source[0].id", is(created.getId())))
                .andExpect(jsonPath("$._source[0].name", is(name)))
                .andExpect(jsonPath("$._source[0].description", is(description)));
    }

    @Test
    public void testGetByName() throws Exception {
        String prefix = RandomStringUtils.randomAlphanumeric(10);//Se cambia inicialización del prefijo para que sea nuevo en cada ejecución y no se acumule con anteriores pruebas

        createProduct(mvc, objectMapper, prefix + RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(20));
        createProduct(mvc, objectMapper, prefix + RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(20));

        mvc.perform(get("/products/by-name")
                .param("name", prefix))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._hits", is(2)))
                .andExpect(jsonPath("$._source[0].name", startsWith(prefix)))
                .andExpect(jsonPath("$._source[1].name", startsWith(prefix)));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        String name = RandomStringUtils.randomAlphabetic(10);
        String description = RandomStringUtils.randomAlphabetic(20);

        Product created = createProduct(mvc, objectMapper, name, description);

        String newDesc = RandomStringUtils.randomAlphabetic(20);

        String updateData = "{\"description\":\"" + newDesc + "\"}";

        mvc.perform(patch("/products/" + created.getId())
                .contentType("application/json")
                .content(updateData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._hits", is(1)))
                .andExpect(jsonPath("$._source[0].name", is(name)))
                .andExpect(jsonPath("$._source[0].description", is(newDesc)));
    }

    @Test
    public void testInvalidUpdateProduct() throws Exception {
        String name = RandomStringUtils.randomAlphabetic(10);
        String description = RandomStringUtils.randomAlphabetic(20);

        Product created = createProduct(mvc, objectMapper, name, description);

        String newId = UUID.randomUUID().toString();

        String updateData = "{\"id\":\"" + newId + "\"}";

        mvc.perform(patch("/products/" + created.getId())
                .contentType("application/json")
                .content(updateData))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        String name = RandomStringUtils.randomAlphabetic(10);
        String description = RandomStringUtils.randomAlphabetic(20);

        Product created = createProduct(mvc, objectMapper, name, description);

        mvc.perform(delete("/products/" + created.getId()))
                .andExpect(status().isOk());

        mvc.perform(get("/products/" + created.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())));
    }

    public static Product createProduct(final MockMvc mvc, final ObjectMapper mapper,
                                        final String name, final String description) throws Exception {
        Product product = TestUtils.buildProduct(name, description);

        MvcResult result = mvc.perform(post("/products")
                .contentType("application/json")
                .content(mapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        JsonNode node = mapper.readTree(response.getContentAsString());
        JsonNode _source = node.get("_source");

        List<Product> products = mapper.convertValue(_source, new TypeReference<>(){});

        assertThat(_source, notNullValue());
        assertThat(products, notNullValue());
        assertThat(products.size(), equalTo(1));

        return products.get(0);
    }
}
