import React from "react";

export default function BillItem(props) {
  let d = new Date(props.element.startDate) + "";
  let arr = d.split(" ");
  let date = arr[2] + " " + arr[1] + " " + arr[3];
  return (
    <tbody>
      <tr>
        <td>{props.element.description}</td>
        <td>{date}</td>
        <td>{props.element.amount}</td>
        <td>{props.element.noOfTimes}</td>
      </tr>
    </tbody>
  );
}
