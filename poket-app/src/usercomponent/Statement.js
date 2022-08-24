import React, { useEffect, useState } from "react";

export default function Statement() {
  const [startDate,setStartDate]=useState();
  const [endDate,setEndDate]=useState();

  useEffect(()=>{
    setStartDate(startDate);
    console.log(typeof (startDate));
  },[startDate]);

  useEffect(()=>{
    setEndDate(endDate);
    console.log(typeof (endDate));
  },[endDate]);

  return (
    <div>
      <div className="container">
        <div className="row">
          <div className="col">
            <div className="container my-3 ">
              <div className="row">
                <div className="col-md-3">
                  <div
                    className="card"
                    style={{ width: "20rem", height: "auto" }}
                  >
                    <label htmlFor="startDate">
                      <center className="text-bold">From</center>
                    </label>
                    <input
                      id="startDate"
                      className="form-control"
                      type="date"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="col">
            <div className="container my-3">
              <div className="row">
                <div className="col-md-3">
                  <div
                    className="card"
                    style={{ width: "20rem", height: "auto" }}
                  >
                    <label htmlFor="endDate">
                      <center className="text-bold">To</center>
                    </label>
                    <input id="endDate" className="form-control" type="date" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
