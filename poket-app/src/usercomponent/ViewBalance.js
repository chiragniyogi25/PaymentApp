import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import AxiosInstance from "../api/AxiosInstance";

export default function ViewBalance() {
  const navigate = useNavigate();
  const VIEW_BALANCE = "/balance";
  const [balance,setBalance]=useState(0);
  //ViewBillContext
  useEffect(() => {
    const accessToken = localStorage.getItem("token");
    async function fetchAmount() {
      try {
        const response = await AxiosInstance.get(VIEW_BALANCE, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setBalance(response.data);
      } catch (err) {
        console.log(err);
        console.log(err.message);
      }
    }
    fetchAmount();
  }, []);
  return (
    <div className="card my-3" style={{ width: "20rem", height: "auto" }}>
      <center>
        <div className="card-header">Your Balance</div>
        <div className="card-body">
          <h4 className="card-title">
            <i className="fa fa-inr">{balance}</i>
          </h4>

          <button
            onClick={() => navigate("/addMoney")}
            className="btn btn-primary my-3 mx-3"
          >
            Add Money
          </button>
          <br />

          <button
            onClick={() => navigate("/viewStatement")}
            className="btn btn-secondary"
          >
            View Statement
          </button>
        </div>
      </center>
    </div>
  );
}
