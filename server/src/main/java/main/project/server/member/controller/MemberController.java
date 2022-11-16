//package main.project.server.member.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import main.project.server.member.dto.MemberDto;
//import main.project.server.member.entity.Member;
//import main.project.server.member.mapper.MemberMapper;
//import main.project.server.member.service.MemberService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@Validated
//@Slf4j
//@RequiredArgsConstructor
//public class MemberController {
//    private final MemberService memberService;
//    private final MemberMapper memberMapper;
//
//    // 맴버 생성
//    @PostMapping("api/members")
//    public ResponseEntity postMember(@RequestBody MemberDto.Post memberPostDto){
//        Member member = memberService.createMember();
//
//        return new ResponseEntity<>( , HttpStatus.CREATED);
//    }
//
//    // 맴버 정보 조회
//    @GetMapping("/api/auth/members")
//    public ResponseEntity getMember(){
//
//        return new ResponseEntity<>( , HttpStatus.OK);
//    }
//
//    // 맴버 정보 수정
//    @PutMapping("/api/auth/members")
//    public ResponseEntity patchMember(){
//
//        return new ResponseEntity<>( , HttpStatus.OK);
//    }
//
//    // 맴버 삭제
//    @DeleteMapping("/api/auth/members")
//    public ResponseEntity deleteMember(){
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
