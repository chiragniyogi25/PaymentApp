import React, { useContext } from "react";
import { ViewBillContext } from "../Helper/Context";
import BillItem from "./BillItem";

export default function BillHeader() {
  const { viewBill } = useContext(ViewBillContext);

  return (
    <table className="table table-dark table-striped table-hover">
      <thead>
        <tr>
          <th scope="col">Paying For</th>
          <th scope="col">Paying Date</th>
          <th scope="col">Amount</th>
          <th scope="col">Months to Pay</th>
        </tr>
      </thead>
      {viewBill.map((obj) => {
        return <BillItem key={obj.reccId} element={obj} />;
      })}
    </table>
  );
}
