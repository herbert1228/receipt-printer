package com.pinnacle.receipt.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Category {

  @Id
  private String id;

  private String name;
  private List<String> include;   // items that belong to this category

}