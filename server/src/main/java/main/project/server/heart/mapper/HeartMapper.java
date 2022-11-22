package main.project.server.heart.mapper;

import main.project.server.heart.dto.HeartDto;
import main.project.server.heart.entity.Heart;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HeartMapper {

    List<HeartDto.ResponseMyPage> reviewToReviewResponseMyPageDto(List<Heart> hearts);
}
