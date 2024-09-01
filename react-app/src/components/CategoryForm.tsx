import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function CategoryForm() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    grade: "",
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
      const response = await axios.post("/category", formData);

      setFormData({
        name: "",
        grade: "",
      });
    } catch (error) {
      console.error("Error creating category:", error);
    }

    window.location.reload();
  };

  return (
    <>
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
            <label htmlFor="grade">Grade:</label>
            <select
              className="form__input"
              name="grade"
              id="grade"
              value={formData.grade}
              onChange={handleInputChange}
            >
        
              <option value="GRADE_9">9</option>
              <option value="GRADE_10">10</option>
              <option value="GRADE_11">11</option>
            </select>
          </div>
          <button className="btn btn-danger" type="submit">
            Add
          </button>
        </form>
      </div>
    </>
  );
}
