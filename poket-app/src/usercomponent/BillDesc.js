import React, { useContext } from "react";
import { AddBillsContext } from "../Helper/Context";

export default function BillDesc() {
    const {category, setCategory}=useContext(AddBillsContext);
  return (
    <div>
      <center>
        <div className="container my-3">
          <label>Choose Category</label>
          <div className="container my-3">
            <div className="row offset-md-3">
              <div className="col-8">
                <select
                  id="mySelect"
                  data-show-content="true"
                  className="form-control"
                  onChange={(e)=>{
                    setCategory(e.target.value)
                  }}
                >
                  <option>Water Bill</option>
                  <option>Electricity Bill</option>
                  <option>Internet Bill</option>
                </select>
              </div>
            </div>
          </div>
        </div>
      </center>
    </div>
  );
}
