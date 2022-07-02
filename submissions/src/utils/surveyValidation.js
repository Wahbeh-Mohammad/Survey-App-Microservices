const validateSurveyDetails = (surveyDetails) => {
    const { surveyName, surveyDescription } = surveyDetails;
    if( !surveyName || !surveyDescription ) return false;
    return true;
}

const validateQuestions = (questions) => {
    let valid = true;
    questions.forEach(question => {
        const { prompt, answers } = question;
        if(!prompt || answers.length < 2 || answers.length > 6) valid = false;
    });
    return valid;
}

export const validateSurvey = (data) => {
    const { survey, questions } = data;
    
    if( !survey || !questions ) return false;

    const validSurvey = validateSurveyDetails(survey);
    const validQuestions = validateQuestions(questions);

    return (validSurvey && validQuestions);
}
