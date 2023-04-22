package com.tikrai.gmail.sales;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.tikrai.gmail.sales.model.Transaction;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionSummaryTest {

  private TransactionSummary transactionSummary;
  Transaction transaction = Fixture.transaction();

  @BeforeEach
  void setup() {
    transactionSummary = new TransactionSummary();
    transaction.setItemPrice(BigDecimal.TEN);
    transaction.setItemQuantity(BigDecimal.valueOf(2));
  }

  @Test
  void acceptOneTransaction() {
    transactionSummary.accept(transaction);
    assertThat(transactionSummary.getTotalRevenue(), equalTo(BigDecimal.valueOf(20)));
  }

  @Test
  void acceptTwoTransactions() {
    transactionSummary.accept(transaction);
    transactionSummary.accept(transaction);
    assertThat(transactionSummary.getTotalRevenue(), equalTo(BigDecimal.valueOf(40)));
  }

  @Test
  void combineEmptySummaryWithOther() {
    TransactionSummary otherSummary = new TransactionSummary();
    otherSummary.accept(transaction);

    transactionSummary.combine(otherSummary);

    assertThat(this.transactionSummary.getTotalRevenue(), equalTo(BigDecimal.valueOf(20)));
  }

  @Test
  void combineSummaryWithOtherEmpty() {
    TransactionSummary otherSummary = new TransactionSummary();
    transactionSummary.accept(transaction);

    transactionSummary.combine(otherSummary);

    assertThat(this.transactionSummary.getTotalRevenue(), equalTo(BigDecimal.valueOf(20)));
  }

  @Test
  void combineSummaryWithOther() {
    TransactionSummary otherSummary = new TransactionSummary();
    otherSummary.accept(transaction);
    transactionSummary.accept(transaction);

    transactionSummary.combine(otherSummary);

    assertThat(this.transactionSummary.getTotalRevenue(), equalTo(BigDecimal.valueOf(40)));
  }
}
