package com.tikrai.gmail.sales;

import com.opencsv.bean.CsvToBeanBuilder;
import com.tikrai.gmail.sales.model.Transaction;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class TransactionCsvReader extends Reader {

  private final Reader in;

  public TransactionCsvReader(Reader in) {
    super(in);
    this.in = in;
  }

  public List<Transaction> readTransactions() {
    return new CsvToBeanBuilder<Transaction>(in).withType(Transaction.class).build().parse();
  }

  @Override
  public int read(char[] cbuf, int off, int len) throws IOException {
    return in.read(cbuf, off, len);
  }

  @Override
  public void close() throws IOException {
    in.close();
  }
}
