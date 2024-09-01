import "../Notification.css"
interface Props {
  text: string,
  date: string,
  id: number
}

const handleDelete = (id : number) => {
  fetch(`http://localhost:8080/notification/${id}`, {
    method: "DELETE",
  })
  window.location.reload();
};

export default function NotificationDetails({id, text, date} : Props) {
  return (
    <>
      <div className="notification">

        <p>
          <strong>Text:</strong> {text}
        </p>
        <p>
          <strong>Sending Date:</strong> {date}
        </p>
      
      <button className = "btn btn-danger" onClick={() => handleDelete(id)}>Sterge</button>
      </div>

      
    </>
  );
}
