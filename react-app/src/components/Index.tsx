import About from "./About";
import LoginForm from "./LoginForm";
import Showcase from "./Showcase";
import "../index.css"
(window as any).global = window;
export default function Index() {
  return (
    <>
      <Showcase />
      <About />
      <LoginForm />
    </>
  );
}
