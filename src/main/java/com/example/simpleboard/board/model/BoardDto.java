package com.example.simpleboard.board.model;

import com.example.simpleboard.post.model.PostDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BoardDto {
    //boardEntity를 리스폰으로 내리지 않기위한 객체

    private Long id;
    private String boardName;
    private String status;

    private List<PostDto> postList = List.of();

}
