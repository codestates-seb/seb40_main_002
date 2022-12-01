package main.project.server.roomreservation.entity;

import lombok.*;
import main.project.server.audit.Auditable;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.member.entity.Member;
import main.project.server.review.entity.Review;
import main.project.server.room.entity.Room;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class RoomReservation extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomReservationId;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "GUEST_HOUSE_ID")
    private GuestHouse guestHouse;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(nullable = false)
    private LocalDate roomReservationStart;

    @Column(nullable = false)
    private LocalDate roomReservationEnd;

    @Enumerated(EnumType.STRING)
    private RoomReservationStatus roomReservationStatus;

    @OneToOne(mappedBy = "roomReservation")
    private Review review;

    public void addGuestHouse(GuestHouse guestHouse) {
        this.guestHouse = guestHouse;
        if (!this.guestHouse.getRoomReservations().contains(this)) {
            this.guestHouse.getRoomReservations().add(this);
        }
    }

    public void addRoom(Room room) {
        this.room = room;
        if (!this.room.getRoomReservations().contains(this)) {
            this.room.getRoomReservations().add(this);
        }
    }

    public void addMember(Member member) {
        this.member = member;
        if (!this.member.getRoomReservations().contains(this)) {
            this.member.getRoomReservations().add(this);
        }
    }
}
