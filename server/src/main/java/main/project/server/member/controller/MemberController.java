package main.project.server.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.project.server.dto.SingleResponseDto;
import main.project.server.member.dto.MemberDto;
import main.project.server.member.entity.Member;
import main.project.server.member.mapper.MemberMapper;
import main.project.server.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    // 맴버 생성
    @PostMapping("api/members")
    public ResponseEntity postMember(@RequestBody MemberDto.Post memberPostDto){
        Member member = memberService.createMember(memberMapper.memberPostDtoToMember(memberPostDto));
        return new ResponseEntity<>(new SingleResponseDto<>("created", null), HttpStatus.CREATED);
    }


    // 맴버 정보 조회
    @GetMapping("/api/auth/members")
    public ResponseEntity getMember(Principal principal){
        Member member = memberService.findMember(principal.getName()).get();
        return new ResponseEntity<>(new SingleResponseDto<>(null, memberMapper.memberToMemberResponseDto(member)), HttpStatus.OK);
    }


    // 맴버 정보 수정
    @PatchMapping("/api/auth/members")
    public ResponseEntity patchMember(@RequestBody MemberDto.Patch memberPatchDto, Principal principal){
        memberPatchDto.setMemberId(principal.getName());
        Member member = memberService.patchMember(memberMapper.memberPatchDtoToMember(memberPatchDto));
        return new ResponseEntity<>(new SingleResponseDto<>("modified",null) , HttpStatus.OK);
    }


    // 맴버 삭제
    @DeleteMapping("/api/auth/members")
    public ResponseEntity deleteMember(){

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
