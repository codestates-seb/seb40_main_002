package main.project.server.guesthouse.repository;


import main.project.server.guesthouse.entity.GuestHouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestHouseRepository extends JpaRepository<GuestHouse, Long> {


}
