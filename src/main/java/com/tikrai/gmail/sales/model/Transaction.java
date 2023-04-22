package com.tikrai.gmail.sales.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.math.BigDecimal;
import java.util.Date;

@lombok.Data
public class Transaction {

  @CsvBindByName(column = "transaction_id")
  private int transactionId;

  @CsvBindByName(column = "customer_id")
  private String customerId;

  @CsvBindByName(column = "item_id")
  private String itemId;

  @CsvDate(value = "yyyy-MM-dd")
  @CsvBindByName(column = "transaction_date")
  private Date transactionDate;

  @CsvBindByName(column = "item_price")
  private BigDecimal itemPrice;

  @CsvBindByName(column = "item_quantity")
  private BigDecimal itemQuantity;
}
