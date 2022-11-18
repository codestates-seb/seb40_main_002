package main.project.server.guesthouseroom.entity;

import lombok.*;
import main.project.server.guesthouse.entity.GuestHouse;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestHouseRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestHouseRoomId;

    @ManyToOne
    @JoinColumn(name = "GUEST_HOUSE_ID")
    private GuestHouse guestHouse;
}

