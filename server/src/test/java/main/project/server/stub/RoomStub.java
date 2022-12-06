package main.project.server.stub;

import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.room.entity.Room;
import main.project.server.roomreservation.entity.RoomReservation;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;

public class RoomStub {

    public static Room getRoom(GuestHouse guestHouse) {
        return Room.builder()
                .guestHouse(guestHouse)
                .build();

    }
}
