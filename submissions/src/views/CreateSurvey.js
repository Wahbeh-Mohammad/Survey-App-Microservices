import React, { useState, useEffect } from "react";
import { Box, Button, Typography, Snackbar, Alert } from "@mui/material";
import { Header } from '../components';
import Editor from "@monaco-editor/react";
import { validateSurvey } from "../utils/surveyValidation";
import DoNotDisturbAltIcon from '@mui/icons-material/DoNotDisturbAlt';
import "../styles/CreateSurvey.css";

const exampleSurvey = `{
    "survey": {
        "surveyName":"Example Survey",
        "surveyDescription":"Example survey for display"
    },
    "questions": [
        {
            "prompt":"Example Question 1",
            "answers": [
                "Answer 1",
                "Answer 2"
            ]
        },
        {
            "prompt":"Example Question 2",
            "answers": [
                "Answer 1",
                "Answer 2",
                "Answer 3",
                "Answer 4"
            ]
        }
    ]
}`


const CreateSurvey = (props) => {
    const [loggedIn, setLoggedIn] = useState(false);
    const [error, setError] = useState("");
    const [open, setOpen] = useState(false);
    const [jsonSurvey, setJsonSurvey] = useState(exampleSurvey);

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') 
            return;

        setOpen(false);
    };

    const handleSubmit = async () => {
        setError("");
        try {
            if(JSON.stringify(exampleSurvey) === JSON.stringify(jsonSurvey)) {
                setError("You cant submit the example survey!");
                return;
            }

            const actualSurvey = JSON.parse(jsonSurvey);

            if(validateSurvey(actualSurvey)) {
                const { survey, questions } = actualSurvey;
                const surveyResponse = await fetch(`${process.env.REACT_APP_SURVEYAPP_URL}/surveys/new`, {
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:JSON.stringify(survey)
                });
                const newSurveyId = await surveyResponse.text();
                questions.forEach( async (question) => {
                    const body = { surveyId: newSurveyId, prompt: question.prompt };
                    const questionResponse = await fetch(`${process.env.REACT_APP_SURVEYAPP_URL}/questions/new`, {
                        method:"POST",
                        headers:{
                            "Content-Type":"application/json"
                        },
                        body:JSON.stringify(body)
                    });
                    const newQuestionId = await questionResponse.text();
                    question.answers.forEach( async (answer) => {
                        const body = { questionId: newQuestionId, value: answer };
                        console.log(body);
                        await fetch(`${process.env.REACT_APP_SURVEYAPP_URL}/answers/new`, {
                            method:"POST",
                            headers:{
                                "Content-Type":"application/json"
                            },
                            body:JSON.stringify(body)
                        });
                        
                    });
                });
                setOpen(true);
            } else {
                setError("Invalid Survey");
            }
        } catch(err) {
            console.log(err);
        }
    }

    useEffect(() => {
        // check if user is logged in.
        let data = localStorage.getItem("user");
        if( data ) {
            data = JSON.parse(data);
            if( data["username"] && data["password"] ) {
                setLoggedIn(true);
            }
        } 
    }, []);
    
    return (
        <Box className="create-wrapper">
            <Box className="create">
                <Header />
                <Box className="create-body">
                    { loggedIn && 
                        <Box className="columns shadow gap">
                            <Box className="editor gap">
                                <Editor 
                                    height="100%"
                                    width="95%"
                                    language={"json"}
                                    defaultValue={jsonSurvey}
                                    onChange={(e) => setJsonSurvey(e)}/>
                                <Box className="controls">
                                    <Button variant="contained" style={{height:"100%"}} onClick={handleSubmit}> Submit </Button>
                                    { error && <Typography className="align-center gap" variant="h6" color="red"> <DoNotDisturbAltIcon /> { error } </Typography> }
                                </Box>
                            </Box>
                            <Box className="tutorial">
                                <Typography variant="h5">  An example Survey is written to the editor by default. </Typography>
                                <Typography variant="h5"> 
                                    Please provide the following data in 
                                    <Typography component="span" variant="h5" color="primary"> JSON </Typography>
                                    format, following the Rules listed below:
                                </Typography>
                                <Typography color="primary" variant="h6" component="div"> - survey </Typography>
                                <Typography variant="h6" component="div" style={{ marginLeft:"1rem" }}> - surveyName: name of new survey </Typography>
                                <Typography variant="h6" component="div" style={{ marginLeft:"1rem" }}> - surveyDescription: description of new survey </Typography>
                                <Typography color="primary" variant="h6" component="div"> - questions : List [ 1 : N ) </Typography>
                                <Typography variant="h6" component="div"> For each object inside the questions list: </Typography>
                                <Typography variant="h6" component="div" style={{ marginLeft:"1rem" }}> - prompt: Question's prompt </Typography>
                                <Typography variant="h6" component="div" style={{ marginLeft:"1rem" }}> - answers [2:6]: ["answer 1", "answer 2", ..] </Typography>
                            </Box>  
                            <Snackbar open={ open }  autoHideDuration={6000} onClose={handleClose}>
                                <Alert onClose={handleClose} style={{background:"lightgreen"}} severity="success" sx={{ width: '100%' }}>
                                    Survey created successfully!
                                </Alert>
                            </Snackbar>
                        </Box> 
                    }
                    { !loggedIn && 
                        <Box className="info">
                            <Typography variant="h2"> You need to be logged in! </Typography>
                        </Box>
                    }
                </Box>
            </Box>
        </Box>
    );
}
 
export default CreateSurvey;