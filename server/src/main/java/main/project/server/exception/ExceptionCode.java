package main.project.server.exception;


import lombok.Getter;

public enum ExceptionCode {

    ALREADY_LOGOUT_MEMBER(400, "already logout member"),

    NOT_EXISTS_GUESTHOUSE(400, "not exists guesthouse"),

    NOT_OWN_GUESTHOUSE(400, "not own guesthouse"),

    EXPIRED_REFRESH_TOKEN(401, "expired refresh token"),

    INVALID_TOKEN(400, "invalid token"),

    ALREADY_LOGOUT_TOKEN(400, "already logout token"),

    NOT_AVAILABLE_RESERVATION(400,"not available reservation"),

    RESERVATION_NOT_FOUND(404,"reservation not found"),

    NOT_MATCH_RESERVATION(400, "reservation not match with guest house"),

    NOT_MATCH_ROOM(400, "room not match with guest house"),

    ROOM_NOT_FOUND(404, "room not found"),

    ROOM_IMAGE_COUNT_INCONSISTENCY(400, "room and image count not match"),

    NICKNAME_DUPLICATED(400, "duplicated member nickname"),

    NOT_A_REVIEW_WRITER(406, "You are not a review writer"),

    NOT_A_REVIEW_COMMENT_WRITER(406, "You are not a review comment writer");

    @Getter
    int code;
    @Getter
    String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
