import { useEffect, useState } from "react";
import attendanceService from "../../services/attendanceService";

function StudentAttendance() {
    const [attendance, setAttendance] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadAttendance = async () => {
            try {
                const data = await attendanceService.getMyAttendance();
                setAttendance(data);
            } catch (err) {
                console.error("Attendance API error:", err);

                setError(
                    err.response?.data?.message ||
                    "Failed to load attendance."
                );
            } finally {
                setLoading(false);
            }
        };

        loadAttendance();
    }, []);

    if (loading) {
        return (
            <div className="p-6">
                <p className="text-slate-600">
                    Loading attendance...
                </p>
            </div>
        );
    }

    if (error) {
        return (
            <div className="p-6">
                <p className="rounded-lg bg-red-100 p-4 text-red-700">
                    {error}
                </p>
            </div>
        );
    }

    return (
        <div className="p-6">
            <h1 className="mb-6 text-3xl font-bold text-slate-800">
                My Attendance
            </h1>

            {attendance.length === 0 ? (
                <div className="rounded-xl bg-white p-6 shadow">
                    <p className="text-slate-600">
                        No attendance records found.
                    </p>
                </div>
            ) : (
                <div className="overflow-x-auto rounded-xl bg-white shadow">
                    <table className="min-w-full">
                        <thead className="bg-slate-100">
                            <tr>
                                <th className="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                                    Subject
                                </th>
                                <th className="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                                    Date
                                </th>
                                <th className="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                                    Status
                                </th>
                            </tr>
                        </thead>

                        <tbody>
                            {attendance.map((record) => (
                                <tr
                                    key={record.id}
                                    className="border-t"
                                >
                                    <td className="px-6 py-4 text-slate-800">
                                        {record.subjectName ||
                                            record.subject?.subjectName ||
                                            "Not available"}
                                    </td>

                                    <td className="px-6 py-4 text-slate-800">
                                        {record.attendanceDate ||
                                            record.date ||
                                            "Not available"}
                                    </td>

                                    <td className="px-6 py-4">
                                        <span
                                            className={`rounded-full px-3 py-1 text-sm font-medium ${
                                                record.status === "PRESENT"
                                                    ? "bg-green-100 text-green-700"
                                                    : "bg-red-100 text-red-700"
                                            }`}
                                        >
                                            {record.status ||
                                                (record.present
                                                    ? "PRESENT"
                                                    : "ABSENT")}
                                        </span>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}

export default StudentAttendance;