package com.tikrai.gmail.sales;

import com.tikrai.gmail.sales.model.Transaction;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Fixture {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  public static Transaction transaction() {
    Transaction transaction = new Transaction();
    transaction.setTransactionId(1001);
    transaction.setCustomerId("cust-001");
    transaction.setItemId("product-001");
    try {
      transaction.setTransactionDate(DATE_FORMAT.parse("2022-01-01"));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    transaction.setItemPrice(BigDecimal.valueOf(12.50).setScale(2, RoundingMode.UNNECESSARY));
    transaction.setItemQuantity(BigDecimal.valueOf(3));
    return transaction;
  }
}
