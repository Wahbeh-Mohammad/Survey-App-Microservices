import React from "react";
import { Login, Home, SurveyAnalysis, Register } from './views';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./styles/global.css"

const App = () => {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={ <Login /> } />
        <Route exact path="/register" element={ <Register /> } />
        <Route exact path="/home" element={ <Home /> } />
        <Route exact path="/survey/:surveyId" element={ <SurveyAnalysis /> } />
      </Routes>
    </Router>
  );
}

export default App;
