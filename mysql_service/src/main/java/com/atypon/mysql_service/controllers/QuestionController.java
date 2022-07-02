package com.atypon.mysql_service.controllers;

import com.atypon.mysql_service.models.core.Question;
import com.atypon.mysql_service.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    QuestionRepository repository;

    @Autowired
    public QuestionController(QuestionRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin
    @PostMapping("/new")
    public ResponseEntity<?> postNewQuestion(@RequestBody Question requestQuestion) {
        if(requestQuestion == null) return new ResponseEntity<String>("Invalid request body", HttpStatus.BAD_REQUEST);

        Integer newQuestionId = repository.createNewQuestion(requestQuestion);

        if(newQuestionId == null)
            return new ResponseEntity<String>("Couldn't create new question", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Integer>(newQuestionId, HttpStatus.ACCEPTED);
    }
}
