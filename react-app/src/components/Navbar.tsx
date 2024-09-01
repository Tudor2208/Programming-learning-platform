import { useNavigate } from "react-router-dom";
import "../Navbar.css";
import { RiLogoutBoxLine, RiNotification2Line } from 'react-icons/ri';
import axios from "axios";

interface Props {
  active: string;
}

export default function Navbar({active} : Props) {
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;
  const userRole = user ? user.role : null;
  const navigate = useNavigate();


  return (
    <div>
      <ul>
        <li>
          <a className = {active === "Home" ? "active" : ""} href="/home">Home</a>
        </li>
        <li>
          <a className = {active === "Problems" ? "active" : ""} href="/problems">Problems</a>
        </li>
        <li>
        <a className = {active === "Profile" ? "active" : ""} href="/profile">Profile</a>
        </li>

        <li>
        <a className = {active === "Leaderboard" ? "active" : ""} href="/leaderboard">Leaderboard</a>
        </li>

        {userRole === "ADMIN" && (
          <li>
           <a className = {active === "Admin" ? "active" : "special"} href="/admin">Admin</a>
          </li>
        )}

        {userRole === "TEACHER" && (
          <li>
           <a className = {active === "Teacher" ? "active" : "special"} href="/teacher">Teacher</a>
          </li>
        )}


      </ul>
    </div>
  );
}
