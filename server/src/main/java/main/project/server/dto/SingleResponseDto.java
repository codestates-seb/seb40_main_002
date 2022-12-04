package main.project.server.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SingleResponseDto <T>{

    String message;
    T data;
}
