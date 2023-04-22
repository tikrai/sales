package com.tikrai.gmail.sales;

import static com.tikrai.gmail.sales.TransactionSummary.toSummary;

public class Sales {

  public static void main(String... args) {
    String fileName = args.length > 0 ? args[0] : "/input.csv";
    System.out.println(TransactionFileReader.read(fileName).stream().collect(toSummary()));
  }
}
