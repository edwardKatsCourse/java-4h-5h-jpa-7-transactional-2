package com.telran.repository;

import com.telran.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

//insert
//update
//upsert - если есть - обнови, если нету - создай
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
