import React from 'react';
import { Link } from 'react-router-dom';
import { Box, Button, Typography } from "@mui/material";
import BallotIcon from '@mui/icons-material/Ballot';
import "../styles/SurveyLink.css";

const SurveyLink = (props) => {
    const { survey } = props;

    return (
        <Box className="survey-link">
            <Box className="survey-link-details">
                <Typography variant="h4" className="align-center gap"> <BallotIcon color="primary" fontSize='large' /> { survey["surveyName"] } </Typography>
                <Typography variant="h6" className="align-center gap"> { survey["surveyDescription"] } </Typography>
            </Box>
            <Box>
                <Link to={`/survey/${survey["surveyId"]}`} className="link"> 
                    <Button variant="contained"> View Analysis </Button>
                </Link>
            </Box>
        </Box>
    );
}
 
export default SurveyLink;