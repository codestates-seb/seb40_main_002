package main.project.server.room.entity;

import lombok.*;
import main.project.server.audit.Auditable;
import main.project.server.room.entity.enums.RoomStatus;
import main.project.server.room.service.RoomService;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Room extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String roomName;

    private int price;

    private String roomImageUrl;

    private String roomInfo;

    private int roomCapacity;

    private RoomStatus roomStatus;

//    private Long guestHouseId;
}
