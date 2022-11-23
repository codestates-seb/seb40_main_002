package main.project.server.jwt.mapper;

import main.project.server.jwt.dto.TokenDto;
import main.project.server.jwt.entity.RefreshToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    RefreshToken tokenPostToRefreshToken(TokenDto tokenDto);
}
