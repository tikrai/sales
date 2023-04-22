package com.tikrai.gmail.sales;

import com.tikrai.gmail.sales.model.Transaction;
import java.math.BigDecimal;
import java.util.HashSet;

public class TransactionSummary {
  private BigDecimal totalRevenue = BigDecimal.ZERO;
  private final HashSet<String> customers = new HashSet<>();

  /**
   * Adds a new value into the summary
   *
   * @param value the input value
   */
  public void accept(Transaction value) {
    totalRevenue = totalRevenue.add(value.getItemPrice().multiply(value.getItemQuantity()));
    customers.add(value.getCustomerId());
  }

  /**
   * Combines the state of another {@code TransactionSummary} into this one.
   *
   * @param other another {@code TransactionSummary}
   * @throws NullPointerException if {@code other} is null
   */
  public void combine(TransactionSummary other) {
    totalRevenue = totalRevenue.add(other.totalRevenue);
    customers.addAll(other.customers);
  }

  public BigDecimal totalRevenue() {
    return totalRevenue;
  }

  public int uniqueCustomersCount() {
    return customers.size();
  }

  @Override
  public String toString() {
    return """
        ● Total Revenue: %s
        ● Unique Customers: %s
        ● Most Popular Item: %s
        ● Date with Highest Revenue: %s"""
        .formatted(totalRevenue, uniqueCustomersCount(), "not calculated", "not calculated");
  }
}
