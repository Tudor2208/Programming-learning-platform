import { useEffect, useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";

interface User {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  points:number;
}

export default function Leaderboard() {
  const [usersByPoints, setUsersByPoints] = useState<User[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/user")
      .then((response) => response.json())
      .then((data) => setUsersByPoints(data.sort((a: { points: number; }, b: { points: number; }) => b.points - a.points).slice(0, 5)));
  }, []);

  
  return (
    <>
      <Banner />
      <Navbar active="Leaderboard" />
      <h1>Leaderboard</h1>

      <h2>Top 5 users (Most points)</h2>

      <table className="table table-dark">
          <thead>
            <tr>
              <th>Username</th>
              <th>First name</th>
              <th>Last name</th>
              <th>Points</th>
            </tr>
          </thead>
          <tbody>
            {usersByPoints.map((user) => (
              <tr key={user.id}>
                <td>{user.username}</td>
                <td>{user.firstName}</td>
                <td>{user.lastName}</td>
                <td>{user.points}</td>
              </tr>
            ))}
          </tbody>
        </table>
     

    </>
  );
}
