package com.atypon.mysql_service.models.mappers;

import com.atypon.mysql_service.models.core.Answer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerMapper implements RowMapper<Answer> {
    @Override
    public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Answer answer = new Answer();
        answer.setAnswerId(rs.getInt("answerId"));
        answer.setQuestionId(rs.getInt("questionId"));
        answer.setValue(rs.getString("value"));
        return answer;
    }
}
