import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

class ProblemCategory {
  id: number;
  grade: string;
  name: string;
  problems: any;

  constructor(id: number, name: string, grade: string) {
    this.id = id;
    this.name = name;
    this.grade = grade;
    this.problems = [];
  }
}

interface FormData {
  name: string;
  problemStatement: string;
  input: string;
  output: string;
  restrictions: string;
  difficulty: string;
  status: string;
  author: any;
  categories: ProblemCategory[];
}

export default function ProblemForm() {
  const navigate = useNavigate();
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;

  const [categories, setCategories] = useState<ProblemCategory[]>([]);
  let [selectedCategories, _] = useState<ProblemCategory[]>([]);

  const [formData, setFormData] = useState<FormData>({
    name: "",
    problemStatement: "",
    input: "",
    output: "",
    restrictions: "",
    difficulty: "",
    status: "PENDING",
    author: user,
    categories: [],
  });

  const handleInputChange = (event: { target: { name: any; value: any } }) => {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value,
    });
  };

  const handleCategoryChange = (event: {
    target: { name: any; value: any };
  }) => {
    console.log(selectedCategories);

    let categoryElements = String(event.target.name).split(",");

    let pc = new ProblemCategory(
      Number(categoryElements[0]),
      categoryElements[1],
      categoryElements[2]
    );
    let findCategories = selectedCategories.find((category) => category === pc);

    if (findCategories === undefined) {
      selectedCategories.push(pc);
    } else {
      selectedCategories = selectedCategories.filter(
        (category) => category != pc
      );
    }

    formData.categories = selectedCategories;

    console.log(selectedCategories);
    console.log(formData);
  };

  const handleSubmit = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      axios.defaults.baseURL = "http://localhost:8080";
      const response = await axios.post("/problem", formData);
      console.log(response.data);
      setFormData({
        name: "",
        problemStatement: "",
        input: "",
        output: "",
        restrictions: "",
        difficulty: "",
        status: "",
        author: "",
        categories: [],
      });
    } catch (error) {
      console.error("Error creating problem:", error);
    }
    window.location.reload();
  };

  useEffect(() => {
    fetch("http://localhost:8080/category")
      .then((response) => response.json())
      .then((data) => setCategories(data));
  }, []);

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
            <label htmlFor="problemStatement">Problem Statement:</label>
            <textarea
              className="form__input"
              name="problemStatement"
              id="problemStatement"
              value={formData.problemStatement}
              onChange={handleInputChange}
              rows={4}
              cols={70}
            />
          </div>

          <div className="form__field">
            <label htmlFor="input">Input:</label>
            <textarea
              className="form__input"
              name="input"
              id="input"
              value={formData.input}
              onChange={handleInputChange}
              rows={4}
              cols={70}
            />
          </div>

          <div className="form__field">
            <label htmlFor="output">Output:</label>
            <textarea
              className="form__input"
              name="output"
              id="output"
              value={formData.output}
              onChange={handleInputChange}
              rows={4}
              cols={70}
            />
          </div>

          <div className="form__field">
            <label htmlFor="restrictions">Restrictions:</label>
            <textarea
              className="form__input"
              name="restrictions"
              id="restrictions"
              value={formData.restrictions}
              onChange={handleInputChange}
              rows={4}
              cols={70}
            />
          </div>

          <div className="form__field">
            <label htmlFor="difficulty">Difficulty:</label>
            <select
              className="form__input"
              name="difficulty"
              id="difficulty"
              value={formData.difficulty}
              onChange={handleInputChange}
            >
              <option value="EASY">Easy</option>
              <option value="MEDIUM">Medium</option>
              <option value="HARD">Hard</option>
              <option value="COMPETITION">Competition</option>
            </select>
          </div>

          <div className="form__field">
            <label htmlFor="categories">Categories: </label>
            {categories.map((category) => (
              <div>
                <label>{category.name}</label>
                <input
                  id="btn-check"
                  type="checkbox"
                  name={
                    category.id.toString() +
                    "," +
                    category.name +
                    "," +
                    category.grade
                  }
                  onChange={handleCategoryChange}
                ></input>
              </div>
            ))}
          </div>

          <button className="btn btn-danger" type="submit">
            Add
          </button>
        </form>
      </div>
    </>
  );
}
