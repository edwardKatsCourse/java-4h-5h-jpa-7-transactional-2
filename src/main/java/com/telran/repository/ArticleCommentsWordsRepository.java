package com.telran.repository;

import com.telran.entity.Article;
import com.telran.entity.ArticleCommentsWordsCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentsWordsRepository extends JpaRepository<ArticleCommentsWordsCount, Long> {

    ArticleCommentsWordsCount findByArticle(Article article);
}
