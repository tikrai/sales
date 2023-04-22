package com.tikrai.gmail.sales;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

class FileReaderTest {

  @Test
  void readsProvidedFile() {
    String read = new FileReader().read("settings.gradle");
    assertThat(read, equalTo("rootProject.name = 'sales'"));
  }

  @Test
  void readsProvidedFileFromResources() {
    String read = new FileReader().read("/justsomething.txt");
    assertThat(read, equalTo("First line\nSecond line"));
  }
}
