import React, { useEffect, useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import Alert from "./Alert";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

export default function Home() {
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;
  const [showAlert, setShowAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");

  useEffect(() => {
    const stompClient = Stomp.over(
      new SockJS("http://localhost:8080/socket", null, {
        transports: "websocket",
      })
    );

    const connect = () => {
      stompClient.connect({}, () => {
        console.log("Connected to WebSocket");
       
        stompClient.subscribe(
          "/topic/socket/notification/" + user.username,
          (notification) => {
            const message = notification.body;
            console.log(message);
            setAlertMessage(message);
            setShowAlert(true);
          }
        );
      });
    };

    connect();

    return () => {
      stompClient.disconnect(); 
    };
  }, []);

  return (
    <>
      <Banner />
      <Navbar active="Home" />
      <h2>Welcome, {user?.username}!</h2>
      {showAlert && <Alert type="info">{alertMessage}</Alert>}
    </>
  );
}
