package com.example.simpleboard.board.db;

import com.example.simpleboard.post.db.PostEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String boardName;
    private String status;
    @OneToMany(
            mappedBy = "board"
    )
    // 1 = BoardEntity , N = PostEntity
    @Where(clause = "status = 'REGISTERED'")
    @Builder.Default
    @OrderBy("id desc ")
    private List<PostEntity> postList = List.of();

}
