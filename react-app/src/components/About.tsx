import "../About.css"
export default function About() {
    return (
      <div className="about">
        <h1 style={{ color: '#f2f2f2' }}>About</h1>
        <p style={{ color: '#f2f2f2' }}>
          This is a platform created especially for the students who want to improve their programming skills.
          <br /><br />
          You can find here a lot of problems and exercises structured in multiple categories.
          <br /><br />
          Also, teachers can use this website for easier class management, being able to view their students' solutions and assign homework to them.
        </p>
      </div>
    );
  }