package main.project.server.exception;


public enum ExceptionCode {

    ALREADY_LOGOUT_MEMBER(400, "already logout member"),

    NOT_EXISTS_GUESTHOUSE(400, "not exists guesthouse"),

    NOT_OWN_GUESTHOUSE(400, "not own guesthouse"),

    NOT_AVAILABLE_RESERVATION(400, "not available reservation");

    int code;
    String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
