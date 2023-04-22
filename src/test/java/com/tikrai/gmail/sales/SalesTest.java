package com.tikrai.gmail.sales;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SalesTest {

  @Test
  void throwsExpectedException() {
    RuntimeException exception = assertThrows(RuntimeException.class, Sales::main);
    assertThat(exception.getMessage(), equalTo("Not implemented"));
  }

}
