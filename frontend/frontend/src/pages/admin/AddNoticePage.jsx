import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../services/api";
import noticeService from "../../services/noticeService";

function AddNoticePage() {
    const navigate = useNavigate();

    const [branches, setBranches] = useState([]);
    const [semesters, setSemesters] = useState([]);

    const [formData, setFormData] = useState({
        title: "",
        description: "",
        priority: "MEDIUM",
        targetAudience: "ALL",
        branchId: "",
        semesterId: "",
    });

    const [loading, setLoading] = useState(true);
    const [submitting, setSubmitting] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadData = async () => {
            try {
                setLoading(true);
                setError("");

                const [branchesResponse, semestersResponse] =
                    await Promise.all([
                        api.get("/admin/branches"),
                        api.get("/admin/semesters"),
                    ]);

                setBranches(branchesResponse.data);
                setSemesters(semestersResponse.data);
            } catch (err) {
                console.error(err);
                setError("Failed to load branches and semesters.");
            } finally {
                setLoading(false);
            }
        };

        loadData();
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

            const noticeData = {
                title: formData.title.trim(),
                description: formData.description.trim(),
                priority: formData.priority,
                targetAudience: formData.targetAudience,
                branchId: formData.branchId
                    ? Number(formData.branchId)
                    : null,
                semesterId: formData.semesterId
                    ? Number(formData.semesterId)
                    : null,
            };

            await noticeService.createNotice(noticeData);

            navigate("/admin/notices");
        } catch (err) {
            console.error(err);

            const message =
                err.response?.data?.message ||
                err.response?.data ||
                "Failed to create notice.";

            setError(
                typeof message === "string"
                    ? message
                    : "Failed to create notice."
            );
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <div className="p-6">
            <div className="mb-6">
                <h1 className="text-2xl font-bold text-slate-800">
                    Add Notice
                </h1>

                <p className="mt-1 text-sm text-slate-500">
                    Publish a notice for students, teachers, or everyone.
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
                <div className="space-y-5">
                    <div>
                        <label className="mb-1 block text-sm font-medium text-slate-700">
                            Title
                        </label>

                        <input
                            type="text"
                            name="title"
                            value={formData.title}
                            onChange={handleChange}
                            maxLength={200}
                            required
                            placeholder="Enter notice title"
                            className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                        />
                    </div>

                    <div>
                        <label className="mb-1 block text-sm font-medium text-slate-700">
                            Description
                        </label>

                        <textarea
                            name="description"
                            value={formData.description}
                            onChange={handleChange}
                            maxLength={2000}
                            rows={5}
                            required
                            placeholder="Enter notice description"
                            className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                        />
                    </div>

                    <div className="grid grid-cols-1 gap-5 md:grid-cols-2">
                        <div>
                            <label className="mb-1 block text-sm font-medium text-slate-700">
                                Priority
                            </label>

                            <select
                                name="priority"
                                value={formData.priority}
                                onChange={handleChange}
                                required
                                className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                            >
                                <option value="LOW">Low</option>
                                <option value="MEDIUM">Medium</option>
                                <option value="HIGH">High</option>
                                <option value="URGENT">Urgent</option>
                            </select>
                        </div>

                        <div>
                            <label className="mb-1 block text-sm font-medium text-slate-700">
                                Target Audience
                            </label>

                            <select
                                name="targetAudience"
                                value={formData.targetAudience}
                                onChange={handleChange}
                                required
                                className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                            >
                                <option value="ALL">Everyone</option>
                                <option value="STUDENTS">Students</option>
                                <option value="TEACHERS">Teachers</option>
                            </select>
                        </div>

                        <div>
                            <label className="mb-1 block text-sm font-medium text-slate-700">
                                Branch
                            </label>

                            <select
                                name="branchId"
                                value={formData.branchId}
                                onChange={handleChange}
                                disabled={loading}
                                className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                            >
                                <option value="">
                                    {loading
                                        ? "Loading branches..."
                                        : "All branches"}
                                </option>

                                {branches.map((branch) => (
                                    <option
                                        key={branch.id}
                                        value={branch.id}
                                    >
                                        {branch.name}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div>
                            <label className="mb-1 block text-sm font-medium text-slate-700">
                                Semester
                            </label>

                            <select
                                name="semesterId"
                                value={formData.semesterId}
                                onChange={handleChange}
                                disabled={loading}
                                className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200"
                            >
                                <option value="">
                                    {loading
                                        ? "Loading semesters..."
                                        : "All semesters"}
                                </option>

                                {semesters.map((semester) => (
                                    <option
                                        key={semester.id}
                                        value={semester.id}
                                    >
                                        Semester {semester.semesterNumber}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>
                </div>

                <div className="mt-6 flex gap-3">
                    <button
                        type="submit"
                        disabled={submitting || loading}
                        className="rounded-lg bg-blue-600 px-5 py-2 text-sm font-medium text-white hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-60"
                    >
                        {submitting ? "Saving..." : "Save Notice"}
                    </button>

                    <button
                        type="button"
                        onClick={() => navigate("/admin/notices")}
                        className="rounded-lg bg-slate-200 px-5 py-2 text-sm font-medium text-slate-700 hover:bg-slate-300"
                    >
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
}

export default AddNoticePage;