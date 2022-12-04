package main.project.server.guesthouse.room.entity;

import lombok.*;
import main.project.server.audit.Auditable;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.room.entity.enums.RoomStatus;
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
public class Room extends Auditable implements Comparable<Room>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "GUEST_HOUSE_ID")
    private GuestHouse guestHouse;
    @Column(nullable = false)
    private String roomName;
    @Column(nullable = false)
    private int roomPrice;
    @Column(nullable = false)
    private String roomImageUrl;
    @Column(nullable = false)
    private String roomInfo;

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

    @Override
    public int compareTo(Room o) {
        return this.roomPrice < o.roomPrice ? -1 : (this.roomPrice == o.roomPrice ? 0 : 1);
    }
}
