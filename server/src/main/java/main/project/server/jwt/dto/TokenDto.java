package main.project.server.jwt.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class TokenDto {

    @NotBlank
    String refreshToken;
    @NotBlank
    String memberId;
}
