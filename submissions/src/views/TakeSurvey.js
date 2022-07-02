import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Header } from '../components';
import { Typography, Box, Button, Radio, RadioGroup, FormControlLabel, FormControl, Snackbar, Alert } from "@mui/material";
import HelpOutlineIcon from '@mui/icons-material/HelpOutline';
import BallotIcon from '@mui/icons-material/Ballot';
import DoNotDisturbAltIcon from '@mui/icons-material/DoNotDisturbAlt';
import "../styles/TakeSurvey.css";

const TakeSurvey = (props) => {
    const { surveyId } = useParams();
    const [loggedIn, setLoggedIn] = useState(false);
    const [survey, setSurvey] = useState({});
    const [fullQuestions, setFullQuestions] = useState([]);
    const [answers, setAnswers] = useState({});
    const [open, setOpen] = useState(false);
    const [info, setInfo] = useState("");
    const [error, setError] = useState("");

    const handleChange = (e, id) => {
        const value = e.target.value;
        setAnswers((answers) => {
            answers[id] = value;
            return answers;
        });
    }

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') 
            return;

        setOpen(false);
    };

    const handleSubmit = async () => {
        setError("");
        const list = [];
        Object.keys(answers).forEach((key) => {
            list.push({ questionId : key, surveyId, answer: answers[key] });
        });
        if(Object.keys(answers).length !== fullQuestions.length) {
            setError("You need to fill out the whole survey!");
            return;
        }    
        try {
            const response = await fetch(`${process.env.REACT_APP_SURVEYAPP_URL}/submissions/new`, {
                method:"POST",
                headers: {
                    "Content-Type":"application/json"
                },
                body: JSON.stringify(list)
            });
            const data = await response.text();
            setInfo(data);
            setOpen(true);
        } catch ( err ) {
            console.log(err);
        }
    }

    useEffect( () => {
        // check if user is logged in.
        let data = localStorage.getItem("user");
        if( data ) {
            data = JSON.parse(data);
            if( data["username"] && data["password"] ) {
                setLoggedIn(true);
            }
        } 

        const fetchSurvey = async () => {
            const response = await fetch(`${process.env.REACT_APP_SURVEYAPP_URL}/surveys/survey?surveyId=${surveyId}`, {
                method:"GET"
            });
            const data = await response.json();
            const { survey, questions } = data;
            setSurvey(survey);
            setFullQuestions(questions);
        }

        fetchSurvey();
    }, [surveyId]);

    return (
        <Box className="survey-wrapper">
            <Box className="survey">
                <Header />
                { loggedIn && 
                    <Box className="survey-body"> 
                        <Box className="survey-details shadow"> 
                            <Typography variant="h4" className="align-center gap"> 
                                <BallotIcon color="primary" fontSize='large' /> { survey.surveyName } 
                            </Typography>
                            <Typography variant="h6" className="align-center gap"> 
                                { survey["surveyDescription"] } 
                            </Typography>
                        </Box>
                        <Box className="survey-questions shadow"> 
                            { fullQuestions.length !== 0 && fullQuestions.map((fullQuestion) => {
                                    const { question, answers } = fullQuestion;
                                    return (
                                        <Box className="question" key={question.questionId}> 
                                            <Typography color="primary" className="align-center gap" variant="h4">
                                                <HelpOutlineIcon fontSize="large"/>  { question.prompt } 
                                            </Typography> 
                                            <FormControl>
                                                <RadioGroup 
                                                    name={question.questionId.toString()}
                                                    onChange={ (e) => handleChange(e, e.target.name) } >
                                                    {   
                                                        answers.map((answer) => {
                                                            return (<FormControlLabel 
                                                                        key={answer.answerId} 
                                                                        value={answer.value} 
                                                                        control={<Radio />} 
                                                                        label={answer.value} />)
                                                        }) 
                                                    }
                                                </RadioGroup>
                                            </FormControl>
                                        </Box> 
                                    )
                                })
                            } 
                            <Box className="controls">
                                <Button variant="contained" onClick={ handleSubmit }> Submit </Button>
                                { error && <Typography className="align-center gap" variant="h6" color="red"> <DoNotDisturbAltIcon /> { error } </Typography> }
                            </Box>
                            <Snackbar open={ open } autoHideDuration={6000} onClose={handleClose}>
                                <Alert onClose={handleClose} style={{background:"lightgreen"}} severity="success" sx={{ width: '100%' }}>
                                    { info }
                                </Alert>
                            </Snackbar>
                        </Box>
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
 
export default TakeSurvey;