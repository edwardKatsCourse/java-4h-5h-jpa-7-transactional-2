package com.telran.runner;

import com.telran.entity.Article;
import com.telran.entity.Comment;
import com.telran.entity.Person;
import com.telran.helpers.PopulationHelper;
import com.telran.repository.ArticleRepository;
import com.telran.repository.CommentRepository;
import com.telran.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

//@Component
public class RollbackExampleRunner implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final PopulationHelper populationHelper;


    @Autowired
    public RollbackExampleRunner(PersonRepository personRepository,
                                 ArticleRepository articleRepository,
                                 CommentRepository commentRepository,
                                 PopulationHelper populationHelper) {
        this.personRepository = personRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.populationHelper = populationHelper;
    }



    @Override
    @Transactional //rollback for | no rollback for | + 2 tx manager + timeout + read only
    public void run(String... args) throws Exception {
        init();


        //person 2 adds a comment
        Comment commentByPerson2 = Comment.builder()
                .person(personRepository.findById(2L).get())
                .article(articleRepository.findById(1L).get())
                .comment("comment 1 person 2")
                .build();

        commentRepository.save(commentByPerson2);

        //unchecked exceptions (derived from RuntimeException) - to be rolled back
        //checked exceptions  (derived from Exception)         - NOT to be rolled back

        if (true) {
            throw new IllegalArgumentException("this is some dummy exception");
        }
    }

    //populate -> population
    //database population - наполнение базы изначальным контентом
    private void init() {
        populationHelper.populate();
    }
}
