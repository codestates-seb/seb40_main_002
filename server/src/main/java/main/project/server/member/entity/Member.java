package main.project.server.member.entity;

import lombok.*;
import main.project.server.audit.Auditable;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberRegisterKind;
import main.project.server.member.entity.enums.MemberStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    private MemberStatus memberStatus;

    private String memberBirth;     // LocalDateTime -> String

    private MemberNationality memberNationality;

    private MemberRegisterKind memberRegisterKind;

    private String memberImageUrl;

    private String memberTags;


    //    private List<String> memberRoles = new ArrayList<>(); //초기화 해주지 않으면 에러 발생
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> memberRoles;

    // 찜 추가 가능
}
