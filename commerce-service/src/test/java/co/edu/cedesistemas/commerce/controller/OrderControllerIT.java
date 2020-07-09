package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.CommerceApp;
import co.edu.cedesistemas.commerce.commons.TestUtils;
import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.common.BaseIT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;
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
public class OrderControllerIT extends BaseIT<Order> {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    @Override
    public void setup() {
        super.setup();
        dropCollection("user");
        dropCollection("store");
        dropCollection("address");
    }

    @AfterEach
    @Override
    public void tearDown() {
        super.tearDown();
        dropCollection("user");
        dropCollection("store");
        dropCollection("address");
    }

    @Test
    public void testCreateOrder() throws Exception {
        int nItems = RandomUtils.nextInt(1, 3);
        Order created = createOrder(mvc, objectMapper, nItems);

        mvc.perform(get("/orders/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._source[0].id", is(created.getId())))
                .andExpect(jsonPath("$._source[0].status", is(Order.Status.CREATED.name())))
                .andExpect(jsonPath("$._source[0].user.id", is(created.getUser().getId())))
                .andExpect(jsonPath("$._source[0].store.id", is(created.getStore().getId())));
    }

    @Test
    public void testGetOrderItems() throws Exception {
        int nItems = RandomUtils.nextInt(1, 3);
        Order created = createOrder(mvc, objectMapper, nItems);

        mvc.perform(get("/orders/" + created.getId() + "/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._hits", is(nItems)))
                .andExpect(jsonPath("$._source[0].product.id",
                        is(created.getItems().get(0).getProduct().getId())))
                .andExpect(jsonPath("$._source[0].finalPrice",
                        hasValue(created.getItems().get(0).getFinalPrice())));
    }

    public static Order createOrder(final MockMvc mvc, final ObjectMapper mapper, int nItems) throws Exception {
        String userEmail = RandomStringUtils.randomAlphanumeric(5) + "@company.com";
        String userName = RandomStringUtils.randomAlphabetic(10);
        String userLastName = RandomStringUtils.randomAlphabetic(10);
        User user = UserControllerIT.createUser(mvc, mapper, userEmail, userName, userLastName);

        String storeName = RandomStringUtils.randomAlphabetic(10);
        String storePhone = RandomStringUtils.randomNumeric(10);
        String storeAddress = RandomStringUtils.randomAlphanumeric(20);
        Store store = StoreControllerIT.createStore(mvc, mapper, storeName, storePhone,
                storeAddress, Store.Type.TECHNOLOGY);

        Address address = AddressControllerIT.createAddress(mvc, mapper);

        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < nItems; i++) {
            String productName = RandomStringUtils.randomAlphabetic(10);
            String productDesc = RandomStringUtils.randomAlphabetic(20);
            Product product = ProductControllerIT.createProduct(mvc, mapper, productName, productDesc);
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(RandomUtils.nextInt(1, 3));
            orderItem.setFinalPrice(RandomUtils.nextFloat(5000, 10000));
            items.add(orderItem);
        }

        Order order = TestUtils.buildOrder(store, user, address);
        order.setItems(items);

        MvcResult result = mvc.perform(post("/orders")
                .contentType("application/json")
                .content(mapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        JsonNode node = mapper.readTree(response.getContentAsString());
        JsonNode _source = node.get("_source");

        List<Order> orders = mapper.convertValue(_source, new TypeReference<>(){});

        assertThat(_source, notNullValue());
        assertThat(orders, notNullValue());
        assertThat(orders.size(), equalTo(1));

        return orders.get(0);
    }
}
