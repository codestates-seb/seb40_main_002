package main.project.server.exception;


import lombok.Getter;

public enum ExceptionCode {

    ALREADY_LOGOUT_MEMBER(400, "already logout member"),

    NOT_EXISTS_GUESTHOUSE(400, "not exists guesthouse"),

    NOT_OWN_GUESTHOUSE(400, "not own guesthouse"),

    EXPIRED_REFRESH_TOKEN(401, "expired refresh token"),

    INVALID_TOKEN(400, "invalid token"),

    REFRESH_TOKEN_NOT_FOUND(404, "refresh token not found"),

    ALREADY_LOGOUT_TOKEN(400, "already logout token"),

    NOT_AVAILABLE_RESERVATION(400,"not available reservation"),

    RESERVATION_NOT_FOUND(404,"reservation not found"),

    NOT_MATCH_RESERVATION(400, "reservation not match with guest house"),

    NOT_MATCH_ROOM(400, "room not match with guest house"),

    ROOM_NOT_FOUND(404, "room not found"),
    ROOM_IMAGE_COUNT_INCONSISTENCY(400, "room and image count not match"),
    NICKNAME_DUPLICATED(400, "duplicated member nickname"),
    NOT_A_REVIEW_WRITER(406, "You are not a review writer"),
    NOT_A_REVIEW_COMMENT_WRITER(406, "You are not a review_comment writer"),
    REVIEW_NOT_FOUND(404, "review not found"),
    REVIEW_COMMENT_NOT_FOUND(404, "review_comment not found"),
    NOT_MATCH_REVIEW_AND_REVIEW_COMMENT(400, "reviews and review_comments don't match"),
    NOT_VALID_SIGNATURE(400, "not valid signature"),

    EXPIRED_JWT_TOKEN(400, "expired jwt token"),

    MALFORMED_JWT_EXCEPTION(400, "malformed jwt exception"),

    UNSUPPORTED_JWT_EXCEPTION(400, "unsupported jwt exception"),

    OTHER_AUTH_EXCEPTION(400, "other auth exception");

    @Getter
    int code;
    @Getter
    String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
