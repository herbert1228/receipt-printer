package com.pinnacle.receipt.controller;

import com.pinnacle.receipt.model.ShoppingCartDto;
import com.pinnacle.receipt.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MainController {

  @Autowired
  ReceiptService receiptService;

  private static final String BASE_URL = "/";
  private static final String GET_RECEIPT_URL = BASE_URL + "get_receipt";

  @GetMapping(BASE_URL)
  public String test() {
    return "Hello World";
  }

  @PostMapping(GET_RECEIPT_URL)
  public ShoppingCartDto getReceipt(@RequestBody ShoppingCartDto shoppingCart) {
    receiptService.initCart(shoppingCart);    // calculate subTotal, tax and total

    return shoppingCart;
  }
}