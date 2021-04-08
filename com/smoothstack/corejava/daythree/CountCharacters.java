package com.smoothstack.corejava.daythree;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Count characters in a given file using command line arguments
 */
public class CountCharacters {
    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Invalid arguments. Usage: <path> <character>");
            return;
        }
        try(FileReader fr = new FileReader(args[0])) {
            char c = args[1].toCharArray()[0]; //If multiple characters, just pick the first
            int read;
            long counter = 0;
            do {
                read = fr.read();
                if(c == read) counter++;
            } while(read != -1);
            System.out.printf("%c appears %d times.", c, counter);
        } catch (FileNotFoundException e) {
            System.out.println("That file does not exist.");
        } catch (IOException e) {
            System.out.println("Couldn't read that file.");
        }
    }
}
