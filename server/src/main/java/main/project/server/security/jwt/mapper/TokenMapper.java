package main.project.server.security.jwt.mapper;

import main.project.server.security.jwt.dto.TokenDto;
import main.project.server.security.jwt.entity.RefreshToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    RefreshToken tokenPostToRefreshToken(TokenDto tokenDto);
}
