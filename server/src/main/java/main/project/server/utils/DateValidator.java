package main.project.server.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateValidator {
    static String basicFormat = "yyyy-MM-dd";
    static SimpleDateFormat sdf = new SimpleDateFormat(basicFormat);

    public static Boolean startEndValidate(String start, String end) {

        sdf.setLenient(false);

        try {
            sdf.parse(start);
            sdf.parse(end);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
