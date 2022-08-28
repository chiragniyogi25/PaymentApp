import React, { useContext } from 'react';
import { AddBillsContext } from "../Helper/Context";

export default function DateAndTime() {
  const {date, setDate,times, setTimes} = useContext(AddBillsContext);
  return (
    <div
          className="card col-md-4 offset-md-2"
          style={{ width: "25rem", height: "auto" }}
        >
          <div className="card-body">
            <div className="row g-3 align-items-center">
              <div className="col-auto">
                <label htmlFor="inputDate" className="col-form-label">
                  Enter Date  
                </label>
              </div>
              <div className="col-auto">
                <input
                  type="date"
                  id="inputDate"
                  className="form-control"
                  value={date}
                  onChange={(e)=>setDate(e.target.value)}
                  aria-describedby="datdHelpInline"
                />
              </div>
            </div>
            <br/>
            <div className="row g-3 align-items-center">
              <div className="col-auto">
                <label htmlFor="inputTimes" className="col-form-label">
                  No of Times
                </label>
              </div>
              <div className="col-auto">
                <input
                  type="number"
                  id="inputTimes"
                  className="form-control"
                  value={times}
                  onChange={(e)=>setTimes(e.target.value)}
                  aria-describedby="inputHelpInline"
                />
              </div>
            </div>
          </div>
        </div>
  )
}
