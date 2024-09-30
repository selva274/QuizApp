package com.selva.QuizApp.dao;

import com.selva.QuizApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer>{
   List<Question> findByAnswer(String category);
   @Query(value = "Select * from question q where q.category=:category ORDER BY RANDOM() LIMIT :numQ",nativeQuery = true)
   List<Question> findRandomQuestionCategory(String category, int numQ);
}