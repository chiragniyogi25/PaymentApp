import React, { useEffect, useState } from "react";
import { AddBillsContext } from "../Helper/Context";
import BillDesc from "./BillDesc";
import MultipleBills from "./MultipleBills";
import TimesAmountBill from "./TimesAmountBill";

export default function AddBill() {
  const [category, setCategory] = useState("");
  const [date, setDate] = useState("");
  const [times, setTimes] = useState(0);
  const [amount, setAmount] = useState(0);
  const [err, setErr] = useState(false);
  const [errMsg, setErrMsg] = useState("");
  useEffect(() => {
    setErr(false);
    setErrMsg("");
  }, [date, times, amount, category]);
  return (
    <div>
      <center>
        <h1>SETUP YOUR MONTHLY BILLS</h1>
      </center>

      <div className="container-fluid my-3">
        <div className="row">
          <div className="col-md-4 offset-md-4">
            <center
              className={` ${
                errMsg.length === 0 || errMsg.trim() === ""
                  ? "hidden"
                  : err
                  ? "alert alert-danger"
                  : "alert alert-success"
              }`}
              role="alert"
              style={{ width: "30rem" }}
            >
              {errMsg}
            </center>
          </div>
        </div>
      </div>

      <AddBillsContext.Provider
        value={{
          category,
          setCategory,
          date,
          setDate,
          times,
          setTimes,
          amount,
          setAmount,
          err,
          setErr,
          errMsg,
          setErrMsg,
        }}
      >
        <BillDesc />

        <TimesAmountBill />
        <MultipleBills />
      </AddBillsContext.Provider>
    </div>
  );
}
