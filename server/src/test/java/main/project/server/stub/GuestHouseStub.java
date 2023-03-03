package main.project.server.stub;

import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.room.entity.Room;

public class GuestHouseStub {

    public static GuestHouse getGuestHouse() {
        
        return GuestHouse.builder().build();
    }
}
