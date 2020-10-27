package com.pinnacle.receipt;

import static org.mockito.Mockito.when;

import com.pinnacle.receipt.model.Category;
import com.pinnacle.receipt.model.Item;
import com.pinnacle.receipt.model.Tax;
import com.pinnacle.receipt.repository.CategoryRepository;
import com.pinnacle.receipt.repository.TaxRepository;
import com.pinnacle.receipt.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReceiptApplicationTests {

  @Autowired
  private ReceiptService receiptService;

  @MockBean
  private TaxRepository taxRepository;

  @MockBean
  private CategoryRepository categoryRepository;

  @Test
  void contextLoads() throws Exception {
    System.out.println("---------- Spring Test Start ----------");
    assertThat(receiptService).isNotNull();
    assertThat(taxRepository).isNotNull();
    assertThat(categoryRepository).isNotNull();

    // Mock TaxRepository for loadCache
    Tax caTax = new Tax();
    caTax.setLocation("CA");
    caTax.setRate(new BigDecimal("0.0975"));
    caTax.setExempt(Collections.singletonList("food"));
    when(taxRepository.findAll()).thenReturn(Collections.singletonList(caTax));

    // Mock CategoryRepository for loadCache
    Category foodCat = new Category();
    foodCat.setName("food");
    foodCat.setInclude(Collections.singletonList("potato chips"));
    when(categoryRepository.findAll()).thenReturn(Collections.singletonList(foodCat));


    // loadCache before other tests start
    Method loadCacheMethod = receiptService.getClass().getDeclaredMethod("loadCache");
    loadCacheMethod.setAccessible(true);
    loadCacheMethod.invoke(receiptService);
    loadCacheMethod.setAccessible(false);

  }

  @Test
  void testGetTax() {
    String location = "CA";
    List<Item> items = Arrays.asList(
      new Item("book", new BigDecimal("17.99"), 1),
      new Item("potato chips", new BigDecimal("3.99"), 1)
    );

    BigDecimal result = receiptService.calculateTax(location, items);
    BigDecimal expectedResult = new BigDecimal("1.80");

    assertEquals(expectedResult, result);
  }

}
