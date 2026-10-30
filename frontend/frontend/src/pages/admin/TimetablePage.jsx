import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import timetableService from "../../services/timetableService";

function TimetablePage() {
  const navigate = useNavigate();

  const [timetables, setTimetables] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadData = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await timetableService.getAllTimetables();

      setTimetables(Array.isArray(data) ? data : []);
    } catch (err) {
      console.error("Load timetable error:", err);
      setError("Failed to load timetable data.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleDelete = async (id) => {
    console.log("Deleting timetable ID:", id);

    if (id === undefined || id === null) {
      setError("Invalid timetable ID.");
      return;
    }

    const confirmed = window.confirm(
      "Are you sure you want to delete this timetable entry?",
    );

    if (!confirmed) {
      return;
    }

    try {
      setError("");

      await timetableService.deleteTimetable(id);
      await loadData();
    } catch (err) {
      console.error("Delete timetable error:", err);

      if (err.response?.status === 404) {
        setError("Timetable entry was not found. Please refresh the page.");
      } else {
        setError("Failed to delete timetable entry.");
      }
    }
  };

  const formatTime = (time) => {
    if (!time) {
      return "-";
    }

    return String(time).substring(0, 5);
  };

  const formatDay = (day) => {
    if (!day) {
      return "-";
    }

    return day.charAt(0) + day.slice(1).toLowerCase();
  };

  return (
    <div className="p-6">
      <div className="mb-6 flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-slate-800">
            Timetable Management
          </h1>

          <p className="mt-1 text-sm text-slate-500">
            Manage lecture schedules for teachers and semesters.
          </p>
        </div>

        <button
          onClick={() => navigate("/admin/timetable/add")}
          className="rounded-lg bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-700"
        >
          Add Timetable
        </button>
      </div>

      {error && (
        <div className="mb-4 rounded-lg bg-red-100 px-4 py-3 text-sm text-red-700">
          {error}
        </div>
      )}

      {loading ? (
        <div className="rounded-lg bg-white p-6 text-center shadow">
          Loading timetable...
        </div>
      ) : (
        <div className="overflow-x-auto rounded-lg bg-white shadow">
          <table className="min-w-full text-left text-sm">
            <thead className="bg-slate-100 text-slate-700">
              <tr>
                <th className="px-4 py-3">Subject</th>
                <th className="px-4 py-3">Teacher</th>
                <th className="px-4 py-3">Semester</th>
                <th className="px-4 py-3">Branch</th>
                <th className="px-4 py-3">Day</th>
                <th className="px-4 py-3">Time</th>
                <th className="px-4 py-3">Room</th>
                <th className="px-4 py-3">Type</th>
                <th className="px-4 py-3">Actions</th>
              </tr>
            </thead>

            <tbody>
              {timetables.length === 0 ? (
                <tr>
                  <td
                    colSpan="9"
                    className="px-4 py-6 text-center text-slate-500"
                  >
                    No timetable entries found.
                  </td>
                </tr>
              ) : (
                timetables.map((item) => (
                  <tr
                    key={item.id}
                    className="border-t hover:bg-slate-50"
                  >
                    <td className="px-4 py-3">
                      <div className="font-medium text-slate-800">
                        {item.subjectCode || "-"}
                      </div>

                      <div className="text-xs text-slate-500">
                        {item.subjectName || "-"}
                      </div>
                    </td>

                    <td className="px-4 py-3">
                      {item.teacherName || "-"}
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
                      {formatDay(item.dayOfWeek)}
                    </td>

                    <td className="px-4 py-3">
                      {formatTime(item.startTime)} -{" "}
                      {formatTime(item.endTime)}
                    </td>

                    <td className="px-4 py-3">
                      {item.roomNumber || "-"}
                    </td>

                    <td className="px-4 py-3">
                      {item.lectureType || "-"}
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

export default TimetablePage;