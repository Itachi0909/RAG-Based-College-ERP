import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import attendanceService from "../../services/attendanceService";
import teacherSubjectService from "../../services/teacherSubjectService";

function CreateAttendanceSession() {
    const navigate = useNavigate();

    const [subjects, setSubjects] = useState([]);
    const [formData, setFormData] = useState({
        teacherSubjectId: "",
        attendanceDate: "",
        startTime: "",
        endTime: "",
        topic: "",
        remarks: "",
    });

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadSubjects = async () => {
            try {
                const data =
                    await teacherSubjectService.getMyTeacherSubjects();

                setSubjects(data);
            } catch (err) {
                console.error("Failed to load subjects:", err);
                setError("Unable to load your assigned subjects.");
            } finally {
                setLoading(false);
            }
        };

        loadSubjects();
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
        setError("");

        if (!formData.teacherSubjectId) {
            setError("Please select a subject.");
            return;
        }

        try {
            setSaving(true);

            await attendanceService.createSession({
                teacherSubjectId: Number(formData.teacherSubjectId),
                attendanceDate: formData.attendanceDate,
                startTime: formData.startTime,
                endTime: formData.endTime,
                topic: formData.topic,
                remarks: formData.remarks,
            });

            navigate("/teacher/attendance");
        } catch (err) {
            console.error("Failed to create attendance session:", err);

            setError(
                err.response?.data?.message ||
                    "Unable to create attendance session."
            );
        } finally {
            setSaving(false);
        }
    };

    return (
        <div className="min-h-screen bg-slate-50 p-6">
            <div className="mb-6">
                <h1 className="text-3xl font-bold text-slate-800">
                    Create Attendance Session
                </h1>

                <p className="mt-1 text-slate-500">
                    Create a session before marking student attendance.
                </p>
            </div>

            <div className="max-w-3xl rounded-2xl bg-white p-6 shadow-sm">
                {error && (
                    <div className="mb-5 rounded-lg border border-red-200 bg-red-50 p-4 text-red-600">
                        {error}
                    </div>
                )}

                {loading ? (
                    <p className="text-slate-500">
                        Loading assigned subjects...
                    </p>
                ) : (
                    <form onSubmit={handleSubmit} className="space-y-5">
                        <div>
                            <label className="mb-2 block text-sm font-medium text-slate-700">
                                Subject
                            </label>

                            <select
                                name="teacherSubjectId"
                                value={formData.teacherSubjectId}
                                onChange={handleChange}
                                required
                                className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none focus:border-teal-500 focus:ring-2 focus:ring-teal-100"
                            >
                                <option value="">
                                    Select subject
                                </option>

                                {subjects.map((subject) => (
                                    <option
                                        key={subject.id}
                                        value={subject.id}
                                    >
                                        {subject.subjectCode} -{" "}
                                        {subject.subjectName} | Semester{" "}
                                        {subject.semesterNumber}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div className="grid gap-5 md:grid-cols-3">
                            <div>
                                <label className="mb-2 block text-sm font-medium text-slate-700">
                                    Date
                                </label>

                                <input
                                    type="date"
                                    name="attendanceDate"
                                    value={formData.attendanceDate}
                                    onChange={handleChange}
                                    required
                                    className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none focus:border-teal-500 focus:ring-2 focus:ring-teal-100"
                                />
                            </div>

                            <div>
                                <label className="mb-2 block text-sm font-medium text-slate-700">
                                    Start Time
                                </label>

                                <input
                                    type="time"
                                    name="startTime"
                                    value={formData.startTime}
                                    onChange={handleChange}
                                    required
                                    className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none focus:border-teal-500 focus:ring-2 focus:ring-teal-100"
                                />
                            </div>

                            <div>
                                <label className="mb-2 block text-sm font-medium text-slate-700">
                                    End Time
                                </label>

                                <input
                                    type="time"
                                    name="endTime"
                                    value={formData.endTime}
                                    onChange={handleChange}
                                    required
                                    className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none focus:border-teal-500 focus:ring-2 focus:ring-teal-100"
                                />
                            </div>
                        </div>

                        <div>
                            <label className="mb-2 block text-sm font-medium text-slate-700">
                                Topic
                            </label>

                            <input
                                type="text"
                                name="topic"
                                value={formData.topic}
                                onChange={handleChange}
                                placeholder="Example: Introduction to DBMS"
                                className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none focus:border-teal-500 focus:ring-2 focus:ring-teal-100"
                            />
                        </div>

                        <div>
                            <label className="mb-2 block text-sm font-medium text-slate-700">
                                Remarks
                            </label>

                            <textarea
                                name="remarks"
                                value={formData.remarks}
                                onChange={handleChange}
                                rows="3"
                                placeholder="Optional remarks"
                                className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none focus:border-teal-500 focus:ring-2 focus:ring-teal-100"
                            />
                        </div>

                        <div className="flex flex-wrap gap-3 pt-2">
                            <button
                                type="submit"
                                disabled={saving}
                                className="rounded-lg bg-teal-600 px-5 py-3 font-semibold text-white transition hover:bg-teal-700 disabled:cursor-not-allowed disabled:opacity-60"
                            >
                                {saving
                                    ? "Creating..."
                                    : "Create Session"}
                            </button>

                            <button
                                type="button"
                                onClick={() =>
                                    navigate("/teacher/attendance")
                                }
                                className="rounded-lg border border-slate-300 px-5 py-3 font-semibold text-slate-700 transition hover:bg-slate-100"
                            >
                                Cancel
                            </button>
                        </div>
                    </form>
                )}
            </div>
        </div>
    );
}

export default CreateAttendanceSession;