package main.project.server.guesthouse.all;


import main.project.server.city.repository.CityRepository;
import main.project.server.guesthouse.repository.GuestHouseRepository;
import main.project.server.guesthouse.room.repository.RoomRepository;
import main.project.server.member.repository.MemberRepository;
import main.project.server.roomreservation.repository.RoomReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class AllGuestHouseTests {


    @Autowired
    CityRepository cityRepository;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GuestHouseRepository guestHouseRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomReservationRepository roomReservationRepository;





}
