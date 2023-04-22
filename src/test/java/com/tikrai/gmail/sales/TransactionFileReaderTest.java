package com.tikrai.gmail.sales;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tikrai.gmail.sales.model.Transaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionFileReaderTest {

  TransactionFileReader reader = new TransactionFileReader();

  @Test
  void readsProvidedFile() {
    List<Transaction> transactions = reader.read("src/test/resources/transaction.csv");
    assertThat(transactions, contains(Fixture.transaction()));
  }

  @Test
  void readsProvidedFileFromResources() {
    List<Transaction> transactions = reader.read("/transaction.csv");
    assertThat(transactions, contains(Fixture.transaction()));
  }

  @Test
  void failToReadWhenFileNotAvailable() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        reader.read("/no.file"));
    assertThat(exception.getMessage(), equalTo("File /no.file not found"));
  }
}
