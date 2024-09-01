import { useNavigate, useParams } from "react-router-dom";
import Banner from "./Banner";
import Navbar from "./Navbar";
import { useEffect, useState } from "react";
import { RiDeleteBin2Line, RiDeleteBin6Fill } from "react-icons/ri";

interface Classroom {
  id: number;
  name: string;
  description: string;
  password: string;
}

interface User {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  role: string;
}

interface Homework {
  id: number;
  deadline: string;
  problems: Problem[];
}

interface Problem {
  id: number;
  name: string;
}

export default function ClassroomDetails() {
  const { id } = useParams<{ id: string }>();
  const [classroom, setClassroom] = useState<Classroom | null>(null);
  const [members, setMembers] = useState<User[]>([]);
  const [homeworks, setHomeworks] = useState<Homework[]>([]);
  const [problems, setProblems] = useState<Problem[]>([]);
  const [resolvedData, setResolvedData] = useState<any[][]>([]);

  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;
  const navigate = useNavigate();

  useEffect(() => {
    fetch(`http://localhost:8080/classroom/${id}`)
      .then((response) => response.json())
      .then((data) => setClassroom(data));
  }, [id]);

  useEffect(() => {
    fetch(`http://localhost:8080/classroom/members/${id}`)
      .then((response) => response.json())
      .then((data) => setMembers(data));
  }, [id]);

  useEffect(() => {
    fetch(`http://localhost:8080/homework/classroom/${id}`)
      .then((response) => response.json())
      .then((data) => setHomeworks(data));
  }, [id]);

  useEffect(() => {
    fetch(`http://localhost:8080/problem`)
      .then((response) => response.json())
      .then((data) => setProblems(data));
  }, []);
  useEffect(() => {
    const fetchProblemsSolved = async (userId: number, homeworkId: number) => {
      try {
        const response = await fetch(
          `http://localhost:8080/homework/nr-problems/${userId}/${homeworkId}`
        );
        const data = await response.json();
        return data;
      } catch (error) {
        console.error("Error fetching data:", error);
        throw error;
      }
    };

    const fetchData = async () => {
      try {
        const resolvedData = await Promise.all(
          members.map((member) =>
            Promise.all(
              homeworks.map((homework) =>
                fetchProblemsSolved(member.id, homework.id)
              )
            )
          )
        );
        setResolvedData(resolvedData);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [members, homeworks]);

  const handleDeleteMember = async (memberId:number) => {
    try {
      const response = await fetch(`http://localhost:8080/classroom/remove-student/${id}/${memberId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      });

    } catch (error) {
      console.error('Error deleting homework:', error);
     
    }

    window.location.reload();
  };

  const handleDeleteHomework = async (homeworkId:number) => {
    try {
      const response = await fetch(`http://localhost:8080/homework/${homeworkId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      });

    } catch (error) {
      console.error('Error deleting homework:', error);
     
    }

    window.location.reload();
  };
  

  const handleAdd = () => {
    navigate(`/add-homework/${id}`);
  };

  return (
    <>
      <Banner />
      <Navbar active="profle" />
      <h1>{classroom?.name}</h1>
      <div className="content">
        <div>
          <p className="type">Classroom ID:</p>
          <p className="details">#{classroom?.id}</p>
        </div>

        <div>
          <p className="type">Description:</p>
          <p className="details">{classroom?.description}</p>
        </div>

        {user.role === "TEACHER" && (
          <div>
            <p className="type">Password:</p>
            <p className="details">{classroom?.password}</p>
          </div>
        )}

        <div>
          <p className="type">Members ({members.length}):</p>
          <table>
            <tr>
              <th>Username</th>
              <th>First name</th>
              <th>Last name</th>
              {user.role === "TEACHER" && <th>Remove</th>}
              {homeworks.map((homework, index) => (
                <th key={homework.deadline}>H{index + 1}</th>
              ))}
            </tr>

            {members.map((member) => (
              <tr>
                {member.role === "TEACHER" && (
                  <td>
                    {member.username}
                    <span id="teacher"> (TEACHER)</span>
                  </td>
                )}
                {member.role != "TEACHER" && <td>{member.username}</td>}

                <td>{member.firstName}</td>
                <td>{member.lastName}</td>

                {user.role === "TEACHER" &&
                  user.username != member.username && (
                    <td>
                      <button className="delete-btn" onClick={()=>handleDeleteMember(member.id)}>
                        <RiDeleteBin2Line />
                      </button>
                    </td>
                  )}

                {user.role === "TEACHER" &&
                  user.username === member.username && <td>-</td>}

                {homeworks.map((homework) => {
                  const memberIndex = members.indexOf(member);
                  const homeworkIndex = homeworks.indexOf(homework);
                  const solvedProblems =
                    resolvedData.length > 0
                      ? resolvedData[memberIndex][homeworkIndex]
                      : null;
                  const totalProblems = homework.problems.length;
                  const displayText =
                    solvedProblems !== null
                      ? `${solvedProblems}/${totalProblems}`
                      : "-";

                  return (
                    <td
                      key={homework.id}
                      className={
                        solvedProblems === totalProblems ? "green-text" : ""
                      }
                    >
                      {displayText}
                    </td>
                  );
                })}
              </tr>
            ))}
          </table>
        </div>

        <div>
          <p className="type">Homeworks:</p>
          {homeworks && homeworks.length > 0 ? (
            <table>
              <thead>
                <tr>
                  <th>Homework Nr</th>
                  <th>Deadline</th>
                  <th>Problems</th>
                  {user.role === "TEACHER" && <th>Delete</th>}
                </tr>
              </thead>
              <tbody>
                {homeworks.map((homework, index) => (
                  <tr key={homework.deadline}>
                    <td>{index + 1}</td>
                    <td>{new Date(homework.deadline).toLocaleDateString()}</td>
                    <td>
                      {homework.problems.map((problem) => (
                        <p id="prob">
                          {problem.name}{" "}
                          <span id="probid">(#{problem.id})</span>{" "}
                        </p>
                      ))}
                    </td>

                    {user.role === "TEACHER" && (
                      <td>
                        <button className="delete-btn" onClick={() => handleDeleteHomework(homework.id)}>
                          <RiDeleteBin6Fill />
                        </button>
                      </td>
                    )}
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p>No homeworks available</p>
          )}
        </div>

        {user.role === "TEACHER" && (
          <button className="btn btn-danger" onClick={handleAdd}>
            Add homework
          </button>
        )}
      </div>
    </>
  );
}
