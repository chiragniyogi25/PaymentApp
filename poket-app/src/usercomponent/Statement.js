import React, {  useState } from "react";
import { StatementContext } from "../Helper/Context";
import DateFromTo from "./DateFromTo";
import StatementList from "./StatementList";

export default function Statement() {
  const [statement, setStatement] = useState([]);
  return (
    <>
      <StatementContext.Provider value={{ statement, setStatement }}>
        <div>
          <DateFromTo />
        </div>
        {statement.length === 0 ? (
          <div>Please Select Date</div>
        ) : (
          <div>
            <StatementList />
          </div>
        )}
      </StatementContext.Provider>
    </>
  );
}
