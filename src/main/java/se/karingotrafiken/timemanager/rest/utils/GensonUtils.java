package se.karingotrafiken.timemanager.rest.utils;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GensonUtils {

    public static Genson dateFormatedGenson() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        return new GensonBuilder().useDateFormat(dateFormat).useDateAsTimestamp(false).create();
    }

    public static Genson timeFormattedGenson() {
        DateFormat format = new SimpleDateFormat("HH:mm");
//        format.setLenient(false);
        return new GensonBuilder().useDateFormat(format).useDateAsTimestamp(false).create();
    }
}