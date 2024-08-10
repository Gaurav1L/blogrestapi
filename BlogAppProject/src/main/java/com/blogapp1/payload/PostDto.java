package com.blogapp1.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {

    private long id;

    @NotEmpty
    @Size(min=3 , message = "Title should be 3 characters")
    private String title;

    @Size(min=3, message = "description should be 3 characters")
    private String description;
    private String content;

}
