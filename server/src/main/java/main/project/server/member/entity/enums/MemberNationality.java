package main.project.server.member.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum MemberNationality {
    LOCAL,
    FOREIGN;

    //    LOCAL("local"),
//    FOREIGN("foreign");

//    private final String value;

//    MemberNationality(String value) {
//        this.value = value;
//    }

//    @JsonCreator
//    public static MemberNationality from(String value) {
//        for (MemberNationality status : MemberNationality.values()) {
//            if (status.getValue().equals(value)) {
//                return status;
//            }
//        }
//        return null;
//    }

//    @JsonValue
//    public String getValue() {
//        return value;
//    }
}
