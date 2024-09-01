import { useEffect, useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import Problem from "./Problem";

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

export default function Problems() {
  const [problems, setProblems] = useState<Problem[]>([]);
  const [searchId, setSearchId] = useState("");
  const [searchName, setSearchName] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/problem/approved")
      .then((response) => response.json())
      .then((data) => setProblems(data));
  }, []);

  const handleSearchId = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchId(event.target.value);
  };

  const handleSearchName = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchName(event.target.value);
  };

  const filteredProblems = problems.filter((problem) => {
    const matchId = problem.id.toString().includes(searchId);
    const matchName = problem.name
      .toLowerCase()
      .includes(searchName.toLowerCase());
    return (searchId === "" || matchId) && (searchName === "" || matchName);
  });

  return (
    <>
      <Banner />
      <Navbar active="Problems" />
      <div>
        <h4>Search a problem..</h4>
        <div style={{ display: "flex", marginBottom: "10px" }}>
          <input
            type="text"
            placeholder="Problem ID"
            value={searchId}
            onChange={handleSearchId}
            style={{ marginRight: "10px", marginLeft: "500px" }}
          />
          <input
            type="text"
            placeholder="Problem name"
            value={searchName}
            onChange={handleSearchName}
          />
        </div>
      </div>
      <ul className="mylist">
        {filteredProblems.map((problem) => (
          <li className="myitem">
            <Problem
              name={problem.name}
              text={problem.problemStatement}
              difficulty={problem.difficulty}
              id={problem.id}
            />
          </li>
        ))}
      </ul>
    </>
  );
}
