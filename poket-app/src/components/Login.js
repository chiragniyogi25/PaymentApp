import React, { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import AxiosInstance from "../api/AxiosInstance";
import { JWTContext, LoginContext } from "../Helper/Context";

export default function Login() {
  const LOGIN_URL = "/login";
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errMsg, setErrMsg] = useState("");
  const [success, setSuccess] = useState(false);

  const { loggedIn, setLoggedIn } = useContext(LoginContext);
  const { accessToken, setAccessToken } = useContext(JWTContext);

  const navigate = useNavigate();

  useEffect(() => {
    setErrMsg("");
  }, [email, password]);

  const loginUser = async (e) => {
    e.preventDefault();
    try {
      const data = {
        email: email,
        password: password,
      };

      const response = await AxiosInstance.post(
        LOGIN_URL,
        JSON.stringify(data), //JSON String
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      setEmail("");
      setPassword("");
      setSuccess(true);
      localStorage.setItem("token", response.data.jwt);
      setLoggedIn(true);
      console.log(response.data.jwt);
      setAccessToken(response.data.jwt);
      navigate("/userDashboard");

      // console.log(accessToken);
    } catch (err) {
      let val = err.reponse;
      let c = err.response.status;
      if (val === undefined && c !== 403) {
        setErrMsg("No Server Response");
      } else if (c == 403) {
        setErrMsg("Please Enter Valid Credentials");
      } else {
        setErrMsg("Login Failed");
      }
      setSuccess(false);
      setLoggedIn(false);
    }
  };
  return success || loggedIn ? (
    <></>
  ) : (
    <div className="container-fluid my-3">
      <div className="row">
        <div className="col-md-4 offset-md-3">
          <center
            className={` ${errMsg ? "alert alert-danger" : "hidden"}`}
            role="alert"
            style={{ width: "30rem" }}
          >
            {errMsg}
          </center>
          <div className="card" style={{ width: "30rem", height: "auto" }}>
            <div className="card-header primary-background text-dark text-center">
              <span className="fa fa-user fa-3x"></span>
              <br />
              <p>Login Here</p>
            </div>
            <div className="card-body">
              <form className="container my-3" onSubmit={loginUser}>
                <div className="mb-3">
                  <label htmlFor="exampleInputEmail1" className="form-label">
                    Email address
                  </label>
                  <input
                    type="email"
                    className="form-control"
                    id="exampleInputEmail1"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    aria-describedby="emailHelp"
                  />
                </div>
                <div className="mb-3">
                  <label htmlFor="exampleInputPassword1" className="form-label">
                    Password
                  </label>
                  <input
                    type="password"
                    className="form-control"
                    id="exampleInputPassword1"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                </div>
                <center>
                  <button type="submit" className="btn btn-primary">
                    Submit
                  </button>
                </center>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
