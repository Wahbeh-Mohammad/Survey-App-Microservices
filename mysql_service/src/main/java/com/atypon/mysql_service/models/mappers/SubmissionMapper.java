package com.atypon.mysql_service.models.mappers;

import com.atypon.mysql_service.models.core.Submission;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubmissionMapper implements RowMapper<Submission> {
    @Override
    public Submission mapRow(ResultSet rs, int rowNum) throws SQLException {
        Submission submission = new Submission();
        submission.setSubmissionId(rs.getInt("submissionId"));
        submission.setAnswer(rs.getString("answer"));
        submission.setSurveyId(rs.getInt("surveyId"));
        submission.setQuestionId(rs.getInt("questionId"));
        return submission;
    }
}
