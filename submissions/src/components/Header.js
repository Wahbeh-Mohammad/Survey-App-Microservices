import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Box, Button, Typography } from "@mui/material";
import "../styles/Header.css";

const Header = (props) => {
    const [userInfo, setUserInfo] = useState({});
    const [loggedIn, setLoggedIn] = useState(false);

    const handleLogout = () => {
        localStorage.removeItem("user");
        window.location.assign("/");
    }

    useEffect(() => {
        // check if user is logged in.
        let data = localStorage.getItem("user");
        if( data ) {
            data = JSON.parse(data);
            if( data["username"] && data["password"] ) {
                setUserInfo(data);
                setLoggedIn(true);
            }
        } 
    }, []);

    return (
        <Box className="navbar">
            <Link to="/home" className="link">
                <Typography variant="h4" color="primary"> Survey Application </Typography>
            </Link>
            { loggedIn && 
                <>
                    <Box>
                        <Link className="link" to="/home"> <Button> Home </Button> </Link>
                        <Link className="link" to="/create"> <Button>Create a Survey</Button> </Link>
                    </Box>
                    <Box className="user-info">
                        <Typography variant="h5"> {userInfo["username"]} </Typography>
                        <Button variant="outlined" size='large' onClick={ handleLogout }> Logout </Button>
                    </Box> 
                </>
            }
            { !loggedIn && <Link to="/"> Login </Link> }
        </Box>
    );
}
 
export default Header;