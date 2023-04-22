package com.tikrai.gmail.sales.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@lombok.Data
public class Transaction {

  public static final String DATE_PATTERN = "yyyy-MM-dd";
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);

  @CsvBindByName(column = "transaction_id")
  private int transactionId;

  @CsvBindByName(column = "customer_id")
  private String customerId;

  @CsvBindByName(column = "item_id")
  private String itemId;

  @CsvDate(value = DATE_PATTERN)
  @CsvBindByName(column = "transaction_date")
  private Date transactionDate;

  @CsvBindByName(column = "item_price")
  private BigDecimal itemPrice;

  @CsvBindByName(column = "item_quantity")
  private BigDecimal itemQuantity;

  public Transaction setTransactionDateString(String date) {
    try {
      this.transactionDate = Transaction.DATE_FORMAT.parse(date);
      return this;
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public String getTransactionDateString() {
    return DATE_FORMAT.format(transactionDate);
  }
}
