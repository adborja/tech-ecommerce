package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.CommerceApp;
import co.edu.cedesistemas.commerce.commons.TestUtils;
import co.edu.cedesistemas.commerce.model.Store;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CommerceApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class StoreControllerIT extends BaseIT<Store> {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    public void testCreateStore() throws Exception {
        String storeName = RandomStringUtils.randomAlphanumeric(10);
        String storePhone = RandomStringUtils.randomNumeric(8);
        String storeAddress = RandomStringUtils.randomAlphanumeric(15);

        Store created = createStore(mvc, objectMapper, storeName, storePhone, storeAddress, Store.Type.PETS);

        mvc.perform(get("/stores/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._source[0].id", is(created.getId())))
                .andExpect(jsonPath("$._source[0].name", is(storeName)));
    }

    @Test
    public void testUpdateStore() throws Exception {
        String storeName = RandomStringUtils.randomAlphanumeric(10);
        String storePhone = RandomStringUtils.randomNumeric(8);
        String storeAddress = RandomStringUtils.randomAlphanumeric(15);

        Store created = createStore(mvc, objectMapper, storeName, storePhone, storeAddress, Store.Type.PETS);

        String newPhone = RandomStringUtils.randomNumeric(8);
        String newAddress = RandomStringUtils.randomNumeric(15);

        String updateData = "{\"phone\": \""+ newPhone + "\", \"address\": \"" + newAddress + "\"}";

        mvc.perform(patch("/stores/" + created.getId())
                .contentType("application/json")
                .content(updateData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._source[0].id", is(created.getId())))
                .andExpect(jsonPath("$._source[0].name", is(storeName)))
                .andExpect(jsonPath("$._source[0].phone", is(newPhone)))
                .andExpect(jsonPath("$._source[0].address", is(newAddress)));
    }

    public static Store createStore(final MockMvc mvc, final ObjectMapper mapper,
                                    final String name, final String phone,
                                    final String address, Store.Type type)
            throws Exception {
        Store store = TestUtils.buildStore(name, phone,
                address, type);
        MvcResult result = mvc.perform(post("/api/v1/stores").contextPath("/api/v1")
                .contentType("application/json")
                .content(mapper.writeValueAsString(store)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$._source[0].links[0].rel", is("self")))
                .andExpect(jsonPath("$._source[0].links[0].href",
                        containsString("/api/v1/stores/")))
                .andExpect(jsonPath("$._source[0].links[1].rel",
                        is("by-type")))
                .andExpect(jsonPath("$._source[0].links[1].href",
                        containsString("/stores/by-type?type=")))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        JsonNode node = mapper.readTree(response.getContentAsString());
        JsonNode _source = node.get("_source");

        List<Store> stores = mapper.convertValue(_source, new TypeReference<List>(){});

        assertThat(_source, notNullValue());
        assertThat(stores, notNullValue());
        assertThat(stores.size(), equalTo(1));

        return stores.get(0);
    }
}