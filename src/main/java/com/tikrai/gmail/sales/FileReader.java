package com.tikrai.gmail.sales;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileReader {

  public String read(String filename) {
    try {
      Path path = Paths.get(filename);
      return String.join("\n", Files.readAllLines(path));
    } catch (IOException e) {
      return readResources(filename);
    }
  }

  private String readResources(String fileName) {
    try (
        InputStream stream = Optional.ofNullable(Sales.class.getResourceAsStream(fileName))
            .orElseThrow(() -> new IllegalArgumentException("File " + fileName + " not found"));
        InputStreamReader inputReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(inputReader)
    ) {
      return reader.lines().collect(Collectors.joining("\n"));
    } catch (IllegalStateException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
