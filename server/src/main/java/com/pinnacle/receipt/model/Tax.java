package com.pinnacle.receipt.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Tax {

  @Id
  private String id;

  private String location = "";
  private BigDecimal rate = new BigDecimal(0);
  private List<String> exempt = new ArrayList<>();

}