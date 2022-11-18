package main.project.server.member.entity;

import lombok.*;
import main.project.server.audit.Auditable;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberRegisterKind;
import main.project.server.member.entity.enums.MemberStatus;
import main.project.server.review.entity.Review;
import main.project.server.review.entity.ReviewComment;
import main.project.server.roomreservation.entity.RoomReservation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends Auditable {

    @Id
    @Column(unique = true)
    private String memberId;

    private String memberNickname;

    private String memberEmail;

    private String memberPhone;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    private String memberBirth;     // LocalDateTime -> String

    @Enumerated(EnumType.STRING)
    private MemberNationality memberNationality;

    @Enumerated(EnumType.STRING)
    private MemberRegisterKind memberRegisterKind;

    private String memberImageUrl;

    private String memberTags;

    @OneToMany(mappedBy = "member")
    private List<RoomReservation> roomReservations = new ArrayList<>();


    //    private List<String> memberRoles = new ArrayList<>(); //초기화 해주지 않으면 에러 발생
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> memberRoles;

    // 찜 추가 가능


    public static Member Member(String memberId) {
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ReviewComment> reviewComments = new ArrayList<>();

}
