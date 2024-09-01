import Banner from "./Banner";
import Navbar from "./Navbar";
import ProblemForm from "./ProblemForm";

export default function AdminProblems() {

    return (
        <>
            <Banner />
            <Navbar active = "Admin"/>
            <h2>Add problem</h2>
            
            <ProblemForm/>
        </>

    );
}