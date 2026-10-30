import AdminLayout from "../../components/layout/AdminLayout";
import TeacherForm from "./TeacherForm";
import { useNavigate } from "react-router-dom";
import teacherService from "../../services/teacherService";

function AddTeacherPage() {
  const handleCreate = async (teacherData) => {
    try {
      await teacherService.createTeacher(teacherData);

      alert("Teacher created successfully.");

      navigate("/admin/teachers");
    } catch (error) {
      console.error("Failed to create teacher:", error);

      alert("Failed to create teacher.");
    }
  };
  return (
    <AdminLayout>
      <h1 className="text-3xl font-bold">Add Teacher</h1>

      <p className="mt-2 text-slate-600">Add a new teacher to the system.</p>

      <div className="mt-6">
        <TeacherForm
          onSubmit={handleCreate}
          submitButtonText="Create Teacher"
        />
      </div>
    </AdminLayout>
  );
}

export default AddTeacherPage;
