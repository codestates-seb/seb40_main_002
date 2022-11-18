package main.project.server.guesthousedetails.repository;

import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.guesthousedetails.entity.GuestHouseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface GuestHouseDetailsRepository extends JpaRepository<GuestHouseDetails, Long> {


}
