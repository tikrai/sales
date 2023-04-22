package com.tikrai.gmail.sales;

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
}
