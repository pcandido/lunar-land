package com.paulocandido.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils {

    public static String readFile(String path) throws FileNotFoundException {
        var sc = new Scanner(new File(path));
        var sb = new StringBuilder();

        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
            sb.append("\n");
        }

        sc.close();
        return sb.toString();
    }

    public static void saveFile(String path, String content) throws IOException {
        var fw = new FileWriter(path);
        fw.write(content);
        fw.close();
    }

}
