package com.tikrai.gmail.sales;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.tikrai.gmail.sales.model.Transaction;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public class TransactionFileReader {

  public static List<Transaction> read(String fileName) {
    try (
        InputStream stream = getInputStream(fileName);
        InputStreamReader inputReader = new InputStreamReader(stream, UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        TransactionCsvReader csvReader = new TransactionCsvReader(bufferedReader)
    ) {
      return csvReader.readTransactions();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static InputStream getInputStream(String fileName) {
    try {
      return new FileInputStream(fileName);
    } catch (FileNotFoundException e) {
      return Optional.ofNullable(Sales.class.getResourceAsStream(fileName))
          .orElseThrow(() -> new IllegalArgumentException("File " + fileName + " not found"));
    }
  }
}
