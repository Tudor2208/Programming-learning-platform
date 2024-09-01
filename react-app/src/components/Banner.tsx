import { RiLogoutBoxLine, RiNotification2Line } from "react-icons/ri";
import "../Banner.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function Banner() {
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;
  const userRole = user ? user.role : null;
  const navigate = useNavigate();

  const handleLogout = () => {
    const logoutActivity = {
      user: user,
      activityType: "LOGOUT",
      date: new Date(),
    };

    axios
      .post("/activity", logoutActivity)
      .then((response) => {
        console.log("Logout activity created successfully");
      })
      .catch((error) => {
        console.log("Error creating logout activity:", error);
      });
    localStorage.removeItem("user");
    navigate("/");
  };

  return (
    <>
      <div>
        <h1>
          <span className="first_letter">C</span>oding{" "}
          <span className="first_letter">P</span>roblems{" "}
          <span className="first_letter">P</span>latform
        </h1>

        {user && (
          <>
            {" "}
            <a href="/notifications">
              <RiNotification2Line />
            </a>
            <span style={{ marginLeft: "10px", marginRight: "10px" }}></span>
            <a href="/">
              <RiLogoutBoxLine onClick={handleLogout} />
            </a>
          </>
        )}
      </div>
    </>
  );
}
