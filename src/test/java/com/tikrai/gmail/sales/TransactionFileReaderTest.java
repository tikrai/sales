package com.tikrai.gmail.sales;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tikrai.gmail.sales.model.Transaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionFileReaderTest {

  @Test
  void readsProvidedFile() {
    List<Transaction> txList = TransactionFileReader.read("src/test/resources/transaction.csv");
    assertThat(txList, contains(Fixture.transaction()));
  }

  @Test
  void readsProvidedFileFromResources() {
    List<Transaction> txList = TransactionFileReader.read("/transaction.csv");
    assertThat(txList, contains(Fixture.transaction()));
  }

  @Test
  void failToReadWhenFileNotAvailable() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        TransactionFileReader.read("/no.file"));
    assertThat(exception.getMessage(), equalTo("File /no.file not found"));
  }
}
