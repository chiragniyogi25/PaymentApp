import React, { useContext, useEffect } from "react";
import AxiosInstance from "../api/AxiosInstance";
import { DateContext, JWTContext, StatementContext } from "../Helper/Context";

export default function DateFromTo() {
  const STATEMENT_URL = "/statement";
  const { startDate, setStartDate, endDate, setEndDate } =
    useContext(DateContext);
  const { accessToken } = useContext(JWTContext);
  const {  statement,setStatement } = useContext(StatementContext);
  //   const [statement, setStatement] = useState([]);

  useEffect(() => {
    setStartDate(startDate);
    let parts = startDate.split("-");
    let mydate = new Date(parts[0], parts[1] - 1, parts[2]);
  }, [startDate]);

  useEffect(() => {
    setEndDate(endDate);
    let parts = endDate.split("-");
    let mydate = new Date(parts[0], parts[1] - 1, parts[2]);
  }, [endDate]);

  const download=()=>{

  }
  const submitDate = async () => {
    try {
      const data = {
        from: startDate,
        to: endDate,
      };
      const response = await AxiosInstance.post(
        STATEMENT_URL,
        JSON.stringify(data), //JSON String
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      setStatement([...response.data]);
      console.log(response);
    } catch (err) {
      console.log(err.message);
    }
  };

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
                      <center className="text-bold">
                        <b>From</b>
                      </center>
                    </label>
                    <input
                      id="startDate"
                      className="form-control datepicker"
                      type="date"
                      value={startDate}
                      onChange={(e) => setStartDate(e.target.value)}
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
                      <center className="text-bold">
                        <b>To</b>
                      </center>
                    </label>
                    <input
                      id="endDate"
                      className="form-control datepicker"
                      type="date"
                      value={endDate}
                      onChange={(e) => setEndDate(e.target.value)}
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="col">
            <button
              disabled={startDate && endDate ? false : true}
              type="button"
              onClick={submitDate}
              className="btn btn-primary my-4"
            >
              Submit
            </button>
          </div>
          <div className="col">
            <button
              disabled={statement.length==0 ? true : false}
              type="button"
              onClick={download}
              className="btn btn-secondary my-4"
            >
              Download Statement
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
