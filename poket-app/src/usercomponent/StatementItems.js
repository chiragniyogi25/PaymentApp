import React from "react";

export default function StatementItems(props) {
  // 2022-08-15T09:32:55.000+00:00
  let d = (new Date(props.element.transactionDate))+"";
  let arr=d.split(' ');
  let date=arr[2]+" "+arr[1]+" "+arr[3];
  let time=arr[4];

  return (
    <tbody>
      <tr>
        <td>{props.element.description}</td>
        <td><small>{date}</small></td>
        <td><small>{time}</small></td>
        <td>{props.element.amount}</td>
        <td>{props.element.openingBal}</td>
        <td>{props.element.closingBal}</td>
        <td>{props.element.type}</td>
      </tr>
    </tbody>
  );
}
