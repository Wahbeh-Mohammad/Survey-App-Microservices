import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Login, Home, TakeSurvey, CreateSurvey, Register } from './views';
import "./styles/global.css"

const App = (props) => {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={ <Login /> }/>
        <Route exact path="/register" element={ <Register /> } />
        <Route exact path="/home" element={ <Home /> } />
        <Route exact path="/survey/:surveyId" element={ <TakeSurvey /> } />
        <Route exact path="/create" element={ <CreateSurvey /> } />
      </Routes>
    </Router>
  );
}

export default App;
