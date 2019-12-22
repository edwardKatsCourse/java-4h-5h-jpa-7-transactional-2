package com.telran.runner;

import com.telran.entity.Article;
import com.telran.entity.Comment;
import com.telran.helpers.PopulationHelper;
import com.telran.repository.ArticleRepository;
import com.telran.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class EagerLazyRunner implements CommandLineRunner {

    @Autowired
    private PopulationHelper populationHelper;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //begin

        //begin
        populationHelper.populate();
        //commit;


        Article article = articleRepository.findById(1L).get();

        System.out.println(article);
        //commit;
    }
}
