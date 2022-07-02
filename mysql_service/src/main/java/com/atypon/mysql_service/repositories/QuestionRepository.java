package com.atypon.mysql_service.repositories;

import com.atypon.mysql_service.models.core.Question;
import com.atypon.mysql_service.models.mappers.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Repository
@Profile("database")
public class QuestionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<Question> fetchSurveyQuestions(Integer surveyId) {
        String sqlStatement = "SELECT * FROM questions WHERE surveyId = ?;";

        ArrayList<Question> questions = (ArrayList<Question>)jdbcTemplate.query(sqlStatement, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, surveyId);
            }
        }, new QuestionMapper());

        if(questions.isEmpty()) return null;
        return questions;
    }

    public Integer createNewQuestion(Question requestQuestion) {
        Integer surveyId = requestQuestion.getSurveyId();
        String prompt = requestQuestion.getPrompt();
        if(surveyId == null || prompt == null)
            return null;

        String sqlStatement = "INSERT INTO questions(surveyId, prompt) VALUES (?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, surveyId);
                preparedStatement.setString(2, prompt);
                return preparedStatement;
            }
        }, keyHolder);

        // failed to create new question.
        if(keyHolder.getKey() == null) return null;
        return keyHolder.getKey().intValue();
    }

}
