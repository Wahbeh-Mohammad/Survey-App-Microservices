import React, { useState, useEffect } from "react";
import { Button, TextField, Box, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import "../styles/Register.css";

const Register = (props) => {
    const [formData, setFormData] = useState({ username:"", password:"" });
    const [info, setInfo] = useState("");

    const handleChange = (e, fieldName) => {
        const newValue = e.target.value;
        setFormData((formData) => {
            formData[fieldName] = newValue;
            return formData;
        });
    }

    const handleSubmit = async () => {
        setInfo("");
        if(!formData["username"]) { setInfo("Please enter Username."); return; }
        if(!formData["password"]) { setInfo("Please enter Password."); return; }
        
        try {
            const response = await fetch(`${process.env.REACT_APP_AUTH_URL}/auth/register`, {
                method:"POST",
                headers:{
                    "Content-Type":"application/json"
                },
                body: JSON.stringify(formData)
            });
            const data = await response.json();
            console.log(JSON.stringify(data));
            if(data && data.username && data.password) {
                localStorage.setItem("user", JSON.stringify(data));
                window.location.assign("/home");
            } else {
                setInfo("Invalid Credentials.");
            }
        } catch ( err ) {
            setInfo("Invalid Credentials.");
        } 
    }

    useEffect(()=>{
        try {
            const user = JSON.parse(localStorage.getItem("user"));
            if(user && user.username && user.password) {
                window.location.assign("/home");
            }
        } catch (err) {
            console.log(err);
        }
    }, []);

    return (
        <Box className="register-wrapper">
            <Box className="register">
                <Typography variant="h4"> Register </Typography>
                <Box className="form-elements">
                    <TextField 
                        type="text" 
                        variant="outlined" 
                        placeholder="Username" 
                        onChange={ (e) => handleChange(e, "username") }/>
                    <TextField 
                        type="password"
                        variant="outlined" 
                        placeholder="Password" 
                        onChange={ (e) => handleChange(e, "password") }/>
                    <Button variant="contained" onClick={handleSubmit}> Submit </Button>
                    { info && <Typography color="error" > { info } </Typography> }
                </Box>
                <Link to="/"> Already have an account? Login </Link>
            </Box>
        </Box>
    );
}

export default Register;