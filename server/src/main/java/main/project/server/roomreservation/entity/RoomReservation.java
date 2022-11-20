package main.project.server.roomreservation.entity;

import lombok.*;
import main.project.server.audit.Auditable;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.member.entity.Member;
import main.project.server.roomreservation.entity.enums.RoomReservationStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "GUEST_HOUSE_ID")
    private GuestHouse guestHouse;

    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime roomReservationStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime roomReservationEnd;

    @Enumerated(EnumType.STRING)
    private RoomReservationStatus roomReservationStatus;
}
