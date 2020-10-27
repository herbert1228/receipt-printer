package com.pinnacle.receipt;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.google.gson.Gson;
import com.pinnacle.receipt.controller.MainController;
import com.pinnacle.receipt.model.Item;
import com.pinnacle.receipt.model.ShoppingCartDto;
import com.pinnacle.receipt.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(MainController.class)
public class WebMockTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ReceiptService receiptService;

  @Test
  public void getReceiptShouldReturnInitedShoppingCart() throws Exception {

    String location = "CA";
    List<Item> items = Arrays.asList(
      new Item("book", new BigDecimal("17.99"), 1),
      new Item("potato chips", new BigDecimal("3.99"), 1)
    );

    ShoppingCartDto requestCart = new ShoppingCartDto(location, items);

    ShoppingCartDto responseCart = new ShoppingCartDto(location, items);
    responseCart.setSubTotal(new BigDecimal("21.98"));
    responseCart.setTax(new BigDecimal("1.8"));
    responseCart.setTotal(new BigDecimal("23.78"));

    // get requestJson and expected responseJson
    Gson gson = new Gson();
    String requestJson = gson.toJson(requestCart);
    String responseJson = gson.toJson(responseCart);

    // mock implementation for receiptService.init()
    doAnswer(new Answer<Void>() {
      @Override
      public Void answer(InvocationOnMock invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        if (arguments != null && arguments.length > 0  && arguments[0] != null) {
          ShoppingCartDto shoppingCart = (ShoppingCartDto) arguments[0];
          shoppingCart.setSubTotal(new BigDecimal("21.98"));
          shoppingCart.setTax(new BigDecimal("1.8"));
          shoppingCart.setTotal(new BigDecimal("23.78"));
        }
        return null;
      }
    }).when(receiptService).initCart(any(ShoppingCartDto.class));

    RequestBuilder request = MockMvcRequestBuilders
            .post("/get_receipt")
            .accept(MediaType.APPLICATION_JSON)
//            .content("{\"items\":[{\"id\":null,\"name\":\"book\",\"price\":17.99,\"qty\":1,\"sum\":17.99},{\"id\":null,\"name\":\"potato chips\",\"price\":3.99,\"qty\":1,\"sum\":3.99}],\"loc\":\"CA\"}")
            .content(requestJson)
            .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(request)
            .andExpect(status().isOk())
//            .andExpect(content().json("\"items\":[{\"id\":null,\"name\":\"book\",\"price\":17.99,\"qty\":1,\"sum\":17.99},{\"id\":null,\"name\":\"potato chips\",\"price\":3.99,\"qty\":1,\"sum\":3.99}],\"loc\":\"CA\"}"))
            .andExpect(content().json(responseJson))
            .andDo(MockMvcResultHandlers.print())
            .andReturn();

  }
}
