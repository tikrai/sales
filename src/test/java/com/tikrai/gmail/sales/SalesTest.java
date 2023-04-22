package com.tikrai.gmail.sales;

import static com.tikrai.gmail.sales.TransactionSummary.toSummary;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

class SalesTest {

  @Test
  void throwsNoException() {
    Sales.main();
  }

  @Test
  void throwsNoExceptionWithParameters() {
    Sales.main("/input.csv");
  }

  @Test
  void calculatesSampleCaseCorrectly() {
    String summary = TransactionFileReader.read("/input.csv")
        .parallelStream()
        .collect(toSummary())
        .toString();
    assertThat(summary, equalTo("""
        ● Total Revenue: 573.39
        ● Unique Customers: 20
        ● Most Popular Item: product-004
        ● Date with Highest Revenue: 2022-01-05"""));
  }
}
