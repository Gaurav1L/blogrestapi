package com.blogapp1.controller;


import com.blogapp1.payload.ListPostDto;
import com.blogapp1.payload.PostDto;
import com.blogapp1.services.PostService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto , HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post is deleted!" , HttpStatus.OK);
    }

    //pagination code from starting here
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc/desc , this is query parameter so used @RequestParam
    @GetMapping
    public ResponseEntity<ListPostDto> fetchAllPosts(
            @RequestParam(name="pageNo" , defaultValue = "0" , required = false) int pageNo,
            @RequestParam(name="pageSize" , defaultValue = "5" , required = false) int pageSize,
            @RequestParam(name="sortBy" , defaultValue = "id" , required = false) String sortBy,
            @RequestParam(name="sortDir" , defaultValue = "asc" , required = false) String sortDir

    ){
        ListPostDto listPostDto = postService.fetchALlPosts(pageNo , pageSize , sortBy , sortDir);    //refactor automatically
        return new ResponseEntity<>(listPostDto , HttpStatus.OK) ;

    }

    //http://localhost:8080/posts ,,query param
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto , HttpStatus.OK);
    }
}
