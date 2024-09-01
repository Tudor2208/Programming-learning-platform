import { useEffect, useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import axios from "axios";
import Alert from "./Alert";
import { useNavigate } from "react-router-dom";

interface Problem {
  id: number;
  name: string;
}

interface User {
  id: number;
  username: string;
  points: number
}

interface Challenge {
  id: number;
  points: number;
  minutes: number;
  challengingUser: User;
  challengedUser: User;
  problem: Problem;
  createTime: string;
  startTime: string;
  endTime: string;
  winner: User
}

const formatTime = (dateString: any) => {
  const date = new Date(dateString);
  return date.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
};

export default function ChallengeForm() {
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;

  const [connectedUser, setConnectedUser] = useState<User>(user);
  const [showAlert, setShowAlert] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");

  const [problems, setProblems] = useState<Problem[]>([]);

  const [users, setUsers] = useState<User[]>([]);

  const [selectedProblem, setSelectedProblem] = useState<Problem | null>(null);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);

  const [pendingChallenges, setPendingChallenges] = useState<Challenge[]>([]);
  const [ongoingChallenges, setOngoingChallenges] = useState<Challenge[]>([]);
  const [completedChallenges, setCompletedChallenges] = useState<Challenge[]>([]);
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    challengedUser: "",
    problem: "",
    minutes: "",
    points: "",
  });

  useEffect(() => {
    fetch("http://localhost:8080/problem")
      .then((response) => response.json())
      .then((data) => setProblems(data));
  }, []);

  useEffect(() => {
    fetch("http://localhost:8080/user")
      .then((response) => response.json())
      .then((data) => setUsers(data));
  }, []);

  useEffect(() => {
    fetch(`http://localhost:8080/challenge/pending/${user.id}`)
      .then((response) => response.json())
      .then((data) => setPendingChallenges(data));
  }, []);

  useEffect(() => {
    fetch(`http://localhost:8080/challenge/ongoing/${user.id}`)
      .then((response) => response.json())
      .then((data) => setOngoingChallenges(data));
  }, []);

  useEffect(() => {
    fetch(`http://localhost:8080/user/${user.id}`)
      .then((response) => response.json())
      .then((data) => setConnectedUser(data));
  }, []);

  useEffect(() => {
    fetch(`http://localhost:8080/challenge/completed/${user.id}`)
      .then((response) => response.json())
      .then((data) => setCompletedChallenges(data));
  }, []);

  const handleSubmit = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    if (!selectedProblem || !selectedUser) return;
    alert("The other user will have only 5 minutes to accept you challenge!");
    try {
      axios.defaults.baseURL = "http://localhost:8080";
      const response = await axios.post("/challenge", {
        ...formData,
        problem: selectedProblem,
        challengedUser: selectedUser,
        challengingUser: user,
        status: "PENDING",
      });

      setFormData({
        points: "",
        minutes: "",
        problem: "",
        challengedUser: "",
      });

    
    } catch (error: any) {
      console.error("Error creating challenge:", error);
      setAlertMessage(error.response.data.message);
      setShowAlert(true);
    }
  };

  const handleInputChange = (event: { target: { name: any; value: any } }) => {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value,
    });
  };

  const handleProblemSelect = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const problemId = Number(event.target.value);
    const problem = problems.find((p) => p.id === problemId);
    setSelectedProblem(problem || null);
  };

  const handleUserSelect = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const userId = Number(event.target.value);
    const user = users.find((p) => p.id === userId);
    setSelectedUser(user || null);
  };

  const handleAccept = async (id: number) => {
    try {
      await axios.put(`http://localhost:8080/challenge/accept/${id}`);
    } catch (error: any) {
      console.error("Failed to accept challenge:", error);
    }

    window.location.reload();
  };

  return (
    <>
      <Banner />
      <Navbar active="profile" />
      <h1>Your challenges</h1>
      <h4>
        Points: <span className="green-text">{connectedUser.points}</span>
      </h4>
      <br /> <br />
      {pendingChallenges.length > 0 && (
        <>
          {" "}
          <h3>Pending challenges</h3>
          <table className="table table-dark">
            <thead>
              <tr>
                <td>From</td>
                <td>To</td>
                <td>Points</td>
                <td>Minutes</td>
                <td>Problem</td>
                <td>Created time</td>
                <td>Accept challenge</td>
              </tr>
            </thead>

            <tbody>
              {pendingChallenges.map((challenge) => (
                <tr>
                  <td>{challenge.challengingUser.username}</td>
                  <td>{challenge.challengedUser.username}</td>
                  <td>{challenge.points}</td>
                  <td>{challenge.minutes}</td>
                  <td>
                    {challenge.problem.name} (#{challenge.problem.id})
                  </td>
                  <td>{formatTime(challenge.createTime)}</td>
                  <td>
                    <button
                      className="btn btn-success"
                      onClick={() => handleAccept(challenge.id)}
                    >
                      accept
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      )}
      {ongoingChallenges.length > 0 && (
        <>
          {" "}
          <h3>Ongoing challenges</h3>
          <table className="table table-dark">
            <thead>
              <tr>
                <td>From</td>
                <td>To</td>
                <td>Points</td>
                <td>Minutes</td>
                <td>Problem</td>
                <td>Start time</td>
                <td>End time</td>
              </tr>
            </thead>

            <tbody>
              {ongoingChallenges.map((challenge) => (
                <tr>
                  <td>{challenge.challengingUser.username}</td>
                  <td>{challenge.challengedUser.username}</td>
                  <td>{challenge.points}</td>
                  <td>{challenge.minutes}</td>
                  <td>
                    {challenge.problem.name} (#{challenge.problem.id})
                  </td>
                  <td>{formatTime(challenge.startTime)}</td>
                  <td>{formatTime(challenge.endTime)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      )}

{completedChallenges.length > 0 && (
        <>
          {" "}
          <h3>Completed challenges</h3>
          <table className="table table-dark">
            <thead>
              <tr>
                <td>From</td>
                <td>To</td>
                <td>Points</td>
                <td>Minutes</td>
                <td>Problem</td>
                <td>Start time</td>
                <td>End time</td>
                <td>Winner</td>
              </tr>
            </thead>

            <tbody>
              {completedChallenges.map((challenge) => (
                <tr>
                  <td>{challenge.challengingUser.username}</td>
                  <td>{challenge.challengedUser.username}</td>
                  <td>{challenge.points}</td>
                  <td>{challenge.minutes}</td>
                  <td>
                    {challenge.problem.name} (#{challenge.problem.id})
                  </td>
                  <td>{formatTime(challenge.startTime)}</td>
                  <td>{formatTime(challenge.endTime)}</td>
                  {challenge.winner && (<td>{challenge.winner.username}</td>)}
                  {!challenge.winner && (<td>None</td>)}
                </tr>
              ))}
            </tbody>
          </table>
        </>
      )}

      <h3>Start a new challenge</h3>
      {showAlert && <Alert type="warning">{alertMessage}</Alert>}
      <div className="grid">
        <form className="form login" onSubmit={handleSubmit}>
          <div className="form__field">
            <label htmlFor="name">Problem:</label>
            <select
              className="form__input"
              name="problem"
              id="problem"
              value={selectedProblem?.id ?? ""}
              onChange={handleProblemSelect}
            >
              <option value="">Select a problem...</option>
              {problems.map((problem) => (
                <option key={problem.id} value={problem.id}>
                  {problem.name}
                </option>
              ))}
            </select>
          </div>

          <div className="form__field">
            <label htmlFor="name">Challenged user:</label>
            <select
              className="form__input"
              name="challengedUser"
              id="challengedUser"
              value={selectedUser?.id ?? ""}
              onChange={handleUserSelect}
            >
              <option value="">Select an user..</option>
              {users.map((user) => (
                <option key={user.id} value={user.id}>
                  {user.username}
                </option>
              ))}
            </select>
          </div>

          <div className="form__field">
            <label htmlFor="input">Points:</label>
            <input
              className="form__input"
              type="text"
              name="points"
              id="points"
              pattern="[0-9]*"
              title="Points should be integer"
              value={formData.points}
              onChange={handleInputChange}
            />
          </div>

          <div className="form__field">
            <label htmlFor="input">Minutes:</label>
            <input
              className="form__input"
              type="text"
              name="minutes"
              id="minutes"
              pattern="[0-9]*"
              title="Minutes should be integer"
              value={formData.minutes}
              onChange={handleInputChange}
            />
          </div>

          <button className="btn btn-danger" type="submit">
            Start a challenge
          </button>
        </form>
      </div>
    </>
  );
}
