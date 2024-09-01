import { BrowserRouter, Route, Routes } from "react-router-dom";
import Showcase from "./components/Showcase";
import Banner from "./components/Banner";
import LoginForm from "./components/LoginForm";
import About from "./components/About";
import Home from "./components/Home";
import RegisterForm from "./components/RegisterForm";
import Admin from "./components/Admin";
import UsersList from "./components/UsersList";
import Categories from "./components/Categories";
import AdminProblems from "./components/AdminProblems";
import Problems from "./components/Problems";
import ProblemDetails from "./components/ProblemDetails";
import Profile from "./components/Profile";
import Notifications from "./components/Notifications";
import AdminTests from "./components/AdminTests";
import AdminExamples from "./components/AdminExamples";
import Teacher from "./components/Teacher";
import CreateClassroom from "./components/ClassroomForm";
import ViewClassrooms from "./components/ViewClassrooms";
import ClassroomForm from "./components/ClassroomForm";
import JoinClassroom from "./components/JoinClassroom";
import ClassroomDetails from "./components/ClassroomDetails";
import HomeworkForm from "./components/HomeworkForm";
import UserActivity from "./components/UserActivity";
import ChallangeForm from "./components/ChallengeForm";
import ChallengeForm from "./components/ChallengeForm";
import PendingProblems from "./components/PendingProblems";
import Leaderboard from "./components/Leaderboard";

export default function App() {
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            <>
              <Showcase /> <About /> <LoginForm />
            </>
          }
        />
        <Route path="/home" element={<Home />} />

        <Route
          path="/register"
          element={
            <>
              <Banner />
              <h2>Create a new account!</h2>
              <RegisterForm />
            </>
          }
        />

        <Route path="/admin" element={<Admin />} />
        <Route path="/admin/users" element={<UsersList />} />
        <Route path="/admin/categories" element={<Categories />} />
        <Route path="/admin/problems" element={<AdminProblems />} />
        <Route path="/admin/tests" element={<AdminTests />} />
        <Route path="/admin/examples" element={<AdminExamples />} />
        <Route path="/admin/activity" element={<UserActivity />} />
        <Route path="/admin/pending-problems" element={<PendingProblems />} />
        <Route path="/problems" element={<Problems />} />
        <Route path="/problem/:id" element={<ProblemDetails />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/profile/join-classroom" element={<JoinClassroom />} />
        <Route path="/notifications" element={<Notifications />} />
        <Route path="/teacher" element={<Teacher />} />
        <Route path="/teacher/create-classroom" element={<ClassroomForm />} />
        <Route path="/view-classrooms" element={<ViewClassrooms />} />
        <Route path="/view-classrooms/:id" element={<ClassroomDetails />} />
        <Route path="/add-homework/:id" element={<HomeworkForm />} />
        <Route path="/challenge" element={<ChallengeForm />} />
        <Route path="/leaderboard" element={<Leaderboard />} />
      </Routes>
    </BrowserRouter>
  );
}
