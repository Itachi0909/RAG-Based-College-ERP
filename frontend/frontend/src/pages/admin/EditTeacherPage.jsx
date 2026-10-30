import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AdminLayout from "../../components/layout/AdminLayout";
import teacherService from "../../services/teacherService";
import TeacherForm from "./TeacherForm";

function EditTeacherPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const handleUpdate = async (teacherData) => {
    try {
      await teacherService.updateTeacher(id, teacherData);

      alert("Teacher updated successfully.");

      navigate("/admin/teachers");
    } catch (error) {
      console.error("Failed to update teacher:", error);

      alert("Failed to update teacher.");
    }
  };

  const [teacher, setTeacher] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadTeacher();
  }, []);

  const loadTeacher = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await teacherService.getTeacherById(id);

      setTeacher(data);
    } catch (error) {
      console.error("Failed to load teacher:", error);

      setError("Failed to load teacher details.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <AdminLayout>
      <h1 className="text-3xl font-bold">Edit Teacher</h1>

      {loading && <p className="mt-4">Loading teacher details...</p>}

      {error && <p className="mt-4 text-red-500">{error}</p>}

      {!loading && !error && teacher && (
        <div className="mt-6">
          <TeacherForm
            initialData={teacher}
            onSubmit={handleUpdate}
            submitButtonText="Update Teacher"
          />
        </div>
      )}
    </AdminLayout>
  );
}

export default EditTeacherPage;
