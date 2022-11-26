package main.project.server.etc;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

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

}
