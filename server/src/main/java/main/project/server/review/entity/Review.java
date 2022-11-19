package main.project.server.review.entity;

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
public class Review extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "GUEST_HOUSE_ID")
    private GuestHouse guestHouse;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private float star;

    @OneToOne(mappedBy = "review", cascade = CascadeType.ALL)
    @JoinColumn
    private ReviewComment reviewComment;

}
