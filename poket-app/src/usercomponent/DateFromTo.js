import axios from "axios";
import React, { useContext, useEffect } from "react";
import AxiosInstance from "../api/AxiosInstance";
import { DateContext, JWTContext, StatementContext } from "../Helper/Context";

export default function DateFromTo() {
  const PDF_URL = "/viewStatements/pdf/";
  const STATEMENT_URL = "/statement";
  const { startDate, setStartDate, endDate, setEndDate } =
    useContext(DateContext);
  const { accessToken } = useContext(JWTContext);
  const { statement, setStatement } = useContext(StatementContext);
  //   const [statement, setStatement] = useState([]);

  useEffect(() => {
    setStartDate(startDate);
  }, [startDate]);

  useEffect(() => {
    setEndDate(endDate);
  }, [endDate]);

 
  const download = () => {
    let parts = startDate.split("-");
    let mydate1 = parts[2] + "-" + parts[1] + "-" + parts[0];

    parts = endDate.split("-");
    let mydate2 = parts[2] + "-" + parts[1] + "-" + parts[0];
    axios({
      url: `http://localhost:8080/viewStatements/pdf/${mydate1}/${mydate2}`, //your url
      method: "GET",
      responseType: "blob", // important
      headers: {
        // "Content-Type": "application/pdf",
        Authorization: `Bearer ${accessToken}`,
      },
    }).then((response) => {
      const data = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = data;
      link.setAttribute("download", `${mydate1}To${mydate2}.pdf`); //or any other extension
      document.body.appendChild(link);
      link.click();
    });
  };
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
              disabled={statement.length == 0 ? true : false}
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
