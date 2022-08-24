import React, { useContext } from "react";
import { LoginContext } from "../Helper/Context";
import LoggedOutComp from "./LoggedOutComp";
import LoggedInComp from "./LoggedInComp";

const Navbar = () => {
  const { loggedIn, setLoggedIn } = useContext(LoginContext);

  return (
    <>
      {console.log(loggedIn)}
      {loggedIn ? <LoggedInComp /> : <LoggedOutComp />}
    </>
  );
};

export default Navbar;
