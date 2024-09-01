import { Link } from "react-router-dom";
import "../Problem.css";
import { AiFillStar } from "react-icons/ai";
import { useEffect, useState } from "react";

interface Props {
  id: number;
  name: string;
  text: string;
  difficulty: string;
}

export default function Problem({ id, difficulty, name, text }: Props) {
  const [scoreMax, setScore] = useState<number | 0>(0);
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;
  const userID = user ? user.id : null;

  useEffect(() => {
    fetch(`http://localhost:8080/solution/maxscore/${id}/${userID}`)
      .then((response) => response.json())
      .then((data) => setScore(data));
  }, []);

  return (
    <div className="prob-container">
      <div className="card">
        <div className="card-header">
          {difficulty === "EASY" && <AiFillStar />}
          {difficulty === "MEDIUM" && (
            <>
              <AiFillStar /> <AiFillStar />
            </>
          )}
          {difficulty === "HARD" && (
            <>
              <AiFillStar /> <AiFillStar /> <AiFillStar />
            </>
          )}
          {difficulty === "COMPETITION" && (
            <>
              <AiFillStar /> <AiFillStar /> <AiFillStar /> <AiFillStar />
            </>
          )}
        </div>
        <div className="card-body">
          <h5 className="card-title">{name}  (#{id})</h5>
          <p className="card-text">{text}</p>

          {scoreMax < 0 && (
            <Link
              to={"/problem/" + id}
              id="card-btn"
              className="btn btn-danger"
            >
              Rezolva
            </Link>
          )}

          {scoreMax >= 0 && scoreMax < 20 && (
            <Link
              to={"/problem/" + id}
              id="card-btn1"
              className="btn btn-danger"
            >
              Rezolva
              <span> [{scoreMax} %]</span>
            </Link>
          )}

          {scoreMax > 20 && scoreMax < 80 && (
            <Link
              to={"/problem/" + id}
              id="card-btn2"
              className="btn btn-danger"
            >
              Rezolva
              <span> [{scoreMax} %]</span>
            </Link>
          )}

          {scoreMax > 80 && scoreMax < 100 && (
            <Link
              to={"/problem/" + id}
              id="card-btn3"
              className="btn btn-danger"
            >
              Rezolva
              <span> [{scoreMax} %]</span>
            </Link>
          )}

          {scoreMax === 100 && (
            <Link
              to={"/problem/" + id}
              id="card-btn4"
              className="btn btn-danger"
            >
              Rezolva
              <span> [{scoreMax} %]</span>
            </Link>
          )}
        </div>
      </div>
    </div>
  );
}
