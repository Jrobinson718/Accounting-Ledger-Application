package com.pluralsight;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {
    //Calculates the start and end date for the current Month-Date period
    public static LocalDate[] getMonthToDateRange(LocalDate today){
        LocalDate startOfMonth = today.withDayOfMonth(1);
        return new LocalDate[]{startOfMonth,today};
    }

    // Calculates the start and end date for the entirety of last month
    public static LocalDate[] getPreviousMonthRange(LocalDate today){
        LocalDate prevMonth = today.minusMonths(1);
        LocalDate startOfPrevMonth = prevMonth.withDayOfMonth(1);
        LocalDate endOfPrevMonth = prevMonth.with(TemporalAdjusters.lastDayOfMonth());

        return new LocalDate[]{startOfPrevMonth, endOfPrevMonth};
    }

    // Calculated the start and end date for the Year-Date period
    public static LocalDate[] getYearToDateRange(LocalDate today) {
        LocalDate startOfYear = today.withDayOfYear(1);
        return new LocalDate[]{startOfYear, today};
    }

    // Calculates the start and end date for the entirety of last year
    public static LocalDate[] getPrevYearRange(LocalDate today) {
        LocalDate prevYear = today.minusYears(1);
        LocalDate startOfPrevYear = prevYear.withDayOfYear(1);
        LocalDate endOfPrevYear = prevYear.with(TemporalAdjusters.lastDayOfYear());
        return new LocalDate[]{startOfPrevYear, endOfPrevYear};
    }
}
