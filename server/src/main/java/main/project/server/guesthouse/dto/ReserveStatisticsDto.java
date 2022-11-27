package main.project.server.guesthouse.dto;

import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReserveStatisticsDto {

    String dt;

    BigInteger reserveCount;

}
