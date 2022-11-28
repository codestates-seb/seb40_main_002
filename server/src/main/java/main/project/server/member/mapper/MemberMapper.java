package main.project.server.member.mapper;

import main.project.server.member.dto.MemberDto;
import main.project.server.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostDtoToMember(MemberDto.Post memberPostDto);


    Member memberPatchDtoToMember(MemberDto.Patch memberPutDto);
    MemberDto.Response memberToMemberResponseDto(Member member);
}
