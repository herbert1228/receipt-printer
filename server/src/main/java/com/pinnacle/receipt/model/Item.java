package com.pinnacle.receipt.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Item {

  @Id
  private String id;

  private String name;
  private BigDecimal price;
  private Integer qty;

  public Item(String name, BigDecimal price, Integer qty) {
    this.name = name;
    this.price = price;
    this.qty = qty;
  }

  public BigDecimal getSum() {
    return price.multiply(new BigDecimal(qty));
  }
}