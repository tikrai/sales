package com.tikrai.gmail.sales;

import com.tikrai.gmail.sales.model.Transaction;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Fixture {

  public static Transaction transaction() {
    return new Transaction()
        .setTransactionId(1001)
        .setCustomerId("cust-001")
        .setItemId("product-001")
        .setTransactionDateString("2022-01-01")
        .setItemPrice(BigDecimal.valueOf(12.50).setScale(2, RoundingMode.UNNECESSARY))
        .setItemQuantity(BigDecimal.valueOf(3));
  }
}
