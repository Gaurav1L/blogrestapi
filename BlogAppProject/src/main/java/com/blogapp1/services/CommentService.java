package com.blogapp1.services;

import com.blogapp1.payload.CommentDto;
import com.blogapp1.payload.PostWithCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto , long postId);

    PostWithCommentDto getAllCommentsByPostId(long Id);


}
