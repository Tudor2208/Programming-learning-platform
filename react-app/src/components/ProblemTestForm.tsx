import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

interface Problem {
  id: number;
  name: string;
  problemStatement: string;
  input: string;
  output: string;
  restrictions: string;
  difficulty: string;
  status: string;
}

export default function ProblemTestForm() {
  const navigate = useNavigate();
  const [problems, setProblems] = useState<Problem[]>([]);
  const [selectedProblem, setSelectedProblem] = useState<Problem | null>(problems[0] || null);


  const [formData, setFormData] = useState({
    input: "",
    output: "",
    problem: ""
  });

  useEffect(() => {
    fetch("http://localhost:8080/problem")
      .then((response) => response.json())
      .then((data) => setProblems(data));
  }, []);

  const handleInputChange = (event: { target: { name: any; value: any } }) => {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value,
    });
  };

  const handleProblemSelect = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const problemId = Number(event.target.value);
    const problem = problems.find((p) => p.id === problemId);
    setSelectedProblem(problem || null);
  };

  const handleSubmit = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    if (!selectedProblem) return;

    try {
      axios.defaults.baseURL = "http://localhost:8080";
      const response = await axios.post("/test", {
        ...formData,
        problem: selectedProblem.id,
      });

      setFormData({
        input: "",
        output: "",
        problem: ""
      });

      
    } catch (error) {
      console.error("Error creating problem test:", error);
    }

    window.location.reload();
  };

  return (
    <>
      <div className="grid">
        <form className="form login" onSubmit={handleSubmit}>
          <div className="form__field">
            <label htmlFor="name">Problem:</label>
            <select
              className="form__input"
              name="problem"
              id="problem"
              value={selectedProblem?.id ?? ""}
              onChange={handleProblemSelect}
            >
              <option value="">Select a problem...</option>
              {problems.map((problem) => (
                <option key={problem.id} value={problem.id}>
                  {problem.name}
                </option>
              ))}
            </select>
          </div>

          {selectedProblem && (
            <>
              <div className="form__field">
                <label htmlFor="input">Input:</label>
                <input
                  className="form__input"
                  type="text"
                  name="input"
                  id="input"
                  value={formData.input}
                  onChange={handleInputChange}
                />
              </div>

              <div className="form__field">
                <label htmlFor="output">Output:</label>
                <input
                  className="form__input"
                  type="text"
                  name="output"
                  id="output"
                  value={formData.output}
                  onChange={handleInputChange}
                />
              </div>
            </>
          )}

          <button className="btn btn-danger" type="submit">
            Add
          </button>
        </form>
      </div>
    </>
  );
}
