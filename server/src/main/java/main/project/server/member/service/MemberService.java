package main.project.server.member.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import main.project.server.jwt.JwtTokenizer;
import main.project.server.member.entity.Member;
import main.project.server.member.repository.MemberRepository;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final RedisTemplate redisTemplate;

    private final JwtTokenizer jwtTokenizer;
    public Optional<Member> findMember(String memberId) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember;
    }


    public Member createMember(Member member) {

        return memberRepository.save(member);
    }


    public Member putMember(Member member) {
        Member putMember = memberRepository.findById(member.getMemberId()).get();

        return putMember;
    }


    public void deleteMember(Member member) {

        memberRepository.delete(member);
    }



    public void registerLogoutToken(String jws) {

        ValueOperations valueOperations = redisTemplate.opsForValue();
        Jws<Claims> jwsClaims = jwtTokenizer.getClaims(
                jws,
                jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey())
        );

        Map<String, Object> claims = jwsClaims.getBody();

        String memberId = (String)claims.get("memberId");
        String logoutKey = "logout@" + jws;
        valueOperations.set(logoutKey, memberId, Duration.ofMinutes(jwtTokenizer.getAccessTokenExpirationMinutes()));
    }
}
