package main.project.server.heart.entity;

import lombok.*;
import main.project.server.audit.Auditable;
import main.project.server.guesthouse.entity.GuestHouse;
import main.project.server.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Heart extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heartId;

    @Column
    private Boolean heartStatus;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "GUEST_HOUSE_ID")
    private GuestHouse guestHouse;
}
