import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import attendanceService from "../../services/attendanceService";

function TeacherAttendance() {
    const navigate = useNavigate();

    const [sessions, setSessions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        loadSessions();
    }, []);

    const loadSessions = async () => {
        try {
            setLoading(true);
            setError("");

            const data = await attendanceService.getMySessions();
            setSessions(data || []);
        } catch (err) {
            console.error("Failed to load attendance sessions:", err);
            setError("Failed to load attendance sessions.");
        } finally {
            setLoading(false);
        }
    };

    const formatDate = (date) => {
        if (!date) return "-";

        return new Date(date).toLocaleDateString("en-IN", {
            day: "2-digit",
            month: "short",
            year: "numeric",
        });
    };

    const formatTime = (time) => {
        if (!time) return "-";

        return time.substring(0, 5);
    };

    const getStatusClass = (status) => {
        switch (status) {
            case "COMPLETED":
                return "bg-green-100 text-green-700";

            case "CANCELLED":
                return "bg-red-100 text-red-700";

            case "IN_PROGRESS":
                return "bg-blue-100 text-blue-700";

            default:
                return "bg-yellow-100 text-yellow-700";
        }
    };

    return (
        <div className="min-h-screen bg-slate-50 p-4 sm:p-6">
            <div className="mb-6 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
                <div>
                    <h1 className="text-2xl font-bold text-slate-800 sm:text-3xl">
                        Attendance
                    </h1>

                    <p className="mt-1 text-sm text-slate-500">
                        Manage your attendance sessions and mark student attendance.
                    </p>
                </div>

                <button
                    onClick={() => navigate("/teacher/attendance/create")}
                    className="rounded-lg bg-teal-600 px-5 py-3 text-sm font-semibold text-white transition hover:bg-teal-700"
                >
                    + Create Session
                </button>
            </div>

            {error && (
                <div className="mb-5 rounded-lg border border-red-200 bg-red-50 p-4 text-sm text-red-700">
                    {error}
                </div>
            )}

            <div className="overflow-hidden rounded-xl bg-white shadow-sm">
                <div className="border-b border-slate-200 px-5 py-4">
                    <h2 className="font-semibold text-slate-800">
                        My Attendance Sessions
                    </h2>
                </div>

                {loading ? (
                    <div className="p-8 text-center text-slate-500">
                        Loading attendance sessions...
                    </div>
                ) : sessions.length === 0 ? (
                    <div className="p-8 text-center">
                        <p className="font-medium text-slate-600">
                            No attendance sessions found.
                        </p>

                        <p className="mt-1 text-sm text-slate-400">
                            Create your first attendance session to get started.
                        </p>
                    </div>
                ) : (
                    <div className="overflow-x-auto">
                        <table className="min-w-full text-left text-sm">
                            <thead className="bg-slate-100 text-xs uppercase text-slate-600">
                                <tr>
                                    <th className="px-5 py-4 font-semibold">
                                        Date
                                    </th>

                                    <th className="px-5 py-4 font-semibold">
                                        Time
                                    </th>

                                    <th className="px-5 py-4 font-semibold">
                                        Subject
                                    </th>

                                    <th className="px-5 py-4 font-semibold">
                                        Semester
                                    </th>

                                    <th className="px-5 py-4 font-semibold">
                                        Topic
                                    </th>

                                    <th className="px-5 py-4 font-semibold">
                                        Status
                                    </th>

                                    <th className="px-5 py-4 text-center font-semibold">
                                        Action
                                    </th>
                                </tr>
                            </thead>

                            <tbody className="divide-y divide-slate-200">
                                {sessions.map((session) => (
                                    <tr
                                        key={session.id}
                                        className="transition hover:bg-slate-50"
                                    >
                                        <td className="whitespace-nowrap px-5 py-4 text-slate-700">
                                            {formatDate(session.attendanceDate)}
                                        </td>

                                        <td className="whitespace-nowrap px-5 py-4 text-slate-600">
                                            {formatTime(session.startTime)} -{" "}
                                            {formatTime(session.endTime)}
                                        </td>

                                        <td className="px-5 py-4">
                                            <p className="font-semibold text-slate-800">
                                                {session.subjectName ||
                                                    session.teacherSubject
                                                        ?.semesterSubject
                                                        ?.subject
                                                        ?.subjectName ||
                                                    "-"}
                                            </p>

                                            <p className="text-xs text-slate-500">
                                                {session.subjectCode ||
                                                    session.teacherSubject
                                                        ?.semesterSubject
                                                        ?.subject
                                                        ?.subjectCode ||
                                                    ""}
                                            </p>
                                        </td>

                                        <td className="whitespace-nowrap px-5 py-4 text-slate-600">
                                            {session.semesterNumber
                                                ? `Semester ${session.semesterNumber}`
                                                : "-"}
                                        </td>

                                        <td className="max-w-xs px-5 py-4 text-slate-600">
                                            {session.topic || "-"}
                                        </td>

                                        <td className="px-5 py-4">
                                            <span
                                                className={`rounded-full px-3 py-1 text-xs font-semibold ${getStatusClass(
                                                    session.status ||
                                                        session.attendanceSessionStatus
                                                )}`}
                                            >
                                                {session.status ||
                                                    session.attendanceSessionStatus ||
                                                    "SCHEDULED"}
                                            </span>
                                        </td>

                                        <td className="px-5 py-4 text-center">
                                            <button
                                                onClick={() =>
                                                    navigate(
                                                        `/teacher/attendance/session/${session.id}/mark`
                                                    )
                                                }
                                                className="rounded-lg bg-teal-50 px-3 py-2 text-xs font-semibold text-teal-700 transition hover:bg-teal-100"
                                            >
                                                Mark Attendance
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
        </div>
    );
}

export default TeacherAttendance;