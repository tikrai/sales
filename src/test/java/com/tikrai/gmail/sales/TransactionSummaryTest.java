package com.tikrai.gmail.sales;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.tikrai.gmail.sales.model.Transaction;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionSummaryTest {

  private TransactionSummary transactionSummary;
  Transaction transaction1 = Fixture.transaction();
  Transaction transaction2 = Fixture.transaction();

  @BeforeEach
  void setup() {
    transactionSummary = new TransactionSummary();

    transaction1.setCustomerId("first");
    transaction1.setItemPrice(BigDecimal.TEN);
    transaction1.setItemQuantity(BigDecimal.valueOf(2));

    transaction1.setCustomerId("second");
    transaction2.setItemPrice(BigDecimal.valueOf(5));
    transaction2.setItemQuantity(BigDecimal.valueOf(3));
  }

  @Test
  void acceptOneTransaction() {
    transactionSummary.accept(transaction1);
    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(20)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(1));
  }

  @Test
  void acceptTwoTransactions() {
    transactionSummary.accept(transaction1);
    transactionSummary.accept(transaction2);
    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(35)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(2));
  }

  @Test
  void combineEmptySummaryWithOther() {
    TransactionSummary otherSummary = new TransactionSummary();
    otherSummary.accept(transaction2);

    transactionSummary.combine(otherSummary);

    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(15)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(1));
  }

  @Test
  void combineSummaryWithOtherEmpty() {
    transactionSummary.accept(transaction1);

    transactionSummary.combine(new TransactionSummary());

    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(20)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(1));
  }

  @Test
  void combineSummaryWithOther() {
    TransactionSummary otherSummary = new TransactionSummary();
    otherSummary.accept(transaction2);
    transactionSummary.accept(transaction1);

    transactionSummary.combine(otherSummary);

    assertThat(transactionSummary.totalRevenue(), equalTo(BigDecimal.valueOf(35)));
    assertThat(transactionSummary.uniqueCustomersCount(), equalTo(2));
  }
}
