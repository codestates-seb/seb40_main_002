package main.project.server.member.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MemberRegisterKind {
    GOOGLE,
    KAKAO,
    NAVER;

    @JsonCreator
    public static MemberRegisterKind from(String value) {
        for (MemberRegisterKind registerKind : MemberRegisterKind.values()) {
            if (registerKind.name().equals(value)) {
                return registerKind;
            }
        }
        return null;
    }
}
