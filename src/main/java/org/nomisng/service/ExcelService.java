package org.nomisng.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ExcelService {

    <T> void writeToExcelMultipleSheet(final String fileName, final String sheetName,
                                              final List<T> data);

    <T> void writeToExcel();

    <T> void readExcelFile(String fileName) throws IOException;

    String capitalize(String text);

    ByteArrayOutputStream downloadFile(String file);
    Set<String> listFiles();
}
