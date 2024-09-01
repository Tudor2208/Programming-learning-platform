import { useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import axios from "axios";
import ReactDOM from "react-dom";
import Alert from "./Alert";
import { useNavigate } from "react-router-dom";

export default function JoinClassroom() {
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    id: "",
    password: "",
  });

  const handleInputChange = (event: { target: { name: any; value: any } }) => {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value,
    });
  };

  const handleSubmit = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      const response = await axios.get(
        `http://localhost:8080/classroom/${formData.id}`
      );
      const classroom = response.data;

      if (classroom.password === formData.password) {
        const addStudentResponse = await axios.post(
          `http://localhost:8080/classroom/add-student/${classroom.id}/${user.id}`
        );
        navigate("/view-classrooms");
        
      } else {
        const errorMessage = "Incorrect password!";
        ReactDOM.render(
          <Alert type="warning">{errorMessage}</Alert>,
          document.getElementById("alert-root")
        );
      }
    } catch (error) {
      const errorMessage = "Invalid classroom ID!";
      ReactDOM.render(
        <Alert type="warning">{errorMessage}</Alert>,
        document.getElementById("alert-root")
      );
    }
  };

  return (
    <>
      <Banner />
      <Navbar active="profile" />
      <h2>Join a new classroom</h2>
      <br />
      <div className="grid">
        <form className="form login" onSubmit={handleSubmit}>
          <div className="form__field">
            <label htmlFor="name">Classroom ID:</label>
            <input
              className="form__input"
              type="text"
              name="id"
              id="id"
              value={formData.id}
              onChange={handleInputChange}
            />
          </div>

          <div className="form__field">
            <label htmlFor="name">Classroom password:</label>
            <input
              className="form__input"
              type="text"
              name="password"
              id="password"
              value={formData.password}
              onChange={handleInputChange}
            />
          </div>

          <button className="btn btn-danger" type="submit">
            Join
          </button>

          <div id="alert-root"></div>
        </form>
      </div>
    </>
  );
}
