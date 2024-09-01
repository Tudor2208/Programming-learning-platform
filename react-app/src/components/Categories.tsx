import { useEffect, useState } from "react";
import Banner from "./Banner";
import Navbar from "./Navbar";
import { RiDeleteBin5Line, RiEdit2Line } from "react-icons/ri";
import CategoryForm from "./CategoryForm";

interface Category {
    id: number;
    name: string;
    grade: string;
  }

export default function Categories() {
    const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/category")
      .then((response) => response.json())
      .then((data) => setCategories(data));
  }, []);

  const handleDelete = (id: number) => {
    fetch(`http://localhost:8080/category/${id}`, {
      method: "DELETE",
    })
      .then((response) => response.json())
      .then((data) => {
        const newCategories = categories.filter((category) => category.id !== id);
        setCategories(newCategories);
      })
      .catch((error) => console.error(error));
  };

  const handleUpdate = (id: number) => {
    
  };

  return (
    <div className="table-container">
      <Banner />
      <Navbar active = "Admin"/>
      <h2>Categories</h2>

      <table className="table table-dark">
        <thead>
          <tr>
            <th>Name</th>
            <th>Grade</th>
            <th>Delete</th>
            <th>Edit</th>
          </tr>
        </thead>
        <tbody>
          {categories.map((category) => (
            <tr key={category.id}>
              <td>{category.name}</td>
              <td>{category.grade.replace("GRADE_", "")}</td>
              <td>
                <button className = "delete-btn" onClick={() => handleDelete(category.id)}>
                  <RiDeleteBin5Line />
                </button>
              </td>
              <td>
                <button className = "delete-btn" onClick={() => handleUpdate(category.id)}>
                  <RiEdit2Line />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>


    <h3>Add a new category</h3>
    <CategoryForm />
    </div>
  );
}
