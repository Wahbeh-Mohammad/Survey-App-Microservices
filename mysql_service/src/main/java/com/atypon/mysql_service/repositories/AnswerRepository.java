package com.atypon.mysql_service.repositories;

import com.atypon.mysql_service.models.core.Answer;
import com.atypon.mysql_service.models.mappers.AnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
@Profile("database")
public class AnswerRepository {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public AnswerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<Answer> fetchQuestionAnswers(Integer questionId) {
        String sqlStatement = "SELECT * FROM answers WHERE questionId = ?;";

        ArrayList<Answer> answers = (ArrayList<Answer>) jdbcTemplate.query(sqlStatement, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, questionId);
            }
        }, new AnswerMapper());

        if(answers.isEmpty())
            return null;
        return answers;
    }

    public void createQuestionAnswers(Answer requestAnswer) {
        String sqlStatement = "INSERT INTO answers(questionId, value) VALUES (?, ?);";

        Integer questionId = requestAnswer.getQuestionId();
        String value = requestAnswer.getValue();
        jdbcTemplate.update(sqlStatement, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, questionId);
                ps.setString(2, value);
            }
        });

    }
}
