package com.atypon.mysql_service.repositories;

import com.atypon.mysql_service.models.core.Submission;
import com.atypon.mysql_service.models.mappers.SubmissionMapper;
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
public class SubmissionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SubmissionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<Submission> fetchSurveySubmissions(Integer surveyId) {
        String sqlStatement = "SELECT * FROM submissions WHERE surveyId = ?;";

        ArrayList<Submission> submissions = (ArrayList<Submission>) jdbcTemplate.query(sqlStatement, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, surveyId);
            }
        }, new SubmissionMapper());

        if(submissions.isEmpty())
            return null;
        return submissions;
    }

    public void createSurveySubmissions(ArrayList<Submission> requestSubmissions) {
        String sqlStatement = "INSERT INTO submissions(surveyId, questionId, answer) VALUES (?, ?, ?);";

        for(Submission submission : requestSubmissions) {
            Integer surveyId = submission.getSurveyId(), questionId = submission.getQuestionId();
            String answer = submission.getAnswer();
            jdbcTemplate.update(sqlStatement, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, surveyId);
                    ps.setInt(2, questionId);
                    ps.setString(3, answer);
                }
            });
        }

    }
}
