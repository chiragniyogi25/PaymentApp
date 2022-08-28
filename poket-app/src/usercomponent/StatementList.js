import React, { useContext } from "react";
import { StatementContext } from "../Helper/Context";
import StatementItems from "./StatementItems";

export default function StatementList() {
  const { statement } = useContext(StatementContext);

  return (
    <>
      <table className="table table-dark table-striped table-hover">
        <thead>
          <tr>
            <th scope="col">Description</th>
            <th scope="col">Date</th>
            <th scope="col">Time</th>
            <th scope="col">Amount</th>
            <th scope="col">Opening Balance</th>
            <th scope="col">Closing Balance</th>
            <th scope="col">Type</th>
          </tr>
        </thead>
        {
          statement.map((obj) => {
            return <StatementItems key={obj.transactionId} element={obj} />;
          })
        }
      </table>
    </>
  );
}
