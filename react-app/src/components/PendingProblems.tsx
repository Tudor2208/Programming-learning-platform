import { useEffect, useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import axios from "axios";

interface Problem {
    id: number;
    name: string;
    problemStatement: string;
    input: string;
    output: string;
    restrictions: string;
    difficulty: string;
    status: string;
  }
  
export default function PendingProblems () {

    const [problems, setProblems] = useState<Problem[]>([]);
    const userString = localStorage.getItem("user");
    const user = userString ? JSON.parse(userString) : null;

    const handleApprove = async (id:number) => {
        try {
            await axios.post(`http://localhost:8080/problem/approve/${id}/${user.id}`);
          } catch (error: any) {
            console.error("Failed to approve problem:", error);
          }
      
          window.location.reload();
    };

    const handleDeny = async (id:number) => {
        try {
            await axios.post(`http://localhost:8080/problem/deny/${id}/${user.id}`);
          } catch (error: any) {
            console.error("Failed to approve problem:", error);
          }
      
          window.location.reload();
    };

    useEffect(() => {
      fetch("http://localhost:8080/problem/pending")
        .then((response) => response.json())
        .then((data) => setProblems(data));
    }, []);


    return (
        <>
        <Banner />
        <Navbar active = "admin" />
        <h2>Pending problems</h2>
        <br />
        {problems.length > 0 && ( <table className="table table-dark">
            <thead>
                <tr>
                    <td>Name</td>
                    <td>Statement</td>
                    <td>Input</td>
                    <td>Output</td>
                    <td>Restrictions</td>
                    <td>Difficulty</td>
                    <td>Approve</td>
                    <td>Deny</td>
                </tr>
            </thead>
            <tbody>
                {problems.map((problem) => <tr>
                    <td>{problem.name}</td>
                    <td>{problem.problemStatement}</td>
                    <td>{problem.input}</td>
                    <td>{problem.output}</td>
                    <td>{problem.restrictions}</td>
                    <td>{problem.difficulty}</td>
                    <td><button className = "btn btn-success" onClick = {() => handleApprove(problem.id)}>Approve</button></td>
                    <td><button className = "btn btn-danger" onClick = {() => handleDeny(problem.id)}>Deny</button></td>
                </tr>)}
            </tbody>
        </table>)}

        {problems.length === 0 && (<h4>No pending problems</h4>)}
       
        </>
    );
}