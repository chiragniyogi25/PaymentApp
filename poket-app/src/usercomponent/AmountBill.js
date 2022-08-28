import React, { useContext, useState } from "react";
import AxiosInstance from "../api/AxiosInstance";
import { AddBillsContext, JWTContext } from "../Helper/Context";

export default function AmountBill() {
  const ADD_BILLS = "/recurring_payment";
  const {
    setErr,
    category,
    date,
    setDate,
    times,
    amount,
    setAmount,
    setErrMsg,
  } = useContext(AddBillsContext);
  const { accessToken } = useContext(JWTContext);

  const setUpBills = async () => {
    try {
      let arr = date.split("-");
      let date1=arr[2] + "-" + arr[1] + "-" + arr[0];
      setDate(date1);
      // console.log(arr);
      // console.log(date1);
      // console.log("Date "+date+" "+date.length+" "+typeof(date));
      const data = {
        description: category,
        amount: amount,
        startDate: date1,
        noOfTimes: times,
      };
      // console.log(date,typeof date);

      const response = await AxiosInstance.post(
        ADD_BILLS,
        JSON.stringify(data), //JSON String
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      setErr(false);
      setErrMsg("Bill Added Successfully");
    } catch (err) {
      // console.log("An Error Occurred");
      setErr(true);
      setErrMsg("An Error Occurred");
    }
  };

  const handleDecrement = () => {
    setAmount((prevCount) => parseInt(prevCount) - 1);
  };
  const handleIncrement = () => {
    setAmount((prevCount) => parseInt(prevCount) + 1);
  };
  return (
    <div
      className="card col-md-4 offset-md-1"
      style={{ width: "20rem", height: "auto" }}
    >
      <div className="card-body">
        <div className="card-header primary-background text-dark text-center">
          <span className="fa fa-credit-card fa-3x"></span>
          <br />
          <label htmlFor="num">Monthly Amount</label>
          <br />
          <div className="input-group">
            <button
              type="button"
              onClick={handleDecrement}
              className="input-group-text"
            >
              -
            </button>
            <input
              type="text"
              className="form-control text-center"
              onChange={(e) => setAmount(e.target.value)}
              value={amount}
            />
            <button
              type="button"
              onClick={handleIncrement}
              className="input-group-text"
            >
              +
            </button>
          </div>
        </div>
        <center>
          <button
            onClick={setUpBills}
            disabled={
              category.length > 0 &&
              date.length > 0 &&
              parseInt(times) > 0 &&
              parseInt(amount) > 0
                ? false
                : true
            }
            className="btn btn-primary my-2"
          >
            Submit
          </button>
        </center>
      </div>
    </div>
  );
}
