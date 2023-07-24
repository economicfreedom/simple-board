package com.example.simpleboard.post.service;


import com.example.simpleboard.board.db.BoardEntity;
import com.example.simpleboard.board.db.BoardRepository;
import com.example.simpleboard.common.Api;
import com.example.simpleboard.common.Pagination;
import com.example.simpleboard.post.db.PostEntity;
import com.example.simpleboard.post.db.PostRepository;
import com.example.simpleboard.post.model.PostRequest;
import com.example.simpleboard.post.model.PostViewRequest;
import com.example.simpleboard.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final ReplyService replyService;

    public PostEntity create(PostRequest postRequest) {
        BoardEntity boardEntity = boardRepository.findById(postRequest.getBoardId()).get();

        PostEntity entity = PostEntity.builder()
                .board(boardEntity)// << 임시 고정
                .userName(postRequest.getUserName())
                .password(postRequest.getPassword())
                .email(postRequest.getEmail())
                .status("REGISTERED")
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postedAt(LocalDateTime.now())
                .build();

        return postRepository.save(entity);
    }

    /*
    1. 게시글이 있는가?
    2. 비밀번호가 맞는가?
    */
    public PostEntity view(PostViewRequest postViewRequest) {

       return postRepository.findFirstByIdAndStatusOrderByIdDesc(postViewRequest.getPostId(),"REGISTERED")
                .map(it -> {
                    if (!it.getPassword().equals(postViewRequest.getPassword())) {
                        String format = "패스워드가 맞지 않습니다 %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }
                    //더 이상 사용하지 않아도 됨
//                    List<ReplyEntity> replyList = replyService.findAllByPostId(it.getId());
//                    it.setReplyList(replyList);

                    return it;
                }).orElseThrow(
                        () -> {
                            return new RuntimeException("해당 게시글이 존재 하지 않습니다. : " + postViewRequest.getPostId());
                        }
                );

    }

    public Api<List<PostEntity>> all(Pageable pageable) {
        //리턴 하는 리절트 타입을 바꿈
        var list = postRepository.findAll(pageable);

        var pagination = Pagination.builder()
                .page(list.getNumber())//현재 페이지
                .size(list.getSize())// 사이즈 몇개를 요청 했는지에 대한 숫자
                .currentElements(list.getNumberOfElements()) // 현재 페이지의 게시글
                .totalElements(list.getTotalElements()) // 전체 게시물
                .totalPage(list.getTotalPages())// 페이지가 몇번까지 있는지
                .build();

        var response = Api.<List<PostEntity>>builder()
                .body(list.toList())
                .pagination(pagination)
                .build();

        return response;
    }


    public void delete(PostViewRequest postViewRequest){
        postRepository.findById(postViewRequest.getPostId())
                .map(it -> {
                    if (!it.getPassword().equals(postViewRequest.getPassword())) {
                        String format = "패스워드가 맞지 않습니다 %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), postViewRequest.getPassword()));
                    }
                    it.setStatus("UNREGISTERED");
                    postRepository.save(it);
                    return it;
                }).orElseThrow(
                        () -> {
                            return new RuntimeException("해당 게시글이 존재 하지 않습니다. : " + postViewRequest.getPostId());
                        }
                );
    }
}
