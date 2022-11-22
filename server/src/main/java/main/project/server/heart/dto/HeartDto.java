package main.project.server.heart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class HeartDto {


    @Setter
    @Getter
    @NoArgsConstructor
    public static class Post {
        private int heart;
    }


}
