import axios from "axios";
import React, { useContext, useState } from "react";
import { JWTContext } from "../Helper/Context";

export default function MultipleBills() {
  const [UPLOAD_URL] = "/addBills/upload";
  const [file, setFile] = useState(null);
  const { accessToken } = useContext(JWTContext);

  const submitFile = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("file", file);
    console.log([...formData]);
    try {
      const response = await axios({
        method: "post",
        url: `http://localhost:8080/api/upload?file=${file}`,
        data: formData,
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${accessToken}`,
        },
      });
      console.log(response);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="input-group container offset-md-5">
      <form onSubmit={submitFile}>
        <input
          type="file"
          className="form-control"
          id="inputGroupFile04"
          aria-describedby="inputGroupFileAddon04"
          onChange={(e) => setFile(e.target.files[0])}
          aria-label="Upload"
        />
        <center>
          <button
            className="btn btn-primary my-3"
            type="submit"
            id="inputGroupFileAddon04"
          >
            Button
          </button>
        </center>
      </form>
    </div>
  );
}
