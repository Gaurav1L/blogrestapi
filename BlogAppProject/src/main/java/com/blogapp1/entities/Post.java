package com.blogapp1.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table   //Table annotation specifies the name of db table to used for mapping.
        (
                name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
        )
public class Post {

    @Id    //it’s a primary key of an entity
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;


    @OneToMany(mappedBy = "post", orphanRemoval = true , cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

}
