import { useEffect, useState } from "react";
import timetableService from "../../services/timetableService";

function TeacherTimetable() {
    const [timetable, setTimetable] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadTimetable = async () => {
            try {
                const data = await timetableService.getTeacherTimetable();
                setTimetable(data);
            } catch (err) {
                console.error("Failed to load teacher timetable:", err);
                setError("Unable to load timetable.");
            } finally {
                setLoading(false);
            }
        };

        loadTimetable();
    }, []);

    const formatTime = (time) => {
        if (!time) return "-";

        const [hours, minutes] = time.split(":");
        const date = new Date();

        date.setHours(Number(hours), Number(minutes));

        return date.toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit",
        });
    };

    return (
        <div className="min-h-screen bg-slate-50 p-6">
            <div className="mb-6">
                <h1 className="text-3xl font-bold text-slate-800">
                    My Timetable
                </h1>
                <p className="mt-1 text-slate-500">
                    View your assigned classes and lecture schedule.
                </p>
            </div>

            {loading && (
                <div className="rounded-xl bg-white p-6 text-center shadow-sm">
                    <p className="text-slate-500">
                        Loading timetable...
                    </p>
                </div>
            )}

            {error && (
                <div className="rounded-xl border border-red-200 bg-red-50 p-4 text-red-600">
                    {error}
                </div>
            )}

            {!loading && !error && (
                <div className="overflow-hidden rounded-xl bg-white shadow-sm">
                    <div className="overflow-x-auto">
                        <table className="min-w-full">
                            <thead className="bg-slate-100">
                                <tr>
                                    <th className="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                                        Day
                                    </th>
                                    <th className="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                                        Time
                                    </th>
                                    <th className="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                                        Subject
                                    </th>
                                    <th className="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                                        Semester
                                    </th>
                                    <th className="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                                        Branch
                                    </th>
                                    <th className="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                                        Room
                                    </th>
                                    <th className="px-6 py-4 text-left text-sm font-semibold text-slate-700">
                                        Type
                                    </th>
                                </tr>
                            </thead>

                            <tbody className="divide-y divide-slate-100">
                                {timetable.length === 0 ? (
                                    <tr>
                                        <td
                                            colSpan="7"
                                            className="px-6 py-10 text-center text-slate-500"
                                        >
                                            No timetable entries found.
                                        </td>
                                    </tr>
                                ) : (
                                    timetable.map((item) => (
                                        <tr
                                            key={item.id}
                                            className="hover:bg-slate-50"
                                        >
                                            <td className="px-6 py-4 text-sm font-medium text-slate-800">
                                                {item.dayOfWeek}
                                            </td>

                                            <td className="px-6 py-4 text-sm text-slate-600">
                                                {formatTime(item.startTime)}
                                                {" - "}
                                                {formatTime(item.endTime)}
                                            </td>

                                            <td className="px-6 py-4 text-sm text-slate-700">
                                                <div className="font-semibold">
                                                    {item.subjectCode}
                                                </div>
                                                <div className="text-slate-500">
                                                    {item.subjectName}
                                                </div>
                                            </td>

                                            <td className="px-6 py-4 text-sm text-slate-600">
                                                Semester {item.semesterNumber}
                                            </td>

                                            <td className="px-6 py-4 text-sm text-slate-600">
                                                {item.branchName || "-"}
                                            </td>

                                            <td className="px-6 py-4 text-sm text-slate-600">
                                                {item.roomNumber}
                                            </td>

                                            <td className="px-6 py-4 text-sm text-slate-600">
                                                {item.lectureType}
                                            </td>
                                        </tr>
                                    ))
                                )}
                            </tbody>
                        </table>
                    </div>
                </div>
            )}
        </div>
    );
}

export default TeacherTimetable;