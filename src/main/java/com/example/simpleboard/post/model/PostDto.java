package com.example.simpleboard.post.model;

import com.example.simpleboard.reply.db.ReplyEntity;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostDto {


    private Long id;

    private Long boardId; // 이름이 board_id로 바뀜
    private String userName;
    private String password;
    private String email;
    private String status;
    private String title;

    private String content;
    private LocalDateTime postedAt;

    private List<ReplyEntity> replyList = List.of();

}
