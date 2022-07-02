import React, { useState, useEffect } from 'react';
import { Header, SurveyLink } from '../components';
import { Typography, Box } from "@mui/material";
import "../styles/Home.css";

const Home = (props) => {
    const [loggedIn, setLoggedIn] = useState(false);
    const [surveys, setSurveys] = useState([]);

    useEffect(() => {
        // check if user is logged in.
        let data = localStorage.getItem("user");
        if( data ) {
            data = JSON.parse(data);
            if( data["username"] && data["password"] ) {
                setLoggedIn(true);
            }
        } 

        const fetchSurveys = async () => {
            // Fetch Surveys
            const response = await fetch(`${process.env.REACT_APP_SURVEYAPP_URL}/surveys/`, {
                method:"GET"
            });
            const data = await response.json();
            setSurveys(data);
        }

        fetchSurveys();
    }, []);

    return (
        <Box className="home-wrapper">
            <Box className="home">
                <Header />
                { loggedIn && 
                    <Box className="surveys">
                        { surveys && surveys.map((survey => {
                            return ( <SurveyLink key={survey.surveyId} survey={survey} /> )
                        }))}
                        { (surveys.length === 0) && <Typography variant="h4"> No surveys yet </Typography> }
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
 
export default Home;