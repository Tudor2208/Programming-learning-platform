import { ReactNode, useState } from "react";
import "../Alert.css";

interface Props {
  children: ReactNode;
  type: "primary" | "secondary" | "success" | "danger" | "warning" | "info";
}

export default function Alert({ children, type }: Props) {
  const [isOpen, setIsOpen] = useState(true);

  const handleCloseClick = () => {
    setIsOpen(false);
    window.location.reload();
  };

  return (
    <>
      {isOpen && (
        <div className="alert-container">
          <div className={"alert alert-" + type}>
            {children}
            <button
              type="button"
              className="close"
              data-dismiss="alert"
              aria-label="Close"
              onClick={handleCloseClick}
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
        </div>
      )}
    </>
  );
}
