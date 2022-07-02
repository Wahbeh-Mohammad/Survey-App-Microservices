package com.atypon.mysql_service.repositories;

import com.atypon.mysql_service.models.composed.FullQuestion;
import com.atypon.mysql_service.models.composed.FullSurvey;
import com.atypon.mysql_service.models.core.Answer;
import com.atypon.mysql_service.models.core.Question;
import com.atypon.mysql_service.models.core.Survey;
import com.atypon.mysql_service.models.mappers.SurveyMapper;
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
import java.util.List;

@Repository
@Profile("database")
public class SurveyRepository {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SurveyRepository(QuestionRepository questionRepository, AnswerRepository answerRepository, JdbcTemplate jdbcTemplate) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<Survey> fetchAllSurveys() {
        String sqlStatement = "SELECT * FROM surveys;";

        List<Survey> surveys = jdbcTemplate.query(sqlStatement, new SurveyMapper());

        if(surveys.isEmpty()) return null;
        return (ArrayList<Survey>)surveys;
    }

    public FullSurvey fetchSpecificSurvey(Integer surveyId) {
        /*
         * To get full survey details:
         * 1. Fetch the Survey itself
         * 2. Fetch the Questions that are related to the Survey.
         * 3. For each Question fetch answers that are related to it.
         * A full survey object will contain:
         *   1. Survey Object
         *   2. An ArrayList of FullQuestions
         *       Each FullQuestion object will have a Question & an ArrayList of Answers
         * Data will be mapped according to the models and FullSurvey model.
         * */

        String surveySql = "SELECT * FROM surveys WHERE surveyId = ?;";
        // Fetch survey.
        List<Survey> surveys = jdbcTemplate.query(surveySql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, surveyId);
            }
        }, new SurveyMapper());

        if(surveys.isEmpty())
            return null; // no survey found with specified id.
        Survey survey = surveys.get(0);

        // Fetch Questions.
        // & For each question, fetch its answers, and create FullQuestion objects.
        ArrayList<FullQuestion> fullQuestions = new ArrayList<>();
        ArrayList<Question> questions = questionRepository.fetchSurveyQuestions(surveyId);
        for(Question question : questions) {
            ArrayList<Answer> answers = answerRepository.fetchQuestionAnswers(question.getQuestionId());
            // create full question and add it to the list.
            fullQuestions.add(new FullQuestion(question, answers));
        }

        return new FullSurvey(survey, fullQuestions);
    }

    public Integer createNewSurvey(Survey requestSurvey) {
        String surveyName = requestSurvey.getSurveyName(), surveyDescription = requestSurvey.getSurveyDescription();
        if(surveyName == null || surveyDescription == null) return null;
        
        String sqlStatement = "INSERT INTO surveys(surveyName, surveyDescription) VALUES (?, ?);";
        // A KeyHolder to hold auto-generated surveyId after a successful insertion.
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, surveyName);
                preparedStatement.setString(2, surveyDescription);
                return preparedStatement;
            }
        }, keyHolder);

        // Failed to create a survey
        if(keyHolder.getKey() == null) return null;
        return keyHolder.getKey().intValue();
    }
}
