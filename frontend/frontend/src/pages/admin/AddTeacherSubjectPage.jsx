import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../services/api";
import teacherSubjectService from "../../services/teacherSubjectService";

function AddTeacherSubjectPage() {
  const navigate = useNavigate();

  const [teachers, setTeachers] = useState([]);
  const [semesterSubjects, setSemesterSubjects] = useState([]);

  const [formData, setFormData] = useState({
    teacherId: "",
    semesterSubjectId: "",
  });

  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        setError("");

        const [teachersResponse, semesterSubjectsResponse] =
          await Promise.all([
            api.get("/admin/teachers"),
            api.get("/admin/semester-subjects"),
          ]);

        setTeachers(
          Array.isArray(teachersResponse.data)
            ? teachersResponse.data
            : [],
        );

        setSemesterSubjects(
          Array.isArray(semesterSubjectsResponse.data)
            ? semesterSubjectsResponse.data
            : [],
        );
      } catch (err) {
        console.error("Load assignment form data error:", err);
        setError("Failed to load teachers or semester subjects.");
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!formData.teacherId || !formData.semesterSubjectId) {
      setError("Please select both a teacher and a semester subject.");
      return;
    }

    try {
      setSaving(true);
      setError("");

      await teacherSubjectService.createTeacherSubject({
        teacherId: Number(formData.teacherId),
        semesterSubjectId: Number(formData.semesterSubjectId),
      });

      navigate("/admin/teacher-subjects");
    } catch (err) {
      console.error("Create teacher-subject assignment error:", err);

      const backendMessage =
        err.response?.data?.message ||
        err.response?.data?.error ||
        "Failed to create teacher-subject assignment.";

      setError(backendMessage);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="p-6">
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-slate-800">
          Add Teacher-Subject Assignment
        </h1>

        <p className="mt-1 text-sm text-slate-500">
          Assign a teacher to a subject offered in a semester.
        </p>
      </div>

      {loading ? (
        <div className="rounded-lg bg-white p-6 text-center shadow">
          Loading form data...
        </div>
      ) : (
        <form
          onSubmit={handleSubmit}
          className="max-w-2xl rounded-lg bg-white p-6 shadow"
        >
          {error && (
            <div className="mb-5 rounded-lg bg-red-100 px-4 py-3 text-sm text-red-700">
              {error}
            </div>
          )}

          <div className="mb-5">
            <label
              htmlFor="teacherId"
              className="mb-2 block text-sm font-medium text-slate-700"
            >
              Teacher
            </label>

            <select
              id="teacherId"
              name="teacherId"
              value={formData.teacherId}
              onChange={handleChange}
              className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500"
              required
            >
              <option value="">Select teacher</option>

              {teachers.map((teacher) => (
                <option key={teacher.id} value={teacher.id}>
                  {teacher.firstName} {teacher.lastName}
                  {teacher.employeeId
                    ? ` (${teacher.employeeId})`
                    : ""}
                </option>
              ))}
            </select>
          </div>

          <div className="mb-6">
            <label
              htmlFor="semesterSubjectId"
              className="mb-2 block text-sm font-medium text-slate-700"
            >
              Semester Subject
            </label>

            <select
              id="semesterSubjectId"
              name="semesterSubjectId"
              value={formData.semesterSubjectId}
              onChange={handleChange}
              className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500"
              required
            >
              <option value="">Select semester subject</option>

              {semesterSubjects.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.subjectCode || item.subject?.subjectCode || "-"}{" "}
                  -{" "}
                  {item.subjectName || item.subject?.subjectName || "-"}{" "}
                  | Semester{" "}
                  {item.semesterNumber || item.semester?.semesterNumber || "-"}
                </option>
              ))}
            </select>
          </div>

          <div className="flex gap-3">
            <button
              type="submit"
              disabled={saving}
              className="rounded-lg bg-blue-600 px-5 py-2 text-sm font-medium text-white hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-60"
            >
              {saving ? "Saving..." : "Save Assignment"}
            </button>

            <button
              type="button"
              onClick={() => navigate("/admin/teacher-subjects")}
              className="rounded-lg bg-slate-200 px-5 py-2 text-sm font-medium text-slate-700 hover:bg-slate-300"
            >
              Cancel
            </button>
          </div>
        </form>
      )}
    </div>
  );
}

export default AddTeacherSubjectPage;