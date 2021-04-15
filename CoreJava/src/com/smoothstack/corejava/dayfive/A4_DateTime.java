package com.smoothstack.corejava.dayfive;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class A4_DateTime {
    //1. You'd use java.time.LocalDateTime
    //2. Use LocalDate.with(TemporalAdjusters.previous(DayOfWeek.THURSDAY));

    //3. ZoneId is an abstract class representing a timezone, and ZoneOffset is a class representing the difference
    //   between a timezone and UTC time

    //4. Use ZonedDateTime.ofInstant(Instant instant, ZoneId Zone) to convert an Instant to a ZonedDateTime
    //   You can also use Instant.atZone(ZoneId zone) to convert an Instant to a ZonedDateTime
    //   You can use ZonedDateTime.toInstant() (inherited from ChronoZonedDateTime) to convert it back.

    //   5-7 given as methods below.

    /**
     * Return an array of integers representing the length of each month in days given a year
     * @param year The year to operate on
     * @return An array of integers of number of days in given month (index 0-11)
     */
    public static int[] monthLengths(int year) {
        LocalDate currentyear = LocalDate.of(year, 1, 1);
        int[] lengths = new int[12];
        for(int i = 1; i <= 12; i++) {
            lengths[i-1] = currentyear.getMonth().length(currentyear.isLeapYear());
            currentyear = currentyear.plusMonths(1);
        }

        return lengths;
    }

    /**
     * List all mondays of the given month in the current year
     * @param month The Month to use
     * @return A list of LocalDates of all Mondays in the month
     */
    public static LocalDate[] listMondays(Month month) {
        LocalDate currentMonday = LocalDate.now().withMonth(month.getValue()).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        LocalDate[] dates = new LocalDate[5];
        int i = 0;
        do {
            dates[i] = currentMonday;
            currentMonday = currentMonday.plusDays(7);
            i++;
        } while(currentMonday.getMonth() == month);

        return dates;
    }

    /**
     * Tests whether the given date is Friday the 13th
     * @param date The date
     * @return Whether Freddy is Ready
     */
    public static boolean noFreddyNo(LocalDate date) {
        return date.getDayOfMonth() == 13 && date.getDayOfWeek() == DayOfWeek.FRIDAY;
    }
}
