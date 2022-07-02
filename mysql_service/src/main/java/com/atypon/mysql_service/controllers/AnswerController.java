package com.atypon.mysql_service.controllers;

import com.atypon.mysql_service.models.core.Answer;
import com.atypon.mysql_service.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private final AnswerRepository repository;

    @Autowired
    public AnswerController(AnswerRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin
    @PostMapping("/new")
    public ResponseEntity<?> postNewAnswer(@RequestBody Answer requestAnswer) {
        if(requestAnswer == null) return new ResponseEntity<String>("Invalid request body", HttpStatus.BAD_REQUEST);

        repository.createQuestionAnswers(requestAnswer);
        return new ResponseEntity<String>("New answers created", HttpStatus.ACCEPTED);
    }
}
