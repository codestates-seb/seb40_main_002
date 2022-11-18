package main.project.server.roomreservation.entity;

import lombok.*;
import main.project.server.audit.Auditable;
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

    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private String memberId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime roomReservationStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime roomReservationEnd;

    private RoomReservationStatus roomReservationStatus;
}
