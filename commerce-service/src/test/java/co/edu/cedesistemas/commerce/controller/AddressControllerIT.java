package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.CommerceApp;
import co.edu.cedesistemas.commerce.commons.TestUtils;
import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.common.BaseIT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CommerceApp.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AddressControllerIT extends BaseIT<Address> {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    public void testCreateAddress() throws Exception {
        Address created = createAddress(mvc, objectMapper);

        mvc.perform(get("/addresses/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._source[0].id", is(created.getId())))
                .andExpect(jsonPath("$._source[0].name", is(created.getName())))
                .andExpect(jsonPath("$._source[0].city", is(created.getCity())));
    }

    public static Address createAddress(final MockMvc mvc, final ObjectMapper mapper) throws Exception {
        Address address = TestUtils.buildAddress();

        MvcResult result = mvc.perform(post("/addresses")
                .contentType("application/json")
                .content(mapper.writeValueAsString(address)))
                .andExpect(status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        JsonNode node = mapper.readTree(response.getContentAsString());
        JsonNode _source = node.get("_source");

        List<Address> addresses = mapper.convertValue(_source, new TypeReference<>(){}); 

        assertThat(_source, notNullValue());
        assertThat(addresses, notNullValue());
        assertThat(addresses.size(), equalTo(1));

        return addresses.get(0);
    }
}
