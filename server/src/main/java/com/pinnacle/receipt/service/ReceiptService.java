package com.pinnacle.receipt.service;

import com.pinnacle.receipt.model.Item;
import com.pinnacle.receipt.model.ShoppingCartDto;

import java.math.BigDecimal;
import java.util.List;

public interface ReceiptService {

  /**
   * Calculate and set subTotal, tax and total of the cart
   *
   * @param shoppingCart Shopping Cart
   */
  void initCart(ShoppingCartDto shoppingCart);

  /**
   * Calculate tax based on location and items
   *
   * @param location the location
   * @param items    the list of items
   * @return the amount of tax
   */
  BigDecimal calculateTax(String location, List<Item> items);

}
