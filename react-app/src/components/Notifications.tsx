import { useEffect, useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import NotificationDetails from "./NotificationDetails";
import "../Notification.css"

interface Notification {
  id: number;
  text: string;
  sendingDate: string;
}

export default function Notifications() {
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;

  const [notifications, setNotifications] = useState<Notification[]>([]);

  useEffect(() => {
    fetch(`http://localhost:8080/notification/user/${user.id}`)
      .then((response) => response.json())
      .then((data) => setNotifications(data));
    console.log(notifications);
  }, []);

  return (
    <>
      <Banner />
      <Navbar active="none" />
      <h2>Notifications</h2>

      <ul id = "notif-ul">
        {notifications.map((notif) => (
          <li>
            <NotificationDetails
              id = {notif.id}
              text={notif.text}
              date={new Date(notif.sendingDate).toLocaleString()}
            />
          </li>
        ))}
      </ul>
    </>
  );
}
