import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import attendanceService from "../../services/attendanceService";

function MarkAttendance() {
    const { sessionId } = useParams();
    const navigate = useNavigate();

    const [students, setStudents] = useState([]);
    const [attendance, setAttendance] = useState({});
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    useEffect(() => {
        loadStudents();
    }, [sessionId]);

    const loadStudents = async () => {
        try {
            setLoading(true);
            setError("");

            const data =
                await attendanceService.getStudentsForAttendance(sessionId);

            const studentList = data || [];
            setStudents(studentList);

            const initialAttendance = {};

            studentList.forEach((student) => {
                initialAttendance[student.studentId] = {
                    attendanceStatus:
                        student.attendanceStatus || "PRESENT",
                    remark: student.remark || "",
                };
            });

            setAttendance(initialAttendance);
        } catch (err) {
            console.error("Failed to load students:", err);
            setError("Failed to load students for attendance.");
        } finally {
            setLoading(false);
        }
    };

    const updateAttendance = (studentId, field, value) => {
        setAttendance((previous) => ({
            ...previous,
            [studentId]: {
                ...previous[studentId],
                [field]: value,
            },
        }));
    };

    const markAll = (status) => {
        const updatedAttendance = {};

        students.forEach((student) => {
            updatedAttendance[student.studentId] = {
                ...attendance[student.studentId],
                attendanceStatus: status,
            };
        });

        setAttendance(updatedAttendance);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            setSaving(true);
            setError("");
            setSuccess("");

            const payload = {
                attendanceSessionId: Number(sessionId),
                attendanceList: students.map((student) => ({
                    studentId: student.studentId,
                    attendanceStatus:
                        attendance[student.studentId]?.attendanceStatus ||
                        "PRESENT",
                    remark:
                        attendance[student.studentId]?.remark?.trim() || "",
                })),
            };

            await attendanceService.markAttendance(payload);

            setSuccess("Attendance marked successfully.");

            setTimeout(() => {
                navigate("/teacher/attendance");
            }, 1000);
        } catch (err) {
            console.error("Failed to mark attendance:", err);

            setError(
                err.response?.data?.message ||
                    "Failed to mark attendance. Please try again."
            );
        } finally {
            setSaving(false);
        }
    };

    return (
        <div className="min-h-screen bg-slate-50 p-4 sm:p-6">
            <div className="mb-6 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
                <div>
                    <h1 className="text-2xl font-bold text-slate-800 sm:text-3xl">
                        Mark Attendance
                    </h1>

                    <p className="mt-1 text-sm text-slate-500">
                        Mark attendance for students in this session.
                    </p>
                </div>

                <button
                    type="button"
                    onClick={() => navigate("/teacher/attendance")}
                    className="rounded-lg border border-slate-300 bg-white px-4 py-2.5 text-sm font-semibold text-slate-700 transition hover:bg-slate-100"
                >
                    Back to Sessions
                </button>
            </div>

            {error && (
                <div className="mb-5 rounded-lg border border-red-200 bg-red-50 p-4 text-sm text-red-700">
                    {error}
                </div>
            )}

            {success && (
                <div className="mb-5 rounded-lg border border-green-200 bg-green-50 p-4 text-sm text-green-700">
                    {success}
                </div>
            )}

            {loading ? (
                <div className="rounded-xl bg-white p-8 text-center text-slate-500 shadow-sm">
                    Loading students...
                </div>
            ) : students.length === 0 ? (
                <div className="rounded-xl bg-white p-8 text-center shadow-sm">
                    <p className="font-medium text-slate-700">
                        No students found for this session.
                    </p>
                </div>
            ) : (
                <form onSubmit={handleSubmit}>
                    <div className="mb-5 flex flex-wrap gap-3">
                        <button
                            type="button"
                            onClick={() => markAll("PRESENT")}
                            className="rounded-lg bg-green-100 px-4 py-2 text-sm font-semibold text-green-700 transition hover:bg-green-200"
                        >
                            Mark All Present
                        </button>

                        <button
                            type="button"
                            onClick={() => markAll("ABSENT")}
                            className="rounded-lg bg-red-100 px-4 py-2 text-sm font-semibold text-red-700 transition hover:bg-red-200"
                        >
                            Mark All Absent
                        </button>

                        <button
                            type="button"
                            onClick={() => markAll("LEAVE")}
                            className="rounded-lg bg-yellow-100 px-4 py-2 text-sm font-semibold text-yellow-700 transition hover:bg-yellow-200"
                        >
                            Mark All Leave
                        </button>
                    </div>

                    <div className="overflow-hidden rounded-xl bg-white shadow-sm">
                        <div className="border-b border-slate-200 px-5 py-4">
                            <h2 className="font-semibold text-slate-800">
                                Student Attendance
                            </h2>
                        </div>

                        <div className="overflow-x-auto">
                            <table className="min-w-full text-left text-sm">
                                <thead className="bg-slate-100 text-xs uppercase text-slate-600">
                                    <tr>
                                        <th className="px-5 py-4 font-semibold">
                                            Enrollment Number
                                        </th>

                                        <th className="px-5 py-4 font-semibold">
                                            Roll Number
                                        </th>

                                        <th className="px-5 py-4 font-semibold">
                                            Student Name
                                        </th>

                                        <th className="px-5 py-4 font-semibold">
                                            Status
                                        </th>

                                        <th className="px-5 py-4 font-semibold">
                                            Remark
                                        </th>
                                    </tr>
                                </thead>

                                <tbody className="divide-y divide-slate-200">
                                    {students.map((student) => {
                                        const studentAttendance =
                                            attendance[student.studentId] || {
                                                attendanceStatus: "PRESENT",
                                                remark: "",
                                            };

                                        return (
                                            <tr
                                                key={student.studentId}
                                                className="transition hover:bg-slate-50"
                                            >
                                                <td className="whitespace-nowrap px-5 py-4 text-slate-700">
                                                    {student.enrollmentNumber ||
                                                        "-"}
                                                </td>

                                                <td className="whitespace-nowrap px-5 py-4 text-slate-600">
                                                    {student.rollNumber || "-"}
                                                </td>

                                                <td className="whitespace-nowrap px-5 py-4 font-medium text-slate-800">
                                                    {student.studentName || "-"}
                                                </td>

                                                <td className="px-5 py-4">
                                                    <select
                                                        value={
                                                            studentAttendance.attendanceStatus
                                                        }
                                                        onChange={(event) =>
                                                            updateAttendance(
                                                                student.studentId,
                                                                "attendanceStatus",
                                                                event.target.value
                                                            )
                                                        }
                                                        className="rounded-lg border border-slate-300 bg-white px-3 py-2 text-sm outline-none focus:border-teal-500 focus:ring-2 focus:ring-teal-100"
                                                    >
                                                        <option value="PRESENT">
                                                            Present
                                                        </option>
                                                        <option value="ABSENT">
                                                            Absent
                                                        </option>
                                                        <option value="LEAVE">
                                                            Leave
                                                        </option>
                                                    </select>
                                                </td>

                                                <td className="px-5 py-4">
                                                    <input
                                                        type="text"
                                                        maxLength="200"
                                                        value={
                                                            studentAttendance.remark
                                                        }
                                                        onChange={(event) =>
                                                            updateAttendance(
                                                                student.studentId,
                                                                "remark",
                                                                event.target.value
                                                            )
                                                        }
                                                        placeholder="Optional remark"
                                                        className="w-full min-w-48 rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-teal-500 focus:ring-2 focus:ring-teal-100"
                                                    />
                                                </td>
                                            </tr>
                                        );
                                    })}
                                </tbody>
                            </table>
                        </div>

                        <div className="flex justify-end border-t border-slate-200 px-5 py-4">
                            <button
                                type="submit"
                                disabled={saving}
                                className="rounded-lg bg-teal-600 px-6 py-3 text-sm font-semibold text-white transition hover:bg-teal-700 disabled:cursor-not-allowed disabled:opacity-60"
                            >
                                {saving
                                    ? "Saving Attendance..."
                                    : "Save Attendance"}
                            </button>
                        </div>
                    </div>
                </form>
            )}
        </div>
    );
}

export default MarkAttendance;