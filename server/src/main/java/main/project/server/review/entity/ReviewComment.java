package main.project.server.review.entity;

import lombok.*;
import main.project.server.audit.Auditable;
import main.project.server.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewComment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewCommentId;

    private String reviewComment;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne
    @JoinColumn(name = "REVIEW_ID")
    private Review review;  // 1:1 연관관계
}
