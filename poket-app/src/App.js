import "./App.css";

import Navbar from "./components/Navbar";
import { useEffect, useState } from "react";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Login from "./components/Login";
import Signup from "./components/Signup";
import AddBill from "./usercomponent/AddBill";
import ViewBill from "./usercomponent/ViewBill";
import Statement from "./usercomponent/Statement";

import {LoginContext} from "./Helper/Context";

function App() {
  const [loggedIn, setLoggedIn] = useState(false);//to check login state
  useEffect(() => {
    let str=localStorage.getItem('token');
    console.log(str);
    console.log(typeof str);
    if(str==null){
      setLoggedIn(false);
      return;
    }
    if(str.length>0){
      setLoggedIn(true);
    }
  }, []);

  return (
    <LoginContext.Provider value={{ loggedIn,setLoggedIn }}>
      <Router>
        <Navbar />
        <Routes>
          <Route exact path="/" element={<Login  />} />
          <Route
            exact
            path="/login"
            element={<Login />}
          />
          <Route exact path="/signup" element={<Signup />} />

          <Route exact path="/viewbill" element={<ViewBill />} />
          <Route exact path="/addBills" element={<AddBill />} />
          <Route exact path="/viewStatement" element={<Statement />} />
        </Routes>
      </Router>
    </LoginContext.Provider>
  );
}

export default App;
