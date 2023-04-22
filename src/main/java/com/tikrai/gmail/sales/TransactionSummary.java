package com.tikrai.gmail.sales;

import com.tikrai.gmail.sales.model.Transaction;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class TransactionSummary {
  private BigDecimal totalRevenue = BigDecimal.ZERO;
  private final HashSet<String> customers = new HashSet<>();
  private final Map<String, BigDecimal> itemQuantities = new HashMap<>();
  private final Map<Date, BigDecimal> revenueByDate = new HashMap<>();

  /**
   * Adds a new value into the summary
   *
   * @param value the input value
   */
  public void accept(Transaction value) {
    BigDecimal revenue = value.getItemPrice().multiply(value.getItemQuantity());
    totalRevenue = totalRevenue.add(revenue);
    customers.add(value.getCustomerId());
    updateItemQuantities(value.getItemId(), value.getItemQuantity());
    updateRevenueByDate(value.getTransactionDate(), revenue);
  }

  /**
   * Combines the state of another {@code TransactionSummary} into this one.
   *
   * @param other another {@code TransactionSummary}
   * @throws NullPointerException if {@code other} is null
   */
  public TransactionSummary combine(TransactionSummary other) {
    totalRevenue = totalRevenue.add(other.totalRevenue);
    customers.addAll(other.customers);
    other.itemQuantities.forEach(this::updateItemQuantities);
    other.revenueByDate.forEach(this::updateRevenueByDate);
    return this;
  }

  private void updateItemQuantities(String itemId, BigDecimal itemQuantity) {
    itemQuantities.merge(itemId, itemQuantity, BigDecimal::add);
  }

  private void updateRevenueByDate(Date date, BigDecimal revenue) {
    revenueByDate.merge(date, revenue, BigDecimal::add);
  }

  public BigDecimal totalRevenue() {
    return totalRevenue;
  }

  public int uniqueCustomersCount() {
    return customers.size();
  }

  public String mostPopularItem() {
    return itemQuantities.entrySet().stream()
        .max(Entry.comparingByValue())
        .orElseThrow()
        .getKey();
  }

  public String bestDate() {
    Date bestDate = revenueByDate.entrySet().stream()
        .max(Entry.comparingByValue())
        .orElseThrow()
        .getKey();
    return Transaction.DATE_FORMAT.format(bestDate);
  }

  @Override
  public String toString() {
    return """
        ● Total Revenue: %s
        ● Unique Customers: %s
        ● Most Popular Item: %s
        ● Date with Highest Revenue: %s"""
        .formatted(totalRevenue, uniqueCustomersCount(), mostPopularItem(), bestDate());
  }

  public static Collector<Transaction, TransactionSummary, TransactionSummary> toSummary() {
    return new Collector<>() {
      @Override
      public Supplier<TransactionSummary> supplier() {
        return TransactionSummary::new;
      }

      @Override
      public BiConsumer<TransactionSummary, Transaction> accumulator() {
        return TransactionSummary::accept;
      }

      @Override
      public BinaryOperator<TransactionSummary> combiner() {
        return TransactionSummary::combine;
      }

      @Override
      public Function<TransactionSummary, TransactionSummary> finisher() {
        return x -> x;
      }

      @Override
      public Set<Characteristics> characteristics() {
        return Collections.emptySet();
      }
    };
  }
}
