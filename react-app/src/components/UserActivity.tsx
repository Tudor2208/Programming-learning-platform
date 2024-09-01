import { useEffect, useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import { RiSearchLine } from "react-icons/ri";

interface User {
  username: string;
}

interface Activity {
  user: User;
  date: string;
  activityType: string;
}

export default function UserActivity() {
  const [activities, setActivities] = useState<Activity[]>([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [connectedUsers, setConnectedUsers] = useState("");


  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };

  useEffect(() => {
    fetch("http://localhost:8080/activity")
      .then((response) => response.json())
      .then((data) => setActivities(data));
  }, []);

  useEffect(() => {
    fetch("http://localhost:8080/activity/connected")
      .then((response) => response.json())
      .then((data) => setConnectedUsers(data));
  }, []);


  const filteredActivities = activities.filter((activity) =>
    activity.user.username.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="table-container">
      <Banner />
      <Navbar active="admin" />
      <h2>View users activity</h2>
      <h4>Connected users: <span id = "connected-users">{connectedUsers}</span></h4>
      <br />

      <div className="search-container">
        <input
          type="text"
          placeholder="Search by username"
          value={searchTerm}
          onChange={handleSearchChange}
        />
      </div>

      <table className="table table-dark">
        <thead>
          <tr>
            <th>Nr.crt</th>
            <th>User</th>
            <th>Activity type</th>
            <th>Time</th>
          </tr>
        </thead>

        <tbody>
          {filteredActivities.map((activity, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{activity.user.username}</td>
              <td>{activity.activityType}</td>
              <td>{activity.date}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
