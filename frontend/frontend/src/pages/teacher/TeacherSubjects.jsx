import { useEffect, useState } from "react";
import teacherSubjectService from "../../services/teacherSubjectService";

const TeacherSubjects = () => {
    const [subjects, setSubjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadSubjects = async () => {
            try {
                const response =
                    await teacherSubjectService.getMyTeacherSubjects();

                const data = Array.isArray(response)
                    ? response
                    : response?.data || response?.content || [];

                setSubjects(data);
            } catch (err) {
                console.error("Failed to load teacher subjects:", err);
                setError("Unable to load assigned subjects.");
            } finally {
                setLoading(false);
            }
        };

        loadSubjects();
    }, []);

    const getSubjectDetails = (assignment) => {
        const semesterSubject =
            assignment.semesterSubject ||
            assignment.semesterSubjectResponse ||
            assignment.subjectAssignment ||
            {};

        const subject =
            assignment.subject ||
            semesterSubject.subject ||
            semesterSubject.subjectResponse ||
            {};

        const semester =
            assignment.semester ||
            semesterSubject.semester ||
            semesterSubject.semesterResponse ||
            {};

        return {
            id: assignment.id,

            subjectCode:
                assignment.subjectCode ||
                subject.subjectCode ||
                subject.code ||
                "—",

            subjectName:
                assignment.subjectName ||
                subject.subjectName ||
                subject.name ||
                "—",

            semesterNumber:
                assignment.semesterNumber ||
                semester.semesterNumber ||
                semester.number ||
                "—",

            internalMarks:
                assignment.internalMarks ??
                subject.internalMarks ??
                "—",

            externalMarks:
                assignment.externalMarks ??
                subject.externalMarks ??
                "—",

            totalMarks:
                assignment.totalMarks ??
                subject.totalMarks ??
                "—",
        };
    };

    const formattedSubjects = subjects.map(getSubjectDetails);

    return (
        <div className="min-h-screen bg-slate-50 p-6">
            <div className="rounded-2xl bg-white p-6 shadow-sm">
                <div className="mb-6 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
                    <div>
                        <h2 className="text-xl font-bold text-slate-800">
                            Assigned Subjects
                        </h2>

                        <p className="mt-1 text-sm text-slate-500">
                            Subjects assigned by the administrator.
                        </p>
                    </div>

                    <span className="w-fit rounded-full bg-teal-100 px-4 py-2 text-sm font-semibold text-teal-700">
                        {formattedSubjects.length}{" "}
                        {formattedSubjects.length === 1
                            ? "Subject"
                            : "Subjects"}
                    </span>
                </div>

                {loading && (
                    <div className="rounded-xl border border-dashed border-slate-300 p-10 text-center text-slate-500">
                        Loading assigned subjects...
                    </div>
                )}

                {!loading && error && (
                    <div className="rounded-xl border border-red-200 bg-red-50 p-6 text-center text-red-600">
                        {error}
                    </div>
                )}

                {!loading &&
                    !error &&
                    formattedSubjects.length === 0 && (
                        <div className="rounded-xl border border-dashed border-slate-300 p-10 text-center">
                            <div className="mb-3 text-5xl">📚</div>

                            <h3 className="font-semibold text-slate-700">
                                No subjects assigned
                            </h3>

                            <p className="mt-1 text-sm text-slate-500">
                                Assigned subjects will be displayed here.
                            </p>
                        </div>
                    )}

                {!loading &&
                    !error &&
                    formattedSubjects.length > 0 && (
                        <div className="overflow-x-auto">
                            <table className="w-full min-w-[700px] text-left">
                                <thead>
                                    <tr className="border-b border-slate-200 text-sm text-slate-500">
                                        <th className="px-4 py-4 font-semibold">
                                            #
                                        </th>

                                        <th className="px-4 py-4 font-semibold">
                                            Subject Code
                                        </th>

                                        <th className="px-4 py-4 font-semibold">
                                            Subject Name
                                        </th>

                                        <th className="px-4 py-4 font-semibold">
                                            Semester
                                        </th>

                                        <th className="px-4 py-4 font-semibold">
                                            Internal Marks
                                        </th>

                                        <th className="px-4 py-4 font-semibold">
                                            External Marks
                                        </th>

                                        <th className="px-4 py-4 font-semibold">
                                            Total Marks
                                        </th>
                                    </tr>
                                </thead>

                                <tbody>
                                    {formattedSubjects.map(
                                        (assignment, index) => (
                                            <tr
                                                key={
                                                    assignment.id ||
                                                    `${assignment.subjectCode}-${index}`
                                                }
                                                className="border-b border-slate-100 last:border-b-0"
                                            >
                                                <td className="px-4 py-4 text-sm text-slate-600">
                                                    {index + 1}
                                                </td>

                                                <td className="px-4 py-4 text-sm font-semibold text-slate-800">
                                                    {assignment.subjectCode}
                                                </td>

                                                <td className="px-4 py-4 text-sm text-slate-700">
                                                    {assignment.subjectName}
                                                </td>

                                                <td className="px-4 py-4 text-sm text-slate-700">
                                                    {assignment.semesterNumber !==
                                                    "—"
                                                        ? `Semester ${assignment.semesterNumber}`
                                                        : "—"}
                                                </td>

                                                <td className="px-4 py-4 text-sm text-slate-700">
                                                    {assignment.internalMarks}
                                                </td>

                                                <td className="px-4 py-4 text-sm text-slate-700">
                                                    {assignment.externalMarks}
                                                </td>

                                                <td className="px-4 py-4 text-sm font-semibold text-slate-800">
                                                    {assignment.totalMarks}
                                                </td>
                                            </tr>
                                        )
                                    )}
                                </tbody>
                            </table>
                        </div>
                    )}
            </div>
        </div>
    );
};

export default TeacherSubjects;