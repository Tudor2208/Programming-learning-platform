import { useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Alert from "./Alert";

export default function ClassroomForm() {
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    description: "",
    password: "",
    owner: user,
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
      const response = await axios.post("/classroom", formData);

      setFormData({
        name: "",
        description: "",
        password: "",
        owner: "",
      });
    } catch (error) {
      console.error("Error creating classroom:", error);
    }

    navigate("/teacher");
  };

  return (
    <>
      <Banner />
      <Navbar active="teacher" />
      <h2>Create a new classroom</h2>
      <div className="grid">
        <form className="form login" onSubmit={handleSubmit}>
          <div className="form__field">
            <label htmlFor="name">Name:</label>
            <input
              className="form__input"
              type="text"
              name="name"
              id="name"
              value={formData.name}
              onChange={handleInputChange}
            />
          </div>

          <div className="form__field">
            <label htmlFor="name">Description:</label>
            <input
              className="form__input"
              type="text"
              name="description"
              id="description"
              value={formData.description}
              onChange={handleInputChange}
            />
          </div>

          <div className="form__field">
            <label htmlFor="name">Password:</label>
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
            Create
          </button>
        </form>
      </div>
    </>
  );
}
