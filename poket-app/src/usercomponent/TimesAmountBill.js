import React, { useState } from "react";
import AmountBill from "./AmountBill";
import DateAndTime from "./DateAndTime";

export default function TimesAmountBill() {
  return (
    <div className="container my-5">
      <div className="row">
        <DateAndTime />
        <AmountBill />
      </div>
    </div>
  );
}
