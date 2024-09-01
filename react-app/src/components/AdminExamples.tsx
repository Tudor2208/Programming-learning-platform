import Banner from "./Banner";
import Navbar from "./Navbar";
import ProblemExampleForm from "./ProblemExampleForm";

export default function AdminExamples() {

    return (
        <>
        <Banner />
        <Navbar active = "Admin" />
        <h2>Add an example for a problem</h2>
        <ProblemExampleForm />
        </>
    );
}