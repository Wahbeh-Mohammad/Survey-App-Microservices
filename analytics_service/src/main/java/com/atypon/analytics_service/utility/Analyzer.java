package com.atypon.analytics_service.utility;

import com.atypon.analytics_service.models.analysis.AnalyzedAnswer;
import com.atypon.analytics_service.models.analysis.AnalyzedQuestion;
import com.atypon.analytics_service.models.analysis.AnalyzedSurvey;
import com.atypon.analytics_service.models.composed.FullQuestion;
import com.atypon.analytics_service.models.composed.FullSurvey;
import com.atypon.analytics_service.models.core.Answer;
import com.atypon.analytics_service.models.core.Submission;
import com.atypon.analytics_service.models.core.Survey;

import java.util.ArrayList;
import java.util.HashMap;

public class Analyzer {
    public AnalyzedSurvey createNewAnalyzedSurvey(FullSurvey fullSurvey, ArrayList<Submission> submissions) {
        Survey survey = fullSurvey.getSurvey();
        ArrayList<FullQuestion> questions = fullSurvey.getQuestions();

        // populate hashmap with { questionId, { {answer1, value=0} , ... , {answerN, value=0} } }
        HashMap<Integer, HashMap<String, Integer>> submissionAnalysis = new HashMap<>();
        for(FullQuestion question : questions) {
            Integer questionId = question.getQuestion().getQuestionId();
            ArrayList<Answer> answers = question.getAnswers();
            submissionAnalysis.put(questionId, new HashMap<>());
            for(Answer answer : answers) {
                submissionAnalysis.get(questionId).put(answer.getValue(), 0);
            }
        }

        // For each submission add 1 to the answer chosen
        ArrayList<AnalyzedQuestion> analyzedQuestions = new ArrayList<>();
        for(Submission submission : submissions) {
            int questionId = submission.getQuestionId();
            String answer = submission.getAnswer();
            int oldValue = submissionAnalysis.get(questionId).get(answer);
            submissionAnalysis.get(questionId).put(answer, oldValue + 1);
        }

        // Build analyzed Questions
        for(FullQuestion question : questions) {
            HashMap<String, Integer> questionAnswerAnalysis = submissionAnalysis.get(question.getQuestion().getQuestionId());
            ArrayList<AnalyzedAnswer> analyzedAnswers = new ArrayList<>();

            int max = -1, min = (int) 1e9;
            String maximum = null, minimum = null; // most chosen & least chosen
            for (Answer answer : question.getAnswers()) {
                AnalyzedAnswer analyzedAnswer = new AnalyzedAnswer(answer.getValue(), questionAnswerAnalysis.get(answer.getValue()));
                analyzedAnswers.add(analyzedAnswer);
                if (analyzedAnswer.getValue() > max) {
                    max = analyzedAnswer.getValue();
                    maximum = analyzedAnswer.getTitle();
                }
                if (analyzedAnswer.getValue() < min) {
                    min = analyzedAnswer.getValue();
                    minimum = analyzedAnswer.getTitle();
                }
            }

            analyzedQuestions.add(new AnalyzedQuestion(question.getQuestion(), maximum, minimum, analyzedAnswers));
        }

        return new AnalyzedSurvey(survey.getSurveyId(), survey, analyzedQuestions, submissions.size() / questions.size() );
    }
}
