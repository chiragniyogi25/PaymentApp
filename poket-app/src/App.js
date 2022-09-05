import "./App.css";
import Navbar from "./components/Navbar";
import { useEffect, useState } from "react";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Login from "./components/Login";
import Signup from "./components/Signup";
import AddBill from "./usercomponent/AddBill";
import Statement from "./usercomponent/Statement";

import { LoginContext, DateContext, JWTContext } from "./Helper/Context";
import Dashboard from "./usercomponent/Dashboard";
import AddMoney from "./usercomponent/AddMoney";
function App() {
  const [loggedIn, setLoggedIn] = useState(false); //to check login state
  const [startDate, setStartDate] = useState(""); //start Date
  const [endDate, setEndDate] = useState(""); //End Date
  const [accessToken, setAccessToken] = useState(""); //access token

  useEffect(() => {
    let str = localStorage.getItem("token");
    // console.log(str);
    // console.log(typeof str);
    if (str === null || str === undefined) {
      setLoggedIn(false);
      return;
    }
    if (str.length > 0) {
      setLoggedIn(true);
      setAccessToken(str);
    }

  }, []);

  return (
    <LoginContext.Provider value={{ loggedIn, setLoggedIn }}>
      <DateContext.Provider
        value={{ startDate, setStartDate, endDate, setEndDate }}
      >
        <JWTContext.Provider value={{ accessToken, setAccessToken }}>
          <Router>
            <Navbar />
            <Routes>
              <Route exact path="/" element={<Login />} />
              <Route exact path="/login" element={<Login />} />
              <Route exact path="/signup" element={<Signup />} />

              <Route exact path="/userDashboard" element={<Dashboard />} />
              <Route exact path="/addBills" element={<AddBill />} />
              <Route exact path="/viewStatement" element={<Statement />} />
              <Route exact path="/addMoney" element={<AddMoney />} />
            </Routes>
          </Router>
        </JWTContext.Provider>
      </DateContext.Provider>
    </LoginContext.Provider>
  );
}

export default App;
