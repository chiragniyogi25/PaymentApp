import React, { useContext } from "react";
import { LoginContext } from "../Helper/Context";
import LoggedOutComp from "./LoggedOutComp";
import LoggedInComp from "./LoggedInComp";

const Navbar = () => {
  const { loggedIn, setLoggedIn } = useContext(LoginContext);

  return (
    <>
      {loggedIn ? <LoggedInComp /> : <LoggedOutComp />}
    </>
  );
};

export default Navbar;
