package com.telran.runner;

import com.telran.entity.Article;
import com.telran.entity.ArticleCommentsWordsCount;
import com.telran.entity.Comment;
import com.telran.helpers.PopulationHelper;
import com.telran.repository.ArticleCommentsWordsRepository;
import com.telran.repository.ArticleRepository;
import com.telran.repository.CommentRepository;
import com.telran.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

//@Component
public class PropagationExampleRunner implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final ArticleCommentsWordsRepository articleCommentsWordsRepository;
    private final PopulationHelper populationHelper;

    @Autowired
    public PropagationExampleRunner(

            PersonRepository personRepository,
            ArticleRepository articleRepository,
            CommentRepository commentRepository,
            ArticleCommentsWordsRepository articleCommentsWordsRepository,

            PopulationHelper populationHelper) {

        this.personRepository = personRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.articleCommentsWordsRepository = articleCommentsWordsRepository;

        this.populationHelper = populationHelper;
    }

    @Autowired
    private PropagationExampleRunner runner;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //begin
        runner.init();
        //commit;

        //article #1 - 100 words
        //article #2 - 120 words

        List<Article> articles = articleRepository.findAll();

        for (Article article : articles) {
            try {
                //begin
                runner.recordWordsCount(article);
                //commit
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void init() {
        populationHelper.populate();
    }

    private static int counter = 0;

    //mysql 5.7

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordWordsCount(Article article) {
        System.out.printf("Article with name=[%s] and ID=[%s]\n", article.getHeader(), article.getId());
        counter++;

        if (counter == 1) {
            throw new RuntimeException("Something went wrong");
        }


        //1. check if article_comments_words_count record exists
        ArticleCommentsWordsCount articleCommentsWords = articleCommentsWordsRepository.findByArticle(article);
        if (articleCommentsWords == null) {
            articleCommentsWords = ArticleCommentsWordsCount.builder()
                    .article(article)
                    .build();
            articleCommentsWordsRepository.save(articleCommentsWords);
        }

        List<Comment> comments = commentRepository.findAllByArticle(article);

        long wordsCount = comments.stream()
                .map(x -> x.getComment().split(" "))
                .flatMap(x -> Arrays.stream(x))
                .count();

        articleCommentsWords.setWordsCount((int) wordsCount);
        articleCommentsWordsRepository.save(articleCommentsWords);


        //commit;
    }

}
