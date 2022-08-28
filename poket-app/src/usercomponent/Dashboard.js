import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import AxiosInstance from "../api/AxiosInstance";
import { ViewBillContext } from "../Helper/Context";
import ViewBalance from "./ViewBalance";
import ViewBill from "./ViewBill";

export default function Dashboard() {
  const [viewBill, setViewBill] = useState([]);
  // const { accessToken } = useContext(JWTContext);
  const VIEW_BILLS = "/recurring_payment";
  //ViewBillContext
  useEffect(() => {
    const accessToken = localStorage.getItem("token");
    async function fetchBills() {
      try {
        const response = await AxiosInstance.get(VIEW_BILLS, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        });
        setViewBill([...response.data]);
        // console.log(response.data);
      } catch (err) {
        console.log(err);
        console.log(err.message);
      }
    }
    fetchBills();
  }, []);
  return (
    <>
      <div className="row">
        <div className="col-sm-6 mx-4 my-5">
          <ViewBillContext.Provider value={{ viewBill, setViewBill }}>
            <div className="card-body">
              <h4>Your Pending Bills</h4>
              {viewBill.length == 0 ? (
                <center>
                  <h5>No Pending Bills Found</h5>
                  <Link className="disabledCursor" to="/addBills">
                    Add Bills
                  </Link>
                </center>
              ) : (
                <ViewBill />
              )}
            </div>
          </ViewBillContext.Provider>
        </div>

        <div className="col-sm-5 my-3">
          <ViewBalance/>
        </div>
      </div>
    </>
  );
}
