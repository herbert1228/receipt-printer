package com.pinnacle.receipt.service.impl;

import com.pinnacle.receipt.model.Category;
import com.pinnacle.receipt.model.Item;
import com.pinnacle.receipt.model.ShoppingCartDto;
import com.pinnacle.receipt.model.Tax;
import com.pinnacle.receipt.repository.CategoryRepository;
import com.pinnacle.receipt.repository.TaxRepository;
import com.pinnacle.receipt.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReceiptServiceImpl implements ReceiptService {

  @Autowired
  TaxRepository taxRepository;

  @Autowired
  CategoryRepository categoryRepository;

  private Map<String, Tax> taxMapCache;
  private Map<String, Category> categoryMapCache;

  /**
   * Cache data on system start to reduce db access
   *
   * run once on startup
   */
  @PostConstruct
  private void loadCache() {

    taxMapCache = taxRepository.findAll().stream()
            .collect(Collectors.toMap(Tax::getLocation, tax -> tax));

    categoryMapCache = categoryRepository.findAll().stream()
            .collect(Collectors.toMap(Category::getName, category -> category));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initCart(ShoppingCartDto shoppingCart) {
    BigDecimal subTotal = shoppingCart.calculateSubTotal();
    BigDecimal tax = calculateTax(shoppingCart.getLocation(), shoppingCart.getItems());

    shoppingCart.setSubTotal(subTotal);
    shoppingCart.setTax(tax);
    shoppingCart.setTotal(subTotal.add(tax));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal calculateTax(String location, List<Item> items) {
    Tax taxConfig = taxMapCache.get(location);

    if (Objects.isNull(taxConfig)) {
      return new BigDecimal(0);
    }

    BigDecimal taxRate = taxConfig.getRate();
    List<String> exemptList = taxConfig.getExempt();

    BigDecimal result = items.stream()
            .filter(item -> !exemptList.contains(getCategoryNameByItemName(item.getName())))    // filter items that are exempted
            .map(Item::getSum)
            .map(sum -> sum.multiply(taxRate))      // multiply before addition in order to apply round up
            .reduce(BigDecimal.ZERO, BigDecimal::add)   // sum the tax for not exempted items
            .setScale(2, BigDecimal.ROUND_UP);

    return roundUpToNearestFiveCents(result);
  }




  /*
      Private methods
   */

  private String getCategoryNameByItemName(String productName) {
    return categoryMapCache.entrySet().stream()
            .filter(set -> set.getValue().getInclude().contains(productName))
            .map(Map.Entry::getValue)
            .map(Category::getName)
            .findFirst()
            .orElse("");
  }

  private BigDecimal roundUpToNearestFiveCents(BigDecimal value) {
    // multiply by 20, round to the nearest integer, then divide by 20
    BigDecimal result =  new BigDecimal(Math.ceil(value.doubleValue() * 20) / 20);
    return result.setScale(2, RoundingMode.HALF_UP);
  }
}
