import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AdminLayout from "../../components/layout/AdminLayout";
import studentService from "../../services/studentService";
import StudentForm from "./StudentForm";

function EditStudentPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [student, setStudent] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadStudent();
  }, []);

  const loadStudent = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await studentService.getStudentById(id);

      console.log("Student data received:", data);

      setStudent(data);
    } catch (error) {
      console.error("Failed to load student:", error);

      setError("Failed to load student details.");
    } finally {
      setLoading(false);
    }
  };

  const handleUpdate = async (studentData) => {
    try {
      await studentService.updateStudent(id, studentData);

      alert("Student updated successfully.");

      navigate("/admin/students");
    } catch (error) {
      console.error("Failed to update student:", error);

      alert("Failed to update student.");
    }
  };

  return (
    <AdminLayout>
      <h1 className="text-3xl font-bold">Edit Student</h1>

      {loading && <p className="mt-4">Loading student details...</p>}

      {error && <p className="mt-4 text-red-500">{error}</p>}

      {!loading && !error && student && (
        <StudentForm
          initialData={student}
          onSubmit={handleUpdate}
          submitButtonText="Update Student"
        />
      )}
    </AdminLayout>
  );
}

export default EditStudentPage;
