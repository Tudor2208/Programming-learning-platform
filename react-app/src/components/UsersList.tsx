import { useState, useEffect } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import "../Admin.css";
import { RiDeleteBin5Line } from "react-icons/ri";

interface User {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  registerDate: string;
  role: string;
}

interface UserLoggedInStatus {
  id: number;
  isLoggedIn: boolean;
}

export default function UsersList() {
  const [users, setUsers] = useState<User[]>([]);
  const [loggedInStatus, setLoggedInStatus] = useState<UserLoggedInStatus[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/user")
      .then((response) => response.json())
      .then((data) => setUsers(data));
  }, []);


  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString("en-US");
  };

  const handleDelete = (id: number) => {
    fetch(`http://localhost:8080/user/${id}`, {
      method: "DELETE",
    })
      .then((response) => response.json())
      .then((data) => {
        const newUsers = users.filter((user) => user.id !== id);
        setUsers(newUsers);
      })
      .catch((error) => console.error(error));
  };



    return (
      <div className="table-container">
        <Banner />
        <Navbar active="Admin" />
        <h2>Users list</h2>
        <br />
        <table className="table table-dark">
          <thead>
            <tr>
              <th>Username</th>
              <th>First name</th>
              <th>Last name</th>
              <th>E-mail</th>
              <th>Register date</th>
              <th>Role</th>
              <th>Delete</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.username}</td>
                <td>{user.firstName}</td>
                <td>{user.lastName}</td>
                <td>{user.email}</td>
                <td>{formatDate(user.registerDate)}</td>
                <td>{user.role}</td>
                <td>
                  <button
                    className="delete-btn"
                    onClick={() => handleDelete(user.id)}
                  >
                    <RiDeleteBin5Line />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
  