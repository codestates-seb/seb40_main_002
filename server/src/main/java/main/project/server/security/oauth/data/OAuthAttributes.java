package main.project.server.security.oauth.data;


import lombok.*;
import main.project.server.member.entity.Member;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class OAuthAttributes {

    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String memberId;
    private String memberEmail;
    private String memberImageUrl;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String memberId, String memberEmail, String memberImageUrl) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberImageUrl = memberImageUrl;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if(registrationId.equals("naver")) {
            return ofNaver(userNameAttributeName, attributes);
        } else if (registrationId.equals("kakao")) {
            return ofKakao(userNameAttributeName, attributes);
        } else {
            return ofGoogle(userNameAttributeName, attributes);
        }
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .memberId(response.get("id") + "@naver")
                .memberEmail((String) response.get("email"))
                .memberImageUrl((String) response.get("profile_image"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {

        Long id = (Long)attributes.get(userNameAttributeName);
        LinkedHashMap<String, Object> kakaoAccount = (LinkedHashMap)attributes.get("kakao_account");
        String email = (String)kakaoAccount.get("email");
        LinkedHashMap<String, Object> profile = (LinkedHashMap) kakaoAccount.get("profile");
        String profileImageUrl = (String)profile.get("profile_image_url");

        return OAuthAttributes.builder()
                .memberId(id + "@kakao")
                .memberEmail(email)
                .memberImageUrl(profileImageUrl)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName).build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        String id = (String) attributes.get(userNameAttributeName);
        String email = (String) attributes.get("email");
        String picture = (String) attributes.get("picture");
        return OAuthAttributes.builder()
                .memberId(id + "@google")
                .memberEmail(email)
                .memberImageUrl(picture)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName).build();
    }

    public Member toEntity() {
        return Member.builder()
                .memberId(memberId)
                .memberEmail(memberEmail)
                .memberImageUrl(memberImageUrl)
                .build();
    }
}
