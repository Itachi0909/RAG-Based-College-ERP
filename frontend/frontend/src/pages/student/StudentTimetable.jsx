import { useEffect, useState } from "react";
import timetableService from "../../services/timetableService";

const StudentTimetable = () => {
    const [timetable, setTimetable] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchTimetable = async () => {
            try {
                const data = await timetableService.getStudentTimetable();
                setTimetable(data);
            } catch (err) {
                console.error(err);
                setError("Failed to load timetable.");
            } finally {
                setLoading(false);
            }
        };

        fetchTimetable();
    }, []);

    if (loading) {
        return <p className="text-gray-600">Loading timetable...</p>;
    }

    return (
        <div className="space-y-6 p-6">
            <div>
                <h1 className="text-3xl font-bold text-slate-800">
                    My Timetable
                </h1>
                <p className="text-gray-600">
                    View your semester timetable
                </p>
            </div>

            {error && (
                <div className="rounded-lg bg-red-100 p-4 text-red-700">
                    {error}
                </div>
            )}

            {!error && timetable.length === 0 && (
                <div className="rounded-lg bg-white p-6 text-gray-600 shadow">
                    No timetable found for your semester.
                </div>
            )}

            {!error && timetable.length > 0 && (
                <div className="overflow-x-auto rounded-lg bg-white shadow">
                    <table className="min-w-full">
                        <thead className="bg-slate-100">
                            <tr>
                                <th className="px-6 py-3 text-left">
                                    Day
                                </th>
                                <th className="px-6 py-3 text-left">
                                    Start Time
                                </th>
                                <th className="px-6 py-3 text-left">
                                    End Time
                                </th>
                                <th className="px-6 py-3 text-left">
                                    Room
                                </th>
                                <th className="px-6 py-3 text-left">
                                    Lecture Type
                                </th>
                            </tr>
                        </thead>

                        <tbody>
                            {timetable.map((item) => (
                                <tr
                                    key={item.id}
                                    className="border-t hover:bg-slate-50"
                                >
                                    <td className="px-6 py-4">
                                        {item.dayOfWeek}
                                    </td>
                                    <td className="px-6 py-4">
                                        {item.startTime}
                                    </td>
                                    <td className="px-6 py-4">
                                        {item.endTime}
                                    </td>
                                    <td className="px-6 py-4">
                                        {item.roomNumber || "-"}
                                    </td>
                                    <td className="px-6 py-4">
                                        {item.lectureType || "-"}
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default StudentTimetable;