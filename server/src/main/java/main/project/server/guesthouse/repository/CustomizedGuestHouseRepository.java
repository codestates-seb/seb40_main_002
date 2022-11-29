package main.project.server.guesthouse.repository;

import main.project.server.guesthouse.entity.GuestHouse;

import java.util.List;

public interface CustomizedGuestHouseRepository {

    List<GuestHouse> findAllGuestHouseWhereCityAndId();
}
