import { useParams } from "react-router-dom";
import Banner from "./Banner";
import Navbar from "./Navbar";
import { useEffect, useRef, useState } from "react";
import "../ProblemDetails.css";

interface User {
  firstName: string;
  lastName: string;
}

interface Problem {
  id: number;
  author: User;
  name: string;
  problemStatement: string;
  input: string;
  output: string;
  restrictions: string;
  difficulty: string;
  status: string;
}

interface ProblemExample {
  input: string;
  output: string;
  explanation: string;
}

export default function ProblemDetails() {
  const [problem, setProblem] = useState<Problem | null>(null);
  const [problemExamples, setProblemExamples] = useState<ProblemExample[]>([]);
  const [isHomeworkExpired, setIsHomeworkExpired] = useState<boolean>(false);

  const { id } = useParams<{ id: string }>();
  const solutionRef = useRef<HTMLTextAreaElement>(null);
  const [solutionScore, setSolutionScore] = useState<number | null>(null);
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;
  const userID = user ? user.id : null;

  useEffect(() => {
    fetch(`http://localhost:8080/problem/${id}`)
      .then((response) => response.json())
      .then((data) => setProblem(data));
  }, [id]);

  useEffect(() => {
    fetch(`http://localhost:8080/homework/check-expired/${userID}/${id}`)
      .then((response) => response.json())
      .then((data) => setIsHomeworkExpired(data));
  }, [id]);

  useEffect(() => {
    fetch(`http://localhost:8080/example/problem/${id}`)
      .then((response) => response.json())
      .then((data) => setProblemExamples(data));
  }, [id]);

  return (
    <>
      <Banner />
      <Navbar active="Problems" />
      {problem && <h1>{problem.name}</h1>}

      <div className="content">
        <div>
          <p className="type">Statement:</p>
          <p className="details"> {problem?.problemStatement}</p>
        </div>

        <div>
          <p className="type">Input:</p>
          <p className="details"> {problem?.input}</p>
        </div>

        <div>
          <p className="type">Output:</p>
          <p className="details"> {problem?.output}</p>
        </div>

        <div>
          <p className="type">Restrictions:</p>
          <p className="details"> {problem?.restrictions}</p>
        </div>

        <div>
          <p className="type">Author:</p>
          <p className="details">
            {problem?.author.firstName + " " + problem?.author.lastName}
          </p>
        </div>

        <div>
          <p className="type">Examples: </p>
          <table>
            <tr>
              <th>Input</th>
              <th>Output</th>
              <th>Explanation</th>
            </tr>

            {problemExamples.map((example) => (
              <tr>
                <td>{example.input}</td>
                <td>{example.output}</td>
                <td>{example.explanation}</td>
              </tr>
            ))}
          </table>
        </div>

        <div>
          <p className="type">Your solution goes here:</p>
          <textarea
            className="problem-textarea"
            ref={solutionRef}
            rows={10}
            cols={100}
          ></textarea>
        </div>

        {isHomeworkExpired && (<><p id = "warning">You can't submit this problem right now! </p> <p id = "warning">One of your homeworks has expired</p></>)}
        {!isHomeworkExpired && (
          <button
            className="btn btn-danger"
            onClick={() => {
              fetch(`http://localhost:8080/solution/evaluate/${id}/${userID}`, {
                method: "POST",
                body: solutionRef.current?.value,
              })
                .then((response) => response.json())
                .then((data) => {
                  setSolutionScore(data);
                })
                .catch((error) => {
                  console.log(error);
                });
            }}
          >
            Add solution
          </button>
        )}
      </div>
      {solutionScore !== null && solutionScore < 20 && solutionScore != -1 && (
        <label className="label-red">Score: {solutionScore}%</label>
      )}

      {solutionScore !== null && solutionScore > 20 && solutionScore < 80 && (
        <label className="label-yellow">Score: {solutionScore}%</label>
      )}

      {solutionScore !== null && solutionScore > 80 && solutionScore < 100 && (
        <label className="label-green2">Score: {solutionScore}%</label>
      )}

      {solutionScore !== null && solutionScore === 100 && (
        <label className="label-green">Score: {solutionScore}%</label>
      )}

      {solutionScore === -1 && (
        <label className="label-red">Compilation error!</label>
      )}
    </>
  );
}
