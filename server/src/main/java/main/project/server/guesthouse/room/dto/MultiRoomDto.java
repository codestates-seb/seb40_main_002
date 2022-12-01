package main.project.server.guesthouse.room.dto;

import lombok.*;

import javax.validation.Valid;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultiRoomDto<T> {
    @Valid
    List<T> roomDto;

}
