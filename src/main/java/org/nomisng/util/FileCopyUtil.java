package org.nomisng.util;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;

public class FileCopyUtil {

    public static void copyFile(File from, File to)
            throws IOException {
        try (
                InputStream in = new BufferedInputStream(
                        new FileInputStream(from));
                OutputStream out = new BufferedOutputStream(
                        new FileOutputStream(to))) {

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }

    }

    public static String readFromInputStream(InputStream inputStream) throws IOException{
        StringBuilder resultStringBuilder = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) !=null) {
                resultStringBuilder.append(line).append("\n");
            }
        } finally {
            if(inputStream != null) {
                try{
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultStringBuilder.toString();
    }
}
