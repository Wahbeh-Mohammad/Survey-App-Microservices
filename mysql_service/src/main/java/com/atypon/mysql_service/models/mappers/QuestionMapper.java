package com.atypon.mysql_service.models.mappers;

import com.atypon.mysql_service.models.core.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question question = new Question();
        question.setQuestionId(rs.getInt("questionId"));
        question.setSurveyId(rs.getInt("surveyId"));
        question.setPrompt(rs.getString("prompt"));
        return question;
    }
}
