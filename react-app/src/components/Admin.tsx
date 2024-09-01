import React, { useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import "../Admin.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { saveAs } from "file-saver";
import { RiDingdingFill, RiGoogleFill } from "react-icons/ri";

export default function Admin() {
  const navigate = useNavigate();
  const [reportType, setReportType] = useState("");

  const handleUsers = () => {
    navigate("/admin/users");
  };

  const handleCategories = () => {
    navigate("/admin/categories");
  };

  const handleProblems = () => {
    navigate("/admin/problems");
  };

  const handleTests = () => {
    navigate("/admin/tests");
  };

  const handleExamples = () => {
    navigate("/admin/examples");
  };

  const handleViewPendingProblems = () => {
    navigate("/admin/pending-problems");
  };

  const handleReports = () => {
    if (reportType === "") {
      alert("You haven't selected any type!");

      return;
    }

    axios.defaults.baseURL = "http://localhost:8080";

    axios
      .get(`/report/${reportType}`, {
        responseType: "blob",
        headers: {
          "Content-Type": "application/octet-stream",
        },
      })
      .then((response) => {
        const blob = new Blob([response.data], {
          type: response.headers["content-type"],
        });
        saveAs(blob, `admin-report.${reportType}`);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleReportTypeChange = (event: {
    target: { value: React.SetStateAction<string> };
  }) => {
    setReportType(event.target.value);
  };

  const handleViewActivity = () => {
    navigate("/admin/activity");
  };

  return (
    <>
      <Banner />
      <Navbar active="Admin" />
      <h2>Admin panel</h2>
      <div className="container">
        <table>
          <tr>
            <th>
              <button
                type="button"
                className="btn btn-danger"
                onClick={handleUsers}
              >
                Users
              </button>
            </th>

            <th>
              <button
                type="button"
                className="btn btn-danger"
                onClick={handleProblems}
              >
                Add problem
              </button>
            </th>
          </tr>
          <tr>
            <td>
              <button
                type="button"
                className="btn btn-danger"
                onClick={handleCategories}
              >
                Categories
              </button>
            </td>
            <td>
              <button
                type="button"
                className="btn btn-danger"
                onClick={handleReports}
              >
                Generate report
              </button>
            </td>
          </tr>
          <tr>
            <td>
              <button type="button" className="btn btn-danger">
                Solutions
              </button>
            </td>

            <td>
              <button
                onClick={handleTests}
                type="button"
                className="btn btn-danger"
              >
                Add problem tests
              </button>
            </td>
          </tr>

          <tr>
            <td>
              <button
                onClick={handleExamples}
                type="button"
                className="btn btn-danger"
              >
                Add problem examples
              </button>
            </td>

            <td>
              <button
                onClick={handleViewActivity}
                type="button"
                className="btn btn-danger"
              >
                View users activity
              </button>
            </td>
          </tr>

          <tr>
            <td>
              <button
                onClick={handleViewPendingProblems}
                type="button"
                className="btn btn-danger"
              >
                View pending problems
              </button>
            </td>
          </tr>
        </table>
      </div>

      <div className="report-dialog">
        <h3>Choose report type:</h3>
        <div className="radio-group">
          <label className="file-type">
            <input
              type="radio"
              value="TXT"
              checked={reportType === "TXT"}
              onChange={handleReportTypeChange}
            />
            TXT
          </label>
          <label className="file-type">
            <input
              type="radio"
              value="XML"
              checked={reportType === "XML"}
              onChange={handleReportTypeChange}
            />
            XML
          </label>
        </div>
      </div>
    </>
  );
}
