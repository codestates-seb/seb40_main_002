package main.project.server.jwt.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
public class TokenDto {

    @NotBlank
    String refreshToken;


    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
        String accessToken;
    }
}
