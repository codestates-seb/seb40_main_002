package main.project.server.guesthousedetails.entity;


import lombok.*;
import main.project.server.guesthouse.entity.GuestHouse;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestHouseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestHouseDetailsId;

    @OneToOne
    @JoinColumn(name = "GUEST_HOUSE_ID")
    private GuestHouse guestHouse;

    private Boolean guestHouseParty;

    private Boolean guestHouseKitchen;

    private Boolean guestHouseWashing;

    private Boolean guestHouseOcean;

    private Boolean guestHouseTask;

    private Boolean guestHouseEssential;

    private Boolean guestHouseWifi;

    private Boolean guestHouseBoard;

    private Boolean guestHouseCook;

    private Boolean guestHouseParking;

}
