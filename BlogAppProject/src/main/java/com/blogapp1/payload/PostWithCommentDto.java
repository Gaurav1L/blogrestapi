package com.blogapp1.payload;

import com.blogapp1.entities.Post;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostWithCommentDto {

    private PostDto post;

//    private long id ;
//    private String name;
//    private String message;
    private List<CommentDto> commentDto = new ArrayList<>();
}
