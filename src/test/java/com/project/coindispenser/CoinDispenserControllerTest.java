package com.project.coindispenser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class CoinDispenserControllerTest {

    private static final String BASE_API_URL = "/coin-dispenser/api";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testTextInput_BadRequest() throws Exception {

        String requestJson = "{\"billValue\":\"abc\"}";
        String responseJson = "{\"error\":\"Bill value is not valid! Value should be from the list: [1, 2, 5, 10, 20, 50, 100]\"}";

        mockMvc.perform(buildRequetBuilderForPost("/dispense", requestJson))
                    //.andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(responseJson));
    }

    @Test
    @Order(1)
    public void testInvalidInput_BadRequest() throws Exception {

        String requestJson = "{\"billValue\":\"6\"}";
        String responseJson = "{\"error\":\"Bill value is not valid! Value should be from the list: [1, 2, 5, 10, 20, 50, 100]\"}";

        mockMvc.perform(buildRequetBuilderForPost("/dispense", requestJson))
                    //.andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(responseJson));
    
    }

    @Test
    @Order(1)
    public void testNoChange_NotFound() throws Exception {

        String requestJson = "{\"billValue\":\"50\"}";
        String responseJson = "{\"error\":\"No change available\"}";

        mockMvc.perform(buildRequetBuilderForPost("/dispense", requestJson))
                    //.andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().json(responseJson));
    
    }

    @Test
    @Order(2)
    public void testMultipleCallsWithValidInput_OK() throws Exception {

        String requestJson = "{\"billValue\":\"20\"}";
        String responseJson = "{\"0.25\":80}";

        mockMvc.perform(buildRequetBuilderForPost("/dispense", requestJson))
                    .andExpect(status().isOk())
                    .andExpect(content().json(responseJson));

        requestJson = "{\"billValue\":\"10\"}";
        responseJson = "{\"0.25\":20,\"0.10\":50}";

        mockMvc.perform(buildRequetBuilderForPost("/dispense", requestJson))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));

        requestJson = "{\"billValue\":\"20\"}";
        responseJson = "{\"error\":\"No change available\"}";

        mockMvc.perform(buildRequetBuilderForPost("/dispense", requestJson))
            .andExpect(status().isNotFound())
            .andExpect(content().json(responseJson));
                    
    }

    private RequestBuilder buildRequetBuilderForPost(String uri, String requestBody) {
        return post(BASE_API_URL + uri).contextPath(BASE_API_URL).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody);
    }

    private RequestBuilder buildRequetBuilderForGet(String uri) {
        return get(BASE_API_URL + uri).contextPath(BASE_API_URL);
    }

    private void getAndPrintBalance() throws Exception {
        MvcResult result = mockMvc.perform(buildRequetBuilderForGet("/balance"))
                    .andExpect(status().isOk())
                    .andReturn();
        System.out.println("Balance = " + result.getResponse().getContentAsString());
    }
    
}
