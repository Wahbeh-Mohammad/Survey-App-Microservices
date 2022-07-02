import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Header } from "../components";
import { Box, Typography } from "@mui/material";
import { PieChart } from "react-minimal-pie-chart";
// icons and styles
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import DoNotDisturbAltIcon from '@mui/icons-material/DoNotDisturbAlt';
import CircleIcon from '@mui/icons-material/Circle';
import HelpOutlineIcon from '@mui/icons-material/HelpOutline';
import BallotIcon from '@mui/icons-material/Ballot';
import "../styles/SurveyAnalysis.css";

const defaultLabelStyle = {
    fontSize: '6px',
    fontWeight:"bold",
    fontFamily: 'sans-serif',
    fill:"white"
};

const SurveyAnalysis = (props) => {
    const { surveyId } = useParams();
    const [loggedIn, setLoggedIn] = useState(false);
    const [survey, setSurvey] = useState({});
    const [questions, setQuestions] = useState([]);
    const [numberOfSubmissions, setNumberOfSubmissions] = useState(0);
    const [analyzed, setAnalyzed] = useState(false);
    const [info, setInfo] = useState("");

    useEffect( () => {
        // check if user is logged in.
        let data = localStorage.getItem("user");
        if( data ) {
            data = JSON.parse(data);
            if( data["username"] && data["password"] ) {
                setLoggedIn(true);
            }
        } 

        const fetchSurveyAnalysis = async () => {
            try {
                const response = await fetch(`${process.env.REACT_APP_MONGO_URL}/analysis/survey?surveyId=${surveyId}`, {
                    method:"GET"
                });
                const contentType = response.headers.get("content-type");
                if(contentType && contentType.indexOf("application/json") !== -1) {
                    const analysis = await response.json();
                    const { survey, questions } = analysis;
            
                    const COLORS = ["#0700C4", "#0000FF", "#0052FF", "#007AFF", "#00A3FF", "#00CCFF"];
                    questions.forEach(function(question, index){
                        let data = [];
                        question.answers.forEach(function(answer, index){
                            let temp = {}; // temp to hold new values 
                            if(answer.value !== 0) {
                                temp["value"] = answer.value;
                                temp["title"] = answer.title;
                                temp["color"] = COLORS[index];
                            }

                            if(Object.keys(temp).length !== 0) {
                                data.push(temp);
                            }
                        });
                        if(data) {
                            questions[index].data = data;
                        }
                    });
                    setNumberOfSubmissions(analysis.numberOfSubmissions);
                    setSurvey(survey);
                    setQuestions(questions);
                    setAnalyzed(true);
                } else {
                    const data = await response.text();
                    setInfo(data);
                }
            } catch(err) {
                console.log(err);
            }
        }

        fetchSurveyAnalysis();
    }, [surveyId]);

    return (
        <Box className="analysis-wrapper">
            <Box className="analysis">
                <Header />
                { loggedIn && 
                    <Box className="analysis-body"> 
                        { analyzed && <>
                            <Box className="survey-details shadow">
                                <Typography variant="h4" className="align-center gap"> 
                                    <BallotIcon color="primary" fontSize='large' />  { survey.surveyName } 
                                </Typography>
                                <Typography variant="h5"> { survey.surveyDescription } </Typography>
                                <Typography variant="h5"> Total Number of Submissions: { numberOfSubmissions } </Typography>
                            </Box>
                            <Box className="questions"> 
                                { questions.map((question) => {
                                    return (
                                        <Box key={question.questionId} className="question-wrapper shadow">
                                            <Box className="question">
                                                <Typography className="align-center gap" color="primary" variant="h4"> 
                                                    <HelpOutlineIcon fontSize="large"/> { question.question.prompt } 
                                                </Typography>
                                                { question.answers.map((answer) => {
                                                    return (
                                                        <Typography key={answer.title} className="question-answer align-center gap" variant="h6"> 
                                                            <CircleIcon fontSize="small" color="primary"/> { answer.title } 
                                                        </Typography>
                                                    )
                                                })}
                                                <br />
                                                <Typography variant="h5" color="green" className="align-center gap"> <CheckCircleIcon /> Most chosen: { question.mostChosen } </Typography>
                                                <Typography variant="h5" color="red" className="align-center gap"> <DoNotDisturbAltIcon /> Least chosen: { question.leastChosen } </Typography>
                                            </Box>  
                                            <Box style={{padding:".5rem", color:"white"}}>
                                                <PieChart 
                                                    className="pie"
                                                    style={{height:"300"}}
                                                    data={ question.data } 
                                                    label={({dataEntry}) => dataEntry.title + " " + dataEntry.value }
                                                    labelPosition="65"
                                                    labelStyle={{ ...defaultLabelStyle }}
                                                    />
                                            </Box>  
                                        </Box>
                                    )
                                })}
                            </Box>
                        </>}
                        { !analyzed && <Typography variant="h6" color="error"> {info} </Typography> }
                    </Box>
                }
                { !loggedIn && 
                    <Box className="info">
                        <Typography variant="h2"> You need to be logged in! </Typography>
                    </Box>
                }
            </Box>
        </Box>
    );
}
 
export default SurveyAnalysis;