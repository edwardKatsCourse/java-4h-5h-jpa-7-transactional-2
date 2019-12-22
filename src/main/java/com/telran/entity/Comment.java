package com.telran.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = "article")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Article article;

    //ArticleProxy

    /*
    //with proxy
    getHeader() {
        return Hibernate.findById ..  from article
    }

    //without proxy
    getHeader() {
        return this.header;
    }
     */

    private String comment;
}
