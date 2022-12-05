package main.project.server.guesthouse.stub;

import main.project.server.city.entity.City;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.entity.enums.GuestHouseStatus;
import main.project.server.member.entity.Member;

public class GuestHouseStub {

    public static GuestHouse getNewGuestHouse(City city, Member member) {

        return GuestHouse.builder().guestHouseName("테스트게하1").city(city).member(member).guestHouseStatus(GuestHouseStatus.OPEN).build();
    }
}
