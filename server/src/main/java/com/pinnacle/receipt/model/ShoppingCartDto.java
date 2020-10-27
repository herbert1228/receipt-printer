package com.pinnacle.receipt.model;

import com.pinnacle.receipt.service.ReceiptService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShoppingCartDto {

  private String location = "";
  private List<Item> items = new ArrayList<>();
  private BigDecimal subTotal;
  private BigDecimal tax;
  private BigDecimal total;

  public ShoppingCartDto(String location, List<Item> items) {
    this.location = location;
    this.items = items;
  }

  public BigDecimal calculateSubTotal() {
    return items.stream()
      .map(Item::getSum)
      .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}