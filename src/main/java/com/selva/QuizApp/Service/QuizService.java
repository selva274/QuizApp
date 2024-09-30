package com.selva.QuizApp.Service;

import com.selva.QuizApp.dao.QuestionDao;
import com.selva.QuizApp.dao.QuizDao;
import com.selva.QuizApp.model.Question;
import com.selva.QuizApp.model.QuestionWrapper;
import com.selva.QuizApp.model.Quiz;
import com.selva.QuizApp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
       QuestionDao questionDao;
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions=questionDao.findRandomQuestionCategory(category,numQ);
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
        Optional<Quiz> quiz= quizDao.findById(id);
        List<Question> questionfromDB=quiz.get().getQuestions();
        List<QuestionWrapper> questionforUser=new ArrayList<>();
        for(Question Q:questionfromDB){
            QuestionWrapper qw=new QuestionWrapper(Q.getId(),Q.getQuestion(),Q.getOption1(),Q.getOption2(),Q.getOption3(),Q.getOption4());
            questionforUser.add(qw);
        }
        return new ResponseEntity<>(questionforUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculate(Integer id, List<Response> responses) {
        Quiz quiz=quizDao.findById(id).get();
        List<Question> questions=quiz.getQuestions();
        int right=0;
        int i=0;
        for(Response response:responses){
            if(response.getResponse().equals(questions.get(i).getAnswer()))
                right++;
            i++;
        }
    return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
