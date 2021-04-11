package com.smoothstack.corejava.daythree;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Append given text to a predetermined file
 */
public class AppendFile {
    public static void main(String[] args) {
        try(FileWriter fw = new FileWriter("append.txt", true)) {
            fw.append((new Scanner(System.in)).nextLine()).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
