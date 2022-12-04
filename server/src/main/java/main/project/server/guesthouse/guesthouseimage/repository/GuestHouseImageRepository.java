package main.project.server.guesthouse.guesthouseimage.repository;

import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthouse.guesthouseimage.entity.GuestHouseImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestHouseImageRepository extends JpaRepository<GuestHouseImage, Long> {

    void deleteAllByGuestHouse(GuestHouse guestHouse);
}
