import React, { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import AxiosInstance from "../api/AxiosInstance";
import { JWTContext } from "../Helper/Context";

export default function AddMoney() {
  const ADD_MONEY = "/addMoney";
  const [amt, setAmt] = useState(0);
  const [flag, setFlag] = useState(false);
  const [amtMsg, setAmtMsg] = useState("");
  const { accessToken } = useContext(JWTContext);
  const navigate = useNavigate();
  const goToStatement = () => {
    navigate("/viewStatement");
  };
  useEffect(() => {
    setAmtMsg("");
  }, [amt]);
  const addTheMoney = async () => {
    try {
      const data = {
        amount: amt,
      };
      const reponse = await AxiosInstance.put(ADD_MONEY, JSON.stringify(data), {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      });
      setAmtMsg("Money Added Successfully");
      setFlag(true);
    } catch (err) {
      setAmtMsg("Money Not Added");
      setFlag(false);
    }
  };
  return (
    <div>
      <div
        className="card offset-md-3 my-5"
        style={{ width: "30rem", height: "auto" }}
      >
        <center>
          <div className="card-header">Add Money</div>
          <div className="card-body">
            <h5 className="card-title">Enter Amount to add</h5>
            <input
              value={amt}
              onChange={(e) => setAmt(e.target.value)}
              type="number"
              className="card-text"
            />
            <br />

            <button
              onClick={addTheMoney}
              className="btn btn-primary my-3 mx-3"
              disabled={amt > 0 ? false : true}
            >
              Add
            </button>

            <button onClick={goToStatement} className="btn btn-secondary">
              Statement
            </button>
          </div>
        </center>
      </div>
      <div className="container-fluid my-3">
        <div className="row">
          <div className="col-md-4 offset-md-3">
            <center
              className={` ${
                amtMsg.length <= 0
                  ? "hidden"
                  : flag
                  ? "alert alert-success"
                  : "alert alert-danger"
              }`}
              role="alert"
              style={{ width: "30rem" }}
            >
              {amtMsg}
            </center>
          </div>
        </div>
      </div>
      ;
    </div>
  );
}
