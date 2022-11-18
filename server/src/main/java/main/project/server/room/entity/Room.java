package main.project.server.room.entity;

import lombok.*;
import main.project.server.audit.Auditable;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.room.entity.enums.RoomStatus;
import main.project.server.room.service.RoomService;
import main.project.server.roomreservation.entity.RoomReservation;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToOne
    @JoinColumn(name = "GUEST_HOUSE_ID")
    private GuestHouse guestHouse;

    private String roomName;

    private int roomPrice;

    private String roomImageUrl;

    private String roomInfo;

    private int roomCapacity;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomReservation> roomReservations = new ArrayList<>();

    public static Room Room(Long roomId) {
        Room room = new Room();
        room.setRoomId(roomId);
        return room;
    }

    public void addGuestHouse(GuestHouse guestHouse) {
        this.guestHouse = guestHouse;
        if (!this.guestHouse.getRooms().contains(this)) {
            this.guestHouse.getRooms().add(this);
        }
    }

}
