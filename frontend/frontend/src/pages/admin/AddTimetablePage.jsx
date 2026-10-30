import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import timetableService from "../../services/timetableService";
import teacherSubjectService from "../../services/teacherSubjectService";

function AddTimetablePage() {
    const navigate = useNavigate();

    const [teacherSubjects, setTeacherSubjects] = useState([]);
    const [formData, setFormData] = useState({
        teacherSubjectId: "",
        dayOfWeek: "MONDAY",
        startTime: "",
        endTime: "",
        roomNumber: "",
        lectureType: "THEORY",
    });

    const [loading, setLoading] = useState(true);
    const [submitting, setSubmitting] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadTeacherSubjects = async () => {
            try {
                const data =
                    await teacherSubjectService.getAllTeacherSubjects();

                setTeacherSubjects(data);
            } catch (err) {
                console.error(err);
                setError("Failed to load teacher-subject assignments.");
            } finally {
                setLoading(false);
            }
        };

        loadTeacherSubjects();
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

        try {
            setSubmitting(true);
            setError("");

            await timetableService.createTimetable({
                teacherSubjectId: Number(formData.teacherSubjectId),
                dayOfWeek: formData.dayOfWeek,
                startTime: formData.startTime,
                endTime: formData.endTime,
                roomNumber: formData.roomNumber,
                lectureType: formData.lectureType,
            });

            navigate("/admin/timetable");
        } catch (err) {
            console.error(err);

            const message =
                err.response?.data?.message ||
                err.response?.data ||
                "Failed to create timetable entry.";

            setError(message);
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <div className="p-6">
            <div className="mb-6">
                <h1 className="text-2xl font-bold text-slate-800">
                    Add Timetable
                </h1>

                <p className="mt-1 text-sm text-slate-500">
                    Create a lecture schedule for an assigned teacher and
                    subject.
                </p>
            </div>

            {error && (
                <div className="mb-4 rounded-lg bg-red-100 px-4 py-3 text-sm text-red-700">
                    {error}
                </div>
            )}

            <form
                onSubmit={handleSubmit}
                className="max-w-3xl rounded-lg bg-white p-6 shadow"
            >
                <div className="grid grid-cols-1 gap-5 md:grid-cols-2">
                    <div className="md:col-span-2">
                        <label className="mb-1 block text-sm font-medium text-slate-700">
                            Teacher and Subject Assignment
                        </label>

                        <select
                            name="teacherSubjectId"
                            value={formData.teacherSubjectId}
                            onChange={handleChange}
                            required
                            disabled={loading}
                            className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                        >
                            <option value="">
                                {loading
                                    ? "Loading assignments..."
                                    : "Select teacher and subject"}
                            </option>

                            {teacherSubjects.map((assignment) => (
                                <option
                                    key={assignment.id}
                                    value={assignment.id}
                                >
                                    {assignment.subjectCode} -{" "}
                                    {assignment.subjectName} |{" "}
                                    {assignment.teacherName} | Semester{" "}
                                    {assignment.semesterNumber} |{" "}
                                    {assignment.branchName}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div>
                        <label className="mb-1 block text-sm font-medium text-slate-700">
                            Day
                        </label>

                        <select
                            name="dayOfWeek"
                            value={formData.dayOfWeek}
                            onChange={handleChange}
                            required
                            className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                        >
                            <option value="MONDAY">Monday</option>
                            <option value="TUESDAY">Tuesday</option>
                            <option value="WEDNESDAY">Wednesday</option>
                            <option value="THURSDAY">Thursday</option>
                            <option value="FRIDAY">Friday</option>
                            <option value="SATURDAY">Saturday</option>
                        </select>
                    </div>

                    <div>
                        <label className="mb-1 block text-sm font-medium text-slate-700">
                            Lecture Type
                        </label>

                        <select
                            name="lectureType"
                            value={formData.lectureType}
                            onChange={handleChange}
                            required
                            className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                        >
                            <option value="THEORY">Theory</option>
                            <option value="PRACTICAL">Practical</option>
                            <option value="TUTORIAL">Tutorial</option>
                        </select>
                    </div>

                    <div>
                        <label className="mb-1 block text-sm font-medium text-slate-700">
                            Start Time
                        </label>

                        <input
                            type="time"
                            name="startTime"
                            value={formData.startTime}
                            onChange={handleChange}
                            required
                            className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                        />
                    </div>

                    <div>
                        <label className="mb-1 block text-sm font-medium text-slate-700">
                            End Time
                        </label>

                        <input
                            type="time"
                            name="endTime"
                            value={formData.endTime}
                            onChange={handleChange}
                            required
                            className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                        />
                    </div>

                    <div>
                        <label className="mb-1 block text-sm font-medium text-slate-700">
                            Room Number
                        </label>

                        <input
                            type="text"
                            name="roomNumber"
                            value={formData.roomNumber}
                            onChange={handleChange}
                            placeholder="Example: Room 204"
                            className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                        />
                    </div>
                </div>

                <div className="mt-6 flex gap-3">
                    <button
                        type="submit"
                        disabled={submitting || loading}
                        className="rounded-lg bg-blue-600 px-5 py-2 text-sm font-medium text-white hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-60"
                    >
                        {submitting ? "Saving..." : "Save Timetable"}
                    </button>

                    <button
                        type="button"
                        onClick={() => navigate("/admin/timetable")}
                        className="rounded-lg bg-slate-200 px-5 py-2 text-sm font-medium text-slate-700 hover:bg-slate-300"
                    >
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
}

export default AddTimetablePage;