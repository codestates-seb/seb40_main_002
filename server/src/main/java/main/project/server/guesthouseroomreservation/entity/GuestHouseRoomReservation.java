package main.project.server.guesthouseroomreservation.entity;

import lombok.*;
import main.project.server.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class GuestHouseRoomReservation extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestHouseRoomReservationId;

    private Long guestHouseRoomId;

    private String memberId;

    private LocalDateTime guestHouseRoomReservationStart;
    private LocalDateTime guestHouseRoomReservationEnd;
}
