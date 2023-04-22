package com.tikrai.gmail.sales;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tikrai.gmail.sales.model.Transaction;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionFileReaderTest {

  TransactionFileReader transactionFileReader = new TransactionFileReader();
  private final Transaction expected = new Transaction();

  @BeforeEach
  void setExpected() throws ParseException {
    expected.setTransactionId(1001);
    expected.setCustomerId("cust-001");
    expected.setItemId("product-001");
    expected.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
    expected.setItemPrice(BigDecimal.valueOf(12.50).setScale(2, RoundingMode.UNNECESSARY));
    expected.setItemQuantity(BigDecimal.valueOf(3));
  }

  @Test
  void readsProvidedFile() {
    List<Transaction> transactions = transactionFileReader.read("src/test/resources/transaction.csv");
    assertThat(transactions, contains(expected));
  }

  @Test
  void readsProvidedFileFromResources() {
    List<Transaction> transactions = transactionFileReader.read("/transaction.csv");
    assertThat(transactions, contains(expected));
  }

  @Test
  void failToReadWhenFileNotAvailable() {

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        transactionFileReader.read("/no.file"));
    assertThat(exception.getMessage(), equalTo("File /no.file not found"));
  }
}
