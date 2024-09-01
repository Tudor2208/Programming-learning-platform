import { useEffect, useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import { RiEyeLine } from "react-icons/ri";
import { Link } from "react-router-dom";

interface Classroom {
  id: number;
  name: string;
  description: string;
  password: string;
}

export default function ViewClassrooms() {
  const userSting = localStorage.getItem("user");
  const user = userSting ? JSON.parse(userSting) : null;
  const userRole = user ? user.role : null;

  const [classrooms, setClassrooms] = useState<Classroom[]>([]);

  useEffect(() => {
    if (userRole === "TEACHER") {
      fetch(`http://localhost:8080/classroom/owner/${user.id}`)
        .then((response) => response.json())
        .then((data) => setClassrooms(data));
    } else {
      fetch(`http://localhost:8080/classroom/student-id/${user.id}`)
        .then((response) => response.json())
        .then((data) => setClassrooms(data));
    }
  }, [userRole, user]);


  return (
    <>
      <Banner />
      <Navbar active="teacher" />
      <h2>View your classrooms</h2>
      <br />
      <table className="table table-dark">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            {userRole === "TEACHER" && <th>Password</th>}

            <th>View</th>
          </tr>
        </thead>
        <tbody>
          {classrooms.map((classroom) => (
            <tr key={classroom.id}>
              <td>{classroom.id}</td>
              <td>{classroom.name}</td>
              {userRole === "TEACHER" && <td>{classroom.password}</td>}

              <td>
                <Link
                  to={"/view-classrooms/" + classroom.id}
                  className="delete-btn"
                >
                  <RiEyeLine />
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}
