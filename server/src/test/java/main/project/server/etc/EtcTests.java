package main.project.server.etc;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.IntStream;

public class EtcTests {

    @Test
    void streamTest() {

        StringBuilder stringBuilder = new StringBuilder();

        String[] addressArr = new String[4];
        IntStream.range(0, addressArr.length)
                .forEach(index-> {
                    stringBuilder.append(addressArr[index]);
                    if (index != addressArr.length - 1) {
                        stringBuilder.append(",");
                    }
                });


        System.out.println(stringBuilder);
        System.out.println("성공");
    }

    @Test
    void dateValidate() {

        String date = "20220124";

        String format = "yyyy-MM-dd";


        SimpleDateFormat sdf = new SimpleDateFormat(format);

        sdf.setLenient(false);
        try {
            Date parse = sdf.parse(date);
            System.out.println(parse);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

}
