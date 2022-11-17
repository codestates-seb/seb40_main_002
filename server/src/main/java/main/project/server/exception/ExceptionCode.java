package main.project.server.exception;


public enum ExceptionCode {

    ALREADY_LOGOUT_MEMBER(400, "already logout member"),

    NOT_EXISTS_GUESTHOUSE(400, "not exists guesthouse");

    int code;
    String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
