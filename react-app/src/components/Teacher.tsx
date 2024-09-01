import { useNavigate } from "react-router-dom";
import Banner from "./Banner";
import Navbar from "./Navbar";

export default function Teacher() {
  const navigate = useNavigate();
  const handleCreateClassroom = () => {
    navigate("/teacher/create-classroom");
  };

  const handleViewClassrooms = () => {
    navigate("/view-classrooms");
  };

  const handleProblems = () => {
    navigate("/admin/problems");
  };

  const handleTests = () => {
    navigate("/admin/tests");
  };

  const handleExamples = () => {
    navigate("/admin/examples");
  };

  return (
    <>
      <Banner />
      <Navbar active="teacher" />
      <h2>Teacher panel</h2>
      <button className="btn btn-danger" onClick={handleCreateClassroom}>
        Create a classroom
      </button>
      <button className="btn btn-danger" onClick={handleViewClassrooms}>
        View you classrooms
      </button>
      <button type="button" className="btn btn-danger" onClick={handleProblems}>
        Add problem
      </button>
      <button onClick={handleTests} type="button" className="btn btn-danger">
        Add problem tests
      </button>
      <button onClick={handleExamples} type="button" className="btn btn-danger">
        Add problem examples
      </button>
    </>
  );
}
