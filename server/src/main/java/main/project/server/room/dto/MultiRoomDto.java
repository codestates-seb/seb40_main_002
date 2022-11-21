package main.project.server.room.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultiRoomDto<T> {

    List<T> roomDto;

}
