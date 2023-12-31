package com.example.simpleboard.reply.db;

import com.example.simpleboard.post.db.PostEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "reply")
//엔티티는 뷰단 까지 내려가지 않는게 좋음
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private PostEntity post; // post => _id => post_id
    private String userName;
    private String password;
    private String status;
    private String title;
    @Column(columnDefinition = "TEXT")

    private String content;
    private LocalDateTime repliedAt;


}
