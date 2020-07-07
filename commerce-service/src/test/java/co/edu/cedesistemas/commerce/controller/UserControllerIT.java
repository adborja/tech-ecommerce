package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.CommerceApp;
import co.edu.cedesistemas.commerce.commons.TestUtils;
import co.edu.cedesistemas.commerce.model.User;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
public class UserControllerIT extends BaseIT {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    public void testCreateUser() throws Exception {
        String userEmail = RandomStringUtils.randomAlphabetic(5) + "@company.com";
        String userName = RandomStringUtils.randomAlphabetic(10);
        String userLastName = RandomStringUtils.randomAlphabetic(10);

        User created = createUser(mvc, objectMapper, userEmail, userName, userLastName);

        mvc.perform(get("/users/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._source[0].id", is(created.getId())))
                .andExpect(jsonPath("$._source[0].name", is(userName)))
                .andExpect(jsonPath("$._source[0].email", is(userEmail)));
    }

    @Test
    public void testGetByEmail() throws Exception {
        String userEmail = RandomStringUtils.randomAlphabetic(5) + "@company.com";
        String userName = RandomStringUtils.randomAlphabetic(10);
        String userLastName = RandomStringUtils.randomAlphabetic(10);

        User created = createUser(mvc, objectMapper, userEmail, userName, userLastName);

        mvc.perform(get("/users/by-email")
                .param("email", userEmail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._source[0].id", is(created.getId())))
                .andExpect(jsonPath("$._source[0].name", is(userName)))
                .andExpect(jsonPath("$._source[0].email", is(userEmail)));
    }

    @Test
    public void testUpdateUser() throws Exception {
        String userEmail = RandomStringUtils.randomAlphabetic(5) + "@company.com";
        String userName = RandomStringUtils.randomAlphabetic(10);
        String userLastName = RandomStringUtils.randomAlphabetic(10);

        User created = createUser(mvc, objectMapper, userEmail, userName, userLastName);

        String newName = RandomStringUtils.randomAlphabetic(10);
        String newLastName = RandomStringUtils.randomAlphabetic(10);

        String updateData = "{\"name\": \"" + newName + "\", \"lastName\": \"" + newLastName + "\"}";

        mvc.perform(patch("/users/" + created.getId())
                .contentType("application/json")
                .content(updateData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._source[0].id", is(created.getId())))
                .andExpect(jsonPath("$._source[0].name", is(newName)))
                .andExpect(jsonPath("$._source[0].lastName", is(newLastName)))
                .andExpect(jsonPath("$._source[0].email", is(userEmail)));
    }

    @Test
    public void testDeleteUser() throws Exception {
        String userEmail = RandomStringUtils.randomAlphabetic(5) + "@company.com";
        String userName = RandomStringUtils.randomAlphabetic(10);
        String userLastName = RandomStringUtils.randomAlphabetic(10);

        User created = createUser(mvc, objectMapper, userEmail, userName, userLastName);

        mvc.perform(delete("/users/" + created.getId()))
                .andExpect(status().isOk());

        mvc.perform(get("/users/" + created.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())));
    }

    public static User createUser(final MockMvc mvc, final ObjectMapper mapper,
                                  final String email, final String name, final String lastName) throws Exception {
        User user = TestUtils.buildUser(email, name, lastName);

        MvcResult result = mvc.perform(post("/users")
                .contentType("application/json")
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        JsonNode node = mapper.readTree(response.getContentAsString());
        JsonNode _source = node.get("_source");

        List<User> users = mapper.convertValue(_source, new TypeReference<List<User>>(){});

        assertThat(_source, notNullValue());
        assertThat(users, notNullValue());
        assertThat(users.size(), equalTo(1));

        return users.get(0);
    }
}
