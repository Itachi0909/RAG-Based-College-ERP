import { useEffect, useState } from "react";
import semesterSubjectService from "../../services/semesterSubjectService";

const StudentSubjects = () => {
    const [subjects, setSubjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchSubjects = async () => {
            try {
                const data = await semesterSubjectService.getMySubjects();
                setSubjects(data);
            } catch (err) {
                console.error(err);
                setError("Failed to load subjects.");
            } finally {
                setLoading(false);
            }
        };

        fetchSubjects();
    }, []);

    if (loading) {
        return <p className="text-gray-600">Loading subjects...</p>;
    }

    return (
        <div className="space-y-6">
            <div>
                <h1 className="text-3xl font-bold text-slate-800">
                    My Subjects
                </h1>
                <p className="text-gray-600">
                    Subjects assigned to your semester
                </p>
            </div>

            {error && (
                <div className="rounded-lg bg-red-100 p-4 text-red-700">
                    {error}
                </div>
            )}

            {!error && subjects.length === 0 && (
                <div className="rounded-lg bg-white p-6 text-gray-600 shadow">
                    No subjects found for your semester.
                </div>
            )}

            {!error && subjects.length > 0 && (
                <div className="overflow-x-auto rounded-lg bg-white shadow">
                    <table className="min-w-full">
                        <thead className="bg-slate-100">
                            <tr>
                                <th className="px-6 py-3 text-left">
                                    Subject Code
                                </th>
                                <th className="px-6 py-3 text-left">
                                    Subject Name
                                </th>
                                <th className="px-6 py-3 text-left">
                                    Internal Marks
                                </th>
                                <th className="px-6 py-3 text-left">
                                    External Marks
                                </th>
                                <th className="px-6 py-3 text-left">
                                    Total Marks
                                </th>
                            </tr>
                        </thead>

                        <tbody>
                            {subjects.map((subject) => (
                                <tr
                                    key={subject.id}
                                    className="border-t hover:bg-slate-50"
                                >
                                    <td className="px-6 py-4">
                                        {subject.subjectCode}
                                    </td>
                                    <td className="px-6 py-4 font-medium">
                                        {subject.subjectName}
                                    </td>
                                    <td className="px-6 py-4">
                                        {subject.internalMarks}
                                    </td>
                                    <td className="px-6 py-4">
                                        {subject.externalMarks}
                                    </td>
                                    <td className="px-6 py-4">
                                        {subject.totalMarks}
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

export default StudentSubjects;