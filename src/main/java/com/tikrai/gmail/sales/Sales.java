package com.tikrai.gmail.sales;

public class Sales {

  public static void main(String... args) {
    String fileName = args.length > 0 ? args[0] : "/input.csv";
    TransactionSummary summary = new TransactionFileReader().read(fileName).stream()
        .collect(TransactionSummary::new, TransactionSummary::accept, TransactionSummary::combine);
    System.out.println(summary);
  }
}
