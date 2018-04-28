package se.karingotrafiken.timemanager.rest.utils;

import org.joda.time.DateTime;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;
import se.karingotrafiken.timemanager.rest.exceptions.ApiException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.MonthDay;
import java.util.Date;

public class DateTimeUtils {

    public static String toDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String toTimestring(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

    public static Date firstDayOfMonth(int year, int month) {
        checkValidMoth(month);
        DateTime dateTime = new DateTime(year, month, 1, 0, 0);
        return dateTime.toDate();
    }

    public static Date lastDayOfMonth(int year, int month) {
        checkValidMoth(month);
        DateTime dateTime = new DateTime(year, month, 1, 0, 0);
        dateTime = dateTime.plusMonths(1);
        return dateTime.minusDays(1).toDate();
    }

    public static Date firstDayOfYear(int year) {
        DateTime dateTime = new DateTime(year, 1, 1, 0, 0);
        return dateTime.toDate();
    }

    public static Date lastDayOfYear(int year) {
        DateTime dateTime = new DateTime(year, 12, 31, 0, 0);
        return dateTime.toDate();
    }

    public static Date dateFromDateString(String dateString) throws ApiException {
        try {
            return dateFormatter().parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_DATE_FORMAT, "Ogiiltigt datum format, måste vara på formen yyyy-MM-dd");
        }
    }

    public static Date dateFromTimeString(String timeString) {
        try {
            return timeFormatter().parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_TIME_FORMAT, "Ogiltigt tisaformat, måste vara på formen HH:mm");
        }
    }


    public static DateFormat dateFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static DateFormat timeFormatter() {
        return new SimpleDateFormat("HH:mm");
    }

    public static String weekDayFromDate(Date date) {
        return new DateTime(date).dayOfWeek().getAsText();
    }

    public static boolean isValidYEarAndMonth(int year, int month) {
        return year > 0 && month >= 1 && month <= 12;
    }

    public static boolean isValidMonthAndDay(int month, int day) {
        try {
            MonthDay monthDay = MonthDay.of(month, day);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    public static Date previousMonthAndDay(int month, int day) {
        return new DateTime(2018, month, day, 0, 0).toDate();
    }

    private static void checkValidMoth(int month) {
        if (month < 1 || month > 12)
            throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_MONTH_OF_YEAR, "Månad måste vara mellan 1 och 12");
    }

}
