package com.tikrai.gmail.sales;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.tikrai.gmail.sales.model.Transaction;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionSummaryTest {

  private final Transaction tx1 = Fixture.transaction()
      .setCustomerId("Customer1")
      .setItemId("Item1")
      .setTransactionDateString("2023-04-21")
      .setItemPrice(BigDecimal.TEN)
      .setItemQuantity(BigDecimal.valueOf(2));
  private final Transaction tx2 = Fixture.transaction()
      .setCustomerId("Customer2")
      .setItemId("Item2")
      .setTransactionDateString("2023-04-22")
      .setItemPrice(BigDecimal.valueOf(5))
      .setItemQuantity(BigDecimal.valueOf(3));

  private TransactionSummary transactionSummary;

  @BeforeEach
  void setup() {
    transactionSummary = new TransactionSummary();
  }

  @Test
  void acceptOneTransaction() {
    transactionSummary.accept(tx1);
    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(20)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(1));
    assertThat(transactionSummary.mostPopularItem(), equalTo(tx1.getItemId()));
    assertThat(transactionSummary.bestDate(), equalTo(tx1.getTransactionDateString()));
  }

  @Test
  void acceptTwoTransactions() {
    transactionSummary.accept(tx1);
    transactionSummary.accept(tx2);
    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(35)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(2));
    assertThat(transactionSummary.mostPopularItem(), equalTo(tx2.getItemId()));
    assertThat(transactionSummary.bestDate(), equalTo(tx1.getTransactionDateString()));
  }

  @Test
  void combineEmptySummaryWithOther() {
    TransactionSummary otherSummary = new TransactionSummary();
    otherSummary.accept(tx2);

    transactionSummary.combine(otherSummary);

    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(15)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(1));
    assertThat(transactionSummary.mostPopularItem(), equalTo(tx2.getItemId()));
    assertThat(transactionSummary.bestDate(), equalTo(tx2.getTransactionDateString()));
  }

  @Test
  void combineSummaryWithOtherEmpty() {
    transactionSummary.accept(tx1);

    transactionSummary.combine(new TransactionSummary());

    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(20)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(1));
    assertThat(transactionSummary.mostPopularItem(), equalTo(tx1.getItemId()));
    assertThat(transactionSummary.bestDate(), equalTo(tx1.getTransactionDateString()));
  }

  @Test
  void combineSummaryWithOther() {
    TransactionSummary otherSummary = new TransactionSummary();
    otherSummary.accept(tx2);
    transactionSummary.accept(tx1);
    transactionSummary.accept(tx2);

    transactionSummary.combine(otherSummary);

    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(50)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(2));
    assertThat(transactionSummary.mostPopularItem(), equalTo(tx2.getItemId()));
    assertThat(transactionSummary.bestDate(), equalTo(tx2.getTransactionDateString()));
  }
}
