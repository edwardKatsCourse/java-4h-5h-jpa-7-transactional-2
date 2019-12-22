package com.telran.helpers;

import com.sun.imageio.plugins.common.LZWCompressor;
import com.telran.entity.Article;
import com.telran.entity.Comment;
import com.telran.entity.Person;
import com.telran.repository.ArticleRepository;
import com.telran.repository.CommentRepository;
import com.telran.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class PopulationHelper {
    private final PersonRepository personRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;


    @Autowired
    public PopulationHelper(PersonRepository personRepository,
                            ArticleRepository articleRepository,
                            CommentRepository commentRepository) {
        this.personRepository = personRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void populate() {
        Person person_1 = Person.builder()
                .name("Dave")
                .build();

        Person person_2 = Person.builder()
                .name("Sarah")
                .build();

        //begin
        personRepository.save(person_1);
        //commit;

        //begin
        personRepository.save(person_2);
        //commit;


        Article article_1 = Article.builder()
                .header("Environment Defense")
                .content("This is an article about environment defense")
                .build();

        articleRepository.save(article_1);


        Article article_2 = Article.builder()
                .header("Spacecraft issues of XX century")
                .content("This is an article about spacecraft issues of XX century")
                .build();

        articleRepository.save(article_2);



        //ctrl + d - clone line

        //1..10


        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int randomWordsCount = new Random().nextInt(10) + 1;

            Person [] persons = {person_1, person_2};
            int randomPerson = new Random().nextInt(persons.length);

            Article [] articles = {article_1, article_2};
            int randomArticle = new Random().nextInt(articles.length);


            Comment comment = Comment.builder()
                    .comment(words(randomWordsCount))
                    .person(persons[randomPerson])
                    .article(articles[randomArticle])
                    .build();

            comments.add(comment);
        }

        commentRepository.saveAll(comments);
    }

    private String words(int randomWordsCount) {
        String [] words = {"word1", "word2", "word3"};
        int randomWord = new Random().nextInt(words.length);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < randomWordsCount; i++) {
            stringBuilder.append(words[randomWord]).append(" ");
        }

        return stringBuilder.toString().trim();
    }
}
