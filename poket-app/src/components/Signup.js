import React, { useEffect, useState } from "react";
import AxiosInstance from "../api/AxiosInstance";

const Signup = () => {
  const EMAIL_REGEX =
    /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
  const SIGNUP_URL = "/signup";
  const [name, setName] = useState("");
  const [userValid, setValidUser] = useState(false);
  const [userFocus, setUserFocus] = useState(false);

  const [email, setEmail] = useState("");
  const [validEmail, setValidEmail] = useState(false);
  const [emailFocus, setEmailFocus] = useState(false);

  const [password, setPassword] = useState("");
  const [validPwd1, setValidPwd1] = useState(false);
  const [pwdFocus, setPwdFocus] = useState(false);

  const [matchPassword, setMatchPassword] = useState("");
  const [validPwd, setValidPwd] = useState(false);
  const [pwdValidFocus, setPwdValidFocus] = useState(false);

  const [errMsg, setErrMsg] = useState("");
  const [errMsg1, setErrMsg1] = useState("");
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    setValidUser(name.length > 0 ? true : false);
  }, [name]);

  //for email validation
  useEffect(() => {
    setValidEmail(EMAIL_REGEX.test(email));
  }, [email]);

  // user useEffect
  useEffect(() => {
    setValidPwd1(password.length >= 4 ? true : false);
  }, [password]);

  //for password validation
  useEffect(() => {
    setValidPwd(password === matchPassword);
  }, [password, matchPassword]);

  //for error msg
  useEffect(() => {
    setErrMsg("");
    setErrMsg1("");
  }, [name, email, password, matchPassword]);

  const registerUser = async (e) => {
    e.preventDefault();
    const v1 = EMAIL_REGEX.test(email);
    if (!v1 || password !== matchPassword || name.length === 0) {
      setErrMsg1("Invalid Entry");
      return;
    }
    // console.log("Submitted");

    try {
      const response = await AxiosInstance.post(
        SIGNUP_URL,
        JSON.stringify({ name, email, password }),
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      console.log(response.data);
      console.log(JSON.stringify(response));
      setSuccess(true);
      setErrMsg("");
      setErrMsg1("");

      // clear input fields
    } catch (err) {
      let val = err.reponse;
      let c = err.response.status;
      if (val === undefined && c != 409) {
        setErrMsg1("No Server Response");
      } else {
        if (c === 409) setErrMsg("Email address Already In-Use");
        else setErrMsg1("Registration Failed");
      }
    }
  };

  return (
    <>
      <div className="container my-3">
        <div className="row">
          <div className="col-md-4 offset-md-3">
            {success ? (
              <div className="card" style={{ width: "30rem", height: "20rem" }}>
                <div className="card-body">
                  <h1>Successfully Registered</h1>
                  <p>
                    <a href="/login">Login</a>
                  </p>
                </div>
              </div>
            ) : (
              <div>
                <center
                  className={` ${errMsg1 ? "alert alert-danger" : "hidden"}`}
                  role="alert"
                  style={{ width: "30rem" }}
                >
                  {errMsg1}
                </center>
                <div
                  className="card"
                  style={{ width: "30rem", height: "auto" }}
                >
                  <div className="card-header primary-background text-bold text-center">
                    <span className="fa fa-user fa-3x"></span>
                    <br />
                    <p>Create New Account</p>
                  </div>
                  <div className="card-body">
                    <form className="container my-3" onSubmit={registerUser}>
                      <div className="form-group">
                        <label htmlFor="exampleInputName">Enter Name</label>
                        <input
                          className={`form-control ${
                            userFocus && !userValid ? "is-invalid" : ""
                          }`}
                          id="exampleInputName"
                          type="text"
                          placeholder="Enter Name"
                          autoComplete="off"
                          value={name}
                          onChange={(e) => setName(e.target.value)}
                          required
                          onBlur={() => setUserFocus(false)}
                          onFocus={() => setUserFocus(true)}
                        />
                        <small
                          id="nameHelp"
                          className={`text-danger ${
                            userFocus && !userValid ? "" : "hidden"
                          }`}
                        >
                          You cannot leave it blank
                        </small>
                      </div>

                      <div className="form-group my-3">
                        <label htmlFor="exampleEmail">Email</label>
                        <input
                          className={`form-control ${
                            emailFocus && !validEmail ? "is-invalid" : ""
                          }`}
                          id="exampleEmail"
                          type="email"
                          placeholder="Enter Email"
                          autoComplete="off"
                          value={email}
                          onChange={(e) => setEmail(e.target.value)}
                          required
                          onBlur={() => setEmailFocus(false)}
                          onFocus={() => setEmailFocus(true)}
                        />
                        <small
                          id="emailHelp"
                          className={`text-danger ${
                            emailFocus && !validEmail ? "" : "hidden"
                          }`}
                        >
                          Provide valid email. Eg: xyz@gmail.com
                        </small>

                        <small
                          className={`text-danger ${errMsg ? "" : "hidden"}`}
                        >
                          {errMsg}
                        </small>
                      </div>

                      <div className="form-group my-3">
                        <label htmlFor="password">Password</label>
                        <input
                          className={`form-control ${
                            pwdFocus && !validPwd1 ? "is-invalid" : ""
                          }`}
                          id="password"
                          type="password"
                          placeholder="Enter Password"
                          autoComplete="off"
                          value={password}
                          onChange={(e) => setPassword(e.target.value)}
                          required
                          onBlur={() => setPwdFocus(false)}
                          onFocus={() => setPwdFocus(true)}
                        />
                        <small
                          id="nameHelp"
                          className={`text-danger ${
                            pwdFocus && !validPwd1 ? "" : "hidden"
                          }`}
                        >
                          Password length should be equal to greater than 4
                        </small>
                      </div>

                      <div className="form-group my-3">
                        <label htmlFor="retypePassword">Re-Type Password</label>
                        <input
                          className={`form-control ${
                            pwdValidFocus && !validPwd ? "is-invalid" : ""
                          }`}
                          id="retypePassword"
                          type="password"
                          placeholder="Enter Password Again"
                          autoComplete="off"
                          value={matchPassword}
                          onChange={(e) => setMatchPassword(e.target.value)}
                          required
                          onBlur={() => setPwdValidFocus(false)}
                          onFocus={() => setPwdValidFocus(true)}
                        />
                        <small
                          id="nameHelp"
                          className={`text-danger ${!validPwd ? "" : "hidden"}`}
                        >
                          Password not being matched yet
                        </small>
                      </div>
                      <center>
                        <button
                          disabled={
                            userValid && validEmail && validPwd1 && validPwd
                              ? false
                              : true
                          }
                          className="btn btn-primary my-3"
                          type="submit"
                        >
                          Register
                        </button>
                      </center>
                    </form>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
};
export default Signup;
