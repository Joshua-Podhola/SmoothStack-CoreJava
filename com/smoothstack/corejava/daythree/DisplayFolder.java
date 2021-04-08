package com.smoothstack.corejava.daythree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Print the contents of a given directory (non-recursive)
 */
public class DisplayFolder {
    public static void main(String[] args) {
        System.out.println("Enter a directory to check:");
        Scanner s = new Scanner(System.in);
        String path = s.nextLine();
        try {
            for(Object o: Files.list(new File(path).toPath()).toArray()) {
                //Cast is safe: object is always of type Path
                System.out.println(((Path)o).getFileName());
            }
        } catch (IOException e) {
            System.out.println("That directory does not exist.");
        }
    }
}
