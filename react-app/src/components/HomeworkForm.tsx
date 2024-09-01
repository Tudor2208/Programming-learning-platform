import { useState, useEffect } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import Alert from "./Alert";

interface Classroom {
  id: number;
  name: string;
  description: string;
  password: string;
}

export default function HomeworkForm() {
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;
  const navigate = useNavigate();
  const { id } = useParams<{ id: string }>();
  const [problems, setProblems] = useState<{ id: number; name: string }[]>([]);
  const [classroom, setClassroom] = useState<Classroom | null>(null);

  useEffect(() => {
    fetch(`http://localhost:8080/classroom/${id}`)
      .then((response) => response.json())
      .then((data) => setClassroom(data));
  }, [id]);

  const [formData, setFormData] = useState({
    deadline: "",
    classroom: classroom,
    problems: [] as string[],
  });

  useEffect(() => {
    axios
      .get("http://localhost:8080/problem")
      .then((response) => setProblems(response.data))
      .catch((error) => console.error("Error fetching problems:", error));
  }, []);

  const handleInputChange = (event: { target: { name: any; value: any } }) => {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value,
    });
  };

  const handleProblemSelect = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedOptions = Array.from(
      event.target.selectedOptions,
      (option) => option.value
    );
    setFormData({
      ...formData,
      classroom: classroom, // Include the classroom data
      problems: selectedOptions,
    });
  };

  const handleSubmit = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      axios.defaults.baseURL = "http://localhost:8080";
      const problemIds = formData.problems.join(",");

      const response = await axios.post(`/homework/${problemIds}`, formData);

      setFormData({
        deadline: "",
        classroom: classroom,
        problems: [],
      });

      console.log(formData);
    } catch (error) {
      console.error("Error creating homework:", error);
    }

    navigate(`/view-classrooms/${id}`);
  };

  return (
    <>
      <Banner />
      <Navbar active="teacher" />
      <h2>Create a new homework</h2>
      <h3>({classroom?.name})</h3>

      <div className="grid">
        <form className="form login" onSubmit={handleSubmit}>
          <div className="form__field">
            <label htmlFor="deadline">Deadline:</label>
            <input
              className="form__input"
              type="text"
              name="deadline"
              id="deadline"
              value={formData.deadline}
              onChange={handleInputChange}
            />
          </div>

          <div className="form__field">
            <label htmlFor="problems">Select Problems:</label>
            <select
              className="form__input"
              name="selectedProblems"
              id="problems"
              multiple
              value={formData.problems}
              onChange={handleProblemSelect}
            >
              {problems.map((problem) => (
                <option key={problem.id} value={problem.id}>
                  {problem.name}
                </option>
              ))}
            </select>
          </div>

          <button className="btn btn-danger" type="submit">
            Create
          </button>
        </form>
      </div>
    </>
  );
}
