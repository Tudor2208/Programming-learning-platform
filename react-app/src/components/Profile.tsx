import { useEffect, useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import { useNavigate } from "react-router-dom";

export default function Profile() {
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;
  const userRole = user ? user.role : null;
  const [isSubscribed, setIsSubscribed] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchIsSubscribed = async () => {
      const response = await fetch(
        `http://localhost:8080/newsletter/is-subscribed/${user.id}`
      );
      const isSubscribed = await response.json();
      setIsSubscribed(isSubscribed);
    };
    fetchIsSubscribed();
  }, [user.id]);

  const handleSubscribeClick = async () => {
    await fetch(`http://localhost:8080/newsletter/subscribe/${user.id}`, {
      method: "GET",
    });
    setIsSubscribed(true);
  };

  const handleUnsubscribeClick = async () => {
    await fetch(`http://localhost:8080/newsletter/unsubscribe/${user.id}`, {
      method: "DELETE",
    });
    setIsSubscribed(false);
  };

  const handleJoin = () => {
    navigate("/profile/join-classroom");
  };

  const handleView = () => {
    navigate("/view-classrooms");
  };

  const handleChallenge = () => {
    navigate("/challenge");
  };

  return (
    <>
      <Banner />
      <Navbar active="Profile" />
      <h2>Your profile</h2>

      {isSubscribed ? (
        <button className="btn btn-danger" onClick={handleUnsubscribeClick}>
          Unsubscribe from the newsletter
        </button>
      ) : (
        <button className="btn btn-success" onClick={handleSubscribeClick}>
          Subscribe to the newsletter
        </button>
      )}

      {userRole === "STUDENT" && <button className = "btn btn-danger" onClick = {handleJoin}>Join a new classroom</button>}
      {userRole === "STUDENT" && <button className = "btn btn-danger" onClick = {handleView}>View your classrooms</button>}
      <button className = "btn btn-danger" onClick = {handleChallenge}>Challenges</button>

    </>
  );
}
