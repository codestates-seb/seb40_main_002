package main.project.server.member.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum MemberNationality {
    LOCAL,
    FOREIGN;

    @JsonCreator
    public static MemberNationality from(String value) {
        for (MemberNationality nationality : MemberNationality.values()) {
            if (nationality.name().equals(value)) {
                return nationality;
            }
        }
        return null;
    }
}
