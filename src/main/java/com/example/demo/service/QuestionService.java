package com.example.demo.service;

import com.example.demo.model.Question;
import com.example.demo.dao.QuestionDao;
import com.example.demo.model.QuestionWrapper;
import com.example.demo.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getQuestionsWithAnswers() {
        try{
            Sort sort = Sort.by(Sort.Direction.ASC, "questionId"); // Sort by questionId in ascending order
            // Fetch questions from data source
            return new ResponseEntity<> (questionDao.findAll(sort), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<> (new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }


    public ResponseEntity<List<QuestionWrapper>> getQuestionsWithoutAnswers() {
        try {
            Sort sort = Sort.by(Sort.Direction.ASC, "questionId"); // Sort by questionId in ascending order

            // Fetch questions from data source
            List<Question> questionsFromDB = questionDao.findAll(sort);

            // Construct QuestionWrapper objects with required fields
            List<QuestionWrapper> questionsForUser = new ArrayList<>();
            for (Question q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper(q.getQuestionId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4(), q.getFeedback1(), q.getFeedback2(), q.getFeedback3(), q.getFeedback4(), q.getGeneralFeedback() );
                questionsForUser.add(qw);
            }

            // Return ResponseEntity with QuestionWrapper objects
            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Integer> calculateScore(List<Response> responses) {
        ResponseEntity<List<Question>> questionsResponse = getQuestionsWithAnswers();
        List<Question> questions = questionsResponse.getBody();


        // Check if the response from getAllQuestions is successful
        if (questionsResponse.getStatusCode() != HttpStatus.OK) {
            // If not successful, return the same response
            return ResponseEntity.badRequest().body(-1); // Indicate a mismatch with a negative score
        }

        int right = 0;

        // Ensure responses and questions have the same size
        if (responses.size() != questions.size()) {
            return ResponseEntity.badRequest().body(-1); // Indicate a mismatch with a negative score
        }

        // Iterate through each response and compare with the corresponding question's rightAnswerIndex
        for (int i = 0; i < responses.size(); i++) {
            Response response = responses.get(i);
            Question question = questions.get(i);

            // Check if the response index matches the right answer index
            if (Integer.parseInt(response.getResponse()) == Integer.parseInt(question.getRightAnswerIndex())) {
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}

