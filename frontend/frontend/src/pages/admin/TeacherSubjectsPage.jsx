import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import teacherSubjectService from "../../services/teacherSubjectService";

function TeacherSubjectsPage() {
  const navigate = useNavigate();

  const [assignments, setAssignments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadData = async () => {
    try {
      setLoading(true);
      setError("");

      const data =
        await teacherSubjectService.getAllTeacherSubjects();

      setAssignments(Array.isArray(data) ? data : []);
    } catch (err) {
      console.error("Load teacher-subject assignments error:", err);
      setError("Failed to load teacher-subject assignments.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this assignment?",
    );

    if (!confirmed) {
      return;
    }

    try {
      setError("");

      await teacherSubjectService.deleteTeacherSubject(id);
      await loadData();
    } catch (err) {
      console.error("Delete teacher-subject assignment error:", err);
      setError("Failed to delete assignment.");
    }
  };

  return (
    <div className="p-6">
      <div className="mb-6 flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-slate-800">
            Teacher-Subject Assignments
          </h1>

          <p className="mt-1 text-sm text-slate-500">
            Assign teachers to subjects offered in different semesters.
          </p>
        </div>

        <button
          onClick={() => navigate("/admin/teacher-subjects/add")}
          className="rounded-lg bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-700"
        >
          Add Assignment
        </button>
      </div>

      {error && (
        <div className="mb-4 rounded-lg bg-red-100 px-4 py-3 text-sm text-red-700">
          {error}
        </div>
      )}

      {loading ? (
        <div className="rounded-lg bg-white p-6 text-center shadow">
          Loading assignments...
        </div>
      ) : (
        <div className="overflow-x-auto rounded-lg bg-white shadow">
          <table className="min-w-full text-left text-sm">
            <thead className="bg-slate-100 text-slate-700">
              <tr>
                <th className="px-4 py-3">Teacher</th>
                <th className="px-4 py-3">Employee ID</th>
                <th className="px-4 py-3">Subject</th>
                <th className="px-4 py-3">Semester</th>
                <th className="px-4 py-3">Branch</th>
                <th className="px-4 py-3">Department</th>
                <th className="px-4 py-3">Actions</th>
              </tr>
            </thead>

            <tbody>
              {assignments.length === 0 ? (
                <tr>
                  <td
                    colSpan="7"
                    className="px-4 py-6 text-center text-slate-500"
                  >
                    No teacher-subject assignments found.
                  </td>
                </tr>
              ) : (
                assignments.map((item) => (
                  <tr
                    key={item.id}
                    className="border-t hover:bg-slate-50"
                  >
                    <td className="px-4 py-3 font-medium text-slate-800">
                      {item.teacherName || "-"}
                    </td>

                    <td className="px-4 py-3">
                      {item.employeeId || "-"}
                    </td>

                    <td className="px-4 py-3">
                      <div className="font-medium text-slate-800">
                        {item.subjectCode || "-"}
                      </div>

                      <div className="text-xs text-slate-500">
                        {item.subjectName || "-"}
                      </div>
                    </td>

                    <td className="px-4 py-3">
                      {item.semesterNumber
                        ? `Semester ${item.semesterNumber}`
                        : "-"}
                    </td>

                    <td className="px-4 py-3">
                      {item.branchName || "-"}
                    </td>

                    <td className="px-4 py-3">
                      {item.departmentName || "-"}
                    </td>

                    <td className="px-4 py-3">
                      <button
                        onClick={() => handleDelete(item.id)}
                        className="rounded bg-red-600 px-3 py-1 text-xs font-medium text-white hover:bg-red-700"
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default TeacherSubjectsPage;