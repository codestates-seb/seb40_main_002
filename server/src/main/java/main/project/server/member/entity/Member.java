package main.project.server.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.project.server.audit.Auditable;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberRegisterKind;
import main.project.server.member.entity.enums.MemberStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String memberName;

    private String memberPassword;

    private String memberEmail;

    private String memberPhone;

    private MemberStatus memberStatus;

    private LocalDateTime memberBirth;

    private MemberNationality memberNationality;

    private MemberRegisterKind memberRegisterKind;

    private String memberImageUrl;

    private String memberTags;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> memberRoles;

    // 찜 추가 가능
}
