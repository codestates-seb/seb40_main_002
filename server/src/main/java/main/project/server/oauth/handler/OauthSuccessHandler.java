package main.project.server.oauth.handler;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import main.project.server.jwt.JwtTokenizer;
import main.project.server.member.dto.MemberDto;
import main.project.server.member.entity.Member;
import main.project.server.member.service.MemberService;
import main.project.server.oauth.wrapper.CustomDefaultOAuth2User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@RequiredArgsConstructor
@Component
public class OauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberService memberService;

    private final Gson gson;

    private final JwtTokenizer jwtTokenizer;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {


        CustomDefaultOAuth2User customDefaultOAuth2User = (CustomDefaultOAuth2User) authentication.getPrincipal();
        String memberId = customDefaultOAuth2User.getOAuthAttributes().getMemberId();

        Optional<Member> member = memberService.findMember(memberId);

        //이미 oauth 가입되어 있는 유저
        if (member.isPresent()) {
            //토큰 발행
            //DB에 최신 데이터 업데이트
            //본 서비스의 메인페이지로 Redirect
            mainRedirect(request, response, member.get());
        }
        //최초 oauth 로그인을 한 유저
        else {
            addInfoRedirect(request, response, customDefaultOAuth2User);
        }

    }


    public String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("memberRoles", member.getMemberRoles());

        String subject = member.getMemberId();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        return jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
    }

    // 리프레시 토큰 추가할 수도 있다.


    private URI createMainPageURI(String accessToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost") // 배포시 변경할 것
                .path("/main.html")
                .queryParams(queryParams)
                .build()
                .toUri();
    }

    private URI createAddInfoURI() {
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")  // 배포시 변경할 것
                .path("/member-register.html")
                .build()
                .toUri();
    }

//    private void mainRedirect(HttpServletRequest request, HttpServletResponse response, Member member) throws IOException{
//        String accessToken = delegateAccessToken(member);
//        String mainUri = createMainPageURI(accessToken).toString();
//
//        String mainJson = gson.toJson(MemberDto.Response.builder()
//                .memberId(member.getMemberId())
//                .memberNickname(member.getMemberNickname())
//                .memberEmail(member.getMemberEmail())
//                .memberPhone(member.getMemberPhone())
//                .memberStatus(member.getMemberStatus())
////                .memberBirth(member.getMemberBirth())
//                .memberNationality(member.getMemberNationality())
//                .memberRegisterKind(member.getMemberRegisterKind())
//                .memberImageUrl(member.getMemberImageUrl())
//                .memberTags(member.getMemberTags())
//                .build());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpStatus.PERMANENT_REDIRECT.value());
//        response.getWriter().write(mainJson);
//
//        getRedirectStrategy().sendRedirect(request, response, mainUri);
//    }

//    private void addInfoRedirect(HttpServletRequest request, HttpServletResponse response, CustomDefaultOAuth2User customDefaultOAuth2User) throws IOException{
//        String addInfoUri = createAddInfoURI().toString();
//        String addInfoJson = gson.toJson(MemberDto.Response.builder()
//                .memberId(customDefaultOAuth2User.getOAuthAttributes().getMemberId())
//                .memberEmail(customDefaultOAuth2User.getOAuthAttributes().getMemberEmail())
//                .memberImageUrl(customDefaultOAuth2User.getOAuthAttributes().getMemberImageUrl())
//                .build());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpStatus.PERMANENT_REDIRECT.value());
//        response.getWriter().write(addInfoJson);
//        getRedirectStrategy().sendRedirect(request, response, addInfoUri);
//    }


    private void mainRedirect(HttpServletRequest request, HttpServletResponse response, Member member) throws IOException{
        String accessToken = delegateAccessToken(member);
        String mainUri = createMainPageURI(accessToken).toString();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("memberId", member.getMemberId());
        response.setHeader("memberEmail", member.getMemberEmail());
        getRedirectStrategy().sendRedirect(request, response, mainUri);
    }



    private void addInfoRedirect(HttpServletRequest request, HttpServletResponse response, CustomDefaultOAuth2User customDefaultOAuth2User) throws IOException{
        String addInfoUri = createAddInfoURI().toString();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.PERMANENT_REDIRECT.value());
        response.setHeader("memberId",customDefaultOAuth2User.getOAuthAttributes().getMemberId());
        response.setHeader("memberEmail",customDefaultOAuth2User.getOAuthAttributes().getMemberEmail());
        response.setHeader("memberImageUrl",customDefaultOAuth2User.getOAuthAttributes().getMemberImageUrl());

        getRedirectStrategy().sendRedirect(request, response, addInfoUri);
    }

}
