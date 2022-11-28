package main.project.server.member.mapper;

import main.project.server.member.dto.MemberDto;
import main.project.server.member.entity.Member;
import main.project.server.member.entity.enums.MemberNationality;
import main.project.server.member.entity.enums.MemberRegisterKind;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostDtoToMember(MemberDto.Post memberPostDto);


    Member memberPatchDtoToMember(MemberDto.Patch memberPutDto);

    MemberDto.Response memberToMemberResponseDto(Member member);
//    default MemberDto.Response memberToMemberResponseDto(Member member) {
//        MemberDto.Response.builder()
//                .memberId(member.getMemberId())
//                .memberNickname(member.getMemberNickname())
//                .memberEmail(member.getMemberEmail())
//                .memberPhone(member.getMemberPhone())
//                .memberBirth(member.getMemberBirth())
//                .memberNationality(member.getMemberNationality())
//                .memberRegisterKind(member.getMemberRegisterKind())
//                .memberImageUrl(member.getMemberImageUrl())
//                .memberRole(member.getMemberRoles())
//                .memberTag(member.getMemberTags())
//
//    }
}
