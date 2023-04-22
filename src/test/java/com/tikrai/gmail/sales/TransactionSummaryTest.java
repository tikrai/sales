package com.tikrai.gmail.sales;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.tikrai.gmail.sales.model.Transaction;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionSummaryTest {

  private TransactionSummary transactionSummary;
  Transaction tx1 = Fixture.transaction();
  Transaction tx2 = Fixture.transaction();

  @BeforeEach
  void setup() {
    transactionSummary = new TransactionSummary();

    tx1.setCustomerId("Customer1");
    tx1.setItemId("Item1");
    tx1.setItemPrice(BigDecimal.TEN);
    tx1.setItemQuantity(BigDecimal.valueOf(2));

    tx1.setCustomerId("Customer1");
    tx1.setItemId("Item2");
    tx2.setItemPrice(BigDecimal.valueOf(5));
    tx2.setItemQuantity(BigDecimal.valueOf(3));
  }

  @Test
  void acceptOneTransaction() {
    transactionSummary.accept(tx1);
    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(20)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(1));
    assertThat(transactionSummary.mostPopularItem(), equalTo(tx1.getItemId()));
  }

  @Test
  void acceptTwoTransactions() {
    transactionSummary.accept(tx1);
    transactionSummary.accept(tx2);
    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(35)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(2));
    assertThat(transactionSummary.mostPopularItem(), equalTo(tx2.getItemId()));
  }

  @Test
  void combineEmptySummaryWithOther() {
    TransactionSummary otherSummary = new TransactionSummary();
    otherSummary.accept(tx2);

    transactionSummary.combine(otherSummary);

    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(15)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(1));
    assertThat(transactionSummary.mostPopularItem(), equalTo(tx2.getItemId()));
  }

  @Test
  void combineSummaryWithOtherEmpty() {
    transactionSummary.accept(tx1);

    transactionSummary.combine(new TransactionSummary());

    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(20)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(1));
    assertThat(transactionSummary.mostPopularItem(), equalTo(tx1.getItemId()));
  }

  @Test
  void combineSummaryWithOther() {
    TransactionSummary otherSummary = new TransactionSummary();
    otherSummary.accept(tx2);
    transactionSummary.accept(tx1);

    transactionSummary.combine(otherSummary);

    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(35)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(2));
    assertThat(transactionSummary.mostPopularItem(), equalTo(tx2.getItemId()));
  }
}
