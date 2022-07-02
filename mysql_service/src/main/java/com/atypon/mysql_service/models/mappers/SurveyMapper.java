package com.atypon.mysql_service.models.mappers;

import com.atypon.mysql_service.models.core.Survey;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SurveyMapper implements RowMapper<Survey> {
    @Override
    public Survey mapRow(ResultSet rs, int rowNum) throws SQLException {
        Survey survey = new Survey();
        survey.setSurveyId(rs.getInt("surveyId"));
        survey.setSurveyName(rs.getString("surveyName"));
        survey.setSurveyDescription(rs.getString("surveyDescription"));
        return survey;
    }
}
