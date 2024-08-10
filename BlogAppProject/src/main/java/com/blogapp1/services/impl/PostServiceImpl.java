package com.blogapp1.services.impl;


import com.blogapp1.entities.Post;
import com.blogapp1.exception.ResourceNotFound;
import com.blogapp1.payload.ListPostDto;
import com.blogapp1.payload.PostDto;
import com.blogapp1.repositories.PostRepository;
import com.blogapp1.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private ModelMapper modelMapper;

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository , ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override     // //dto--entity
    public PostDto createPost(PostDto postDto) {
     //   Post post = new Post();  //we have post object and I need to save this entity object to db using repository
        Post post = mapToEntity(postDto);

        Post savedPost = postRepository.save(post);

        PostDto dto = mapToDto(savedPost);

        //now i need to convert this object to dto and return back bcoz i cant return entity , i need to return dto

        return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    //getpostById  / whatever exception im throwing here i send to GlobalException Handler class
    public PostDto getPostById(long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("Post is not found with id:"+ id)
        );
        return mapToDto(post);
    }

    @Override
    public ListPostDto fetchALlPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo , pageSize , sort);  //i want sort object here to convert string to sort ,Sort.by / this will convert string to sort object
       // List<Post> posts = postRepository.findAll();
        Page<Post> all = postRepository.findAll(pageable); //convert this page to List
        List<Post> post = all.getContent();
        List<PostDto> postDtos = post.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

       //here we got all info of pagination , we create class in payload
        ListPostDto listPostDto = new ListPostDto();
        listPostDto.setPostDto(postDtos);
        listPostDto.setTotalPages(all.getTotalPages());
        listPostDto.setTotalElements((int) all.getTotalElements());
        listPostDto.setFirstPage(all.isFirst());
        listPostDto.setLastPage(all.isLast());
        listPostDto.setPageNumber(all.getNumber());

        return listPostDto;
    }


    //dto--entity
    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto , Post.class);
        return post;

    }

    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;

    }
}
