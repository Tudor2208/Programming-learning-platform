import Banner from "./Banner";
import Navbar from "./Navbar";
import ProblemTestForm from "./ProblemTestForm";

export default function AdminTests() {
  return (
    <>
      <Banner />
      <Navbar active="Admin" />
      <h2>Add a test for a problem</h2>
      <ProblemTestForm/>
    </>
  );
}
