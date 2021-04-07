package com.smoothstack.corejava.daytwo;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Find the highest number in a 2D array
 */
public class HighestIn2D {
    public static void main(String[] args) {
        //No instructions as to how to construct the 2D array; make a random 5x5
        ThreadLocalRandom randomizer = ThreadLocalRandom.current();
        int[][] arr = new int[5][5];

        for(int x = 0; x != 5; x++) {
            for(int y = 0; y != 5; y++) {
                arr[x][y] = randomizer.nextInt(0, 100);
            }
        }
        //Print the array out
        for(int[] firstdim: arr) {
            StringBuilder str = new StringBuilder();
            str.append("[");
            for(int seconddim: firstdim) str.append(seconddim).append(", ");
            str.append("...]");
            System.out.println(str.toString());
        }

        //Algorithm
        int lowest_x = 0, lowest_y = 0, lowest_val = 0;
        for(int x = 0; x != 5; x++) {
            for(int y = 0; y != 5; y++) {
                if(arr[x][y] > lowest_val) {
                    lowest_val = arr[x][y];
                    lowest_x = x;
                    lowest_y = y;
                }
            }
        }

        System.out.printf("The lowest value is %d from %d, %d%n", lowest_val, lowest_x, lowest_y);
    }
}
