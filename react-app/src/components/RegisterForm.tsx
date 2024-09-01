import React, { useState } from "react";
import axios from "axios";
import "../index.css";
import { useNavigate } from "react-router-dom";
import Alert from "./Alert";

export default function RegisterForm() {
  const navigate = useNavigate();
  const [showAlert, setShowAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");

  const [formData, setFormData] = useState({
    username: "",
    firstName: "",
    lastName: "",
    password: "",
    email: "",
    role: "STUDENT",
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
      axios.defaults.baseURL = "http://localhost:8080";
      const response = await axios.post("/user", formData);
      navigate("/");
      setFormData({
        username: "",
        firstName: "",
        lastName: "",
        password: "",
        email: "",
        role: "STUDENT",
      });
    } catch (error:any) {
      setAlertMessage(error.response.data.message);
      setShowAlert(true);
    }
  };

  return (
    <>
      {showAlert && <Alert type="danger">{alertMessage}</Alert>}
      <div className="grid">
        <form className="form login" onSubmit={handleSubmit}>
          <div className="form__field">
            <label htmlFor="username">Username:</label>
            <input
              className="form__input"
              type="text"
              name="username"
              id="username"
              value={formData.username}
              onChange={handleInputChange}
            />
          </div>
          <div className="form__field">
            <label htmlFor="firstName">First Name:</label>
            <input
              className="form__input"
              type="text"
              name="firstName"
              id="firstName"
              value={formData.firstName}
              onChange={handleInputChange}
            />
          </div>
          <div className="form__field">
            <label htmlFor="lastName">Last Name:</label>
            <input
              className="form__input"
              type="text"
              name="lastName"
              id="lastName"
              value={formData.lastName}
              onChange={handleInputChange}
            />
          </div>
          <div className="form__field">
            <label htmlFor="password">Password:</label>
            <input
              className="form__input"
              type="password"
              name="password"
              id="password"
              value={formData.password}
              onChange={handleInputChange}
            />
          </div>
          <div className="form__field">
            <label htmlFor="email">Email:</label>
            <input
              className="form__input"
              type="text"
              name="email"
              id="email"
              value={formData.email}
              onChange={handleInputChange}
            />
          </div>
          <div className="form__field">
            <label htmlFor="role">Role:</label>
            <select
              className="form__input"
              name="role"
              id="role"
              value={formData.role}
              onChange={handleInputChange}
            >
              <option value="STUDENT">Student</option>
              <option value="TEACHER">Teacher</option>
            </select>
          </div>
          <button className="btn btn-danger" type="submit">
            Register
          </button>
        </form>
      </div>
    </>
  );
}
