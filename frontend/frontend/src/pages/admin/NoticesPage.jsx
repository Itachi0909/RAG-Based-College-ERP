import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import noticeService from "../../services/noticeService";

function NoticesPage() {
    const navigate = useNavigate();

    const [notices, setNotices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const loadNotices = async () => {
        try {
            setLoading(true);
            setError("");

            const data = await noticeService.getAllNotices();
            setNotices(data);
        } catch (err) {
            console.error(err);
            setError("Failed to load notices.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadNotices();
    }, []);

    const handleDelete = async (id) => {
        if (!id) {
            return;
        }

        const confirmed = window.confirm(
            "Are you sure you want to delete this notice?"
        );

        if (!confirmed) {
            return;
        }

        try {
            await noticeService.deleteNotice(id);
            loadNotices();
        } catch (err) {
            console.error(err);
            setError("Failed to delete notice.");
        }
    };

    const formatAudience = (audience) => {
        if (!audience) return "-";

        return audience
            .toLowerCase()
            .replace("_", " ")
            .replace(/\b\w/g, (letter) => letter.toUpperCase());
    };

    const formatPriority = (priority) => {
        if (!priority) return "-";

        return priority.charAt(0) + priority.slice(1).toLowerCase();
    };

    return (
        <div className="p-6">
            <div className="mb-6 flex flex-wrap items-center justify-between gap-4">
                <div>
                    <h1 className="text-2xl font-bold text-slate-800">
                        Notice Management
                    </h1>

                    <p className="mt-1 text-sm text-slate-500">
                        Create and manage college notices.
                    </p>
                </div>

                <button
                    onClick={() => navigate("/admin/notices/add")}
                    className="rounded-lg bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-700"
                >
                    Add Notice
                </button>
            </div>

            {error && (
                <div className="mb-4 rounded-lg bg-red-100 px-4 py-3 text-sm text-red-700">
                    {error}
                </div>
            )}

            <div className="overflow-x-auto rounded-lg bg-white shadow">
                <table className="min-w-full text-left text-sm">
                    <thead className="bg-slate-100 text-slate-700">
                        <tr>
                            <th className="px-4 py-3">Title</th>
                            <th className="px-4 py-3">Priority</th>
                            <th className="px-4 py-3">Audience</th>
                            <th className="px-4 py-3">Branch</th>
                            <th className="px-4 py-3">Semester</th>
                            <th className="px-4 py-3">Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        {loading ? (
                            <tr>
                                <td
                                    colSpan="6"
                                    className="px-4 py-6 text-center text-slate-500"
                                >
                                    Loading notices...
                                </td>
                            </tr>
                        ) : notices.length === 0 ? (
                            <tr>
                                <td
                                    colSpan="6"
                                    className="px-4 py-6 text-center text-slate-500"
                                >
                                    No notices found.
                                </td>
                            </tr>
                        ) : (
                            notices.map((notice) => (
                                <tr
                                    key={notice.id}
                                    className="border-t border-slate-200"
                                >
                                    <td className="px-4 py-3 font-medium text-slate-800">
                                        {notice.title}
                                    </td>

                                    <td className="px-4 py-3">
                                        {formatPriority(notice.priority)}
                                    </td>

                                    <td className="px-4 py-3">
                                        {formatAudience(
                                            notice.targetAudience
                                        )}
                                    </td>

                                    <td className="px-4 py-3">
                                        {notice.branchName || "All branches"}
                                    </td>

                                    <td className="px-4 py-3">
                                        {notice.semesterNumber
                                            ? `Semester ${notice.semesterNumber}`
                                            : "All semesters"}
                                    </td>

                                    <td className="px-4 py-3">
                                        <button
                                            onClick={() =>
                                                handleDelete(notice.id)
                                            }
                                            className="rounded-lg bg-red-100 px-3 py-1.5 text-xs font-medium text-red-700 hover:bg-red-200"
                                        >
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default NoticesPage;