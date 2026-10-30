import { useEffect, useState } from "react";
import noticeService from "../../services/noticeService";

function TeacherNotices() {
    const [notices, setNotices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        loadNotices();
    }, []);

    const loadNotices = async () => {
        try {
            setLoading(true);
            setError("");

            const data = await noticeService.getTeacherNotices();
            setNotices(data || []);
        } catch (err) {
            console.error("Failed to load teacher notices:", err);
            setError("Failed to load notices.");
        } finally {
            setLoading(false);
        }
    };

    const getPriorityClass = (priority) => {
        switch (priority) {
            case "URGENT":
                return "bg-red-100 text-red-700";

            case "HIGH":
                return "bg-orange-100 text-orange-700";

            case "MEDIUM":
                return "bg-yellow-100 text-yellow-700";

            default:
                return "bg-green-100 text-green-700";
        }
    };

    const formatPriority = (priority) => {
        if (!priority) return "LOW";

        return priority.charAt(0) + priority.slice(1).toLowerCase();
    };

    return (
        <div className="min-h-screen bg-slate-50 p-4 sm:p-6">
            <div className="mb-6">
                <h1 className="text-2xl font-bold text-slate-800 sm:text-3xl">
                    Notices
                </h1>

                <p className="mt-1 text-sm text-slate-500">
                    View notices and announcements relevant to you.
                </p>
            </div>

            {error && (
                <div className="mb-5 rounded-lg border border-red-200 bg-red-50 p-4 text-sm text-red-700">
                    {error}
                </div>
            )}

            {loading ? (
                <div className="rounded-xl bg-white p-8 text-center text-slate-500 shadow-sm">
                    Loading notices...
                </div>
            ) : notices.length === 0 ? (
                <div className="rounded-xl bg-white p-8 text-center shadow-sm">
                    <p className="font-medium text-slate-700">
                        No notices available.
                    </p>

                    <p className="mt-1 text-sm text-slate-400">
                        New announcements will appear here.
                    </p>
                </div>
            ) : (
                <div className="grid gap-5 lg:grid-cols-2">
                    {notices.map((notice) => (
                        <div
                            key={notice.id}
                            className="rounded-xl bg-white p-5 shadow-sm transition hover:shadow-md"
                        >
                            <div className="mb-3 flex flex-wrap items-start justify-between gap-3">
                                <h2 className="text-lg font-bold text-slate-800">
                                    {notice.title}
                                </h2>

                                <span
                                    className={`rounded-full px-3 py-1 text-xs font-semibold ${getPriorityClass(
                                        notice.priority
                                    )}`}
                                >
                                    {formatPriority(notice.priority)}
                                </span>
                            </div>

                            <p className="whitespace-pre-line text-sm leading-6 text-slate-600">
                                {notice.description}
                            </p>

                            <div className="mt-5 flex flex-wrap gap-2 text-xs text-slate-500">
                                {notice.branchName && (
                                    <span className="rounded-md bg-slate-100 px-2.5 py-1">
                                        Branch: {notice.branchName}
                                    </span>
                                )}

                                {notice.semesterNumber && (
                                    <span className="rounded-md bg-slate-100 px-2.5 py-1">
                                        Semester {notice.semesterNumber}
                                    </span>
                                )}

                                {notice.targetAudience && (
                                    <span className="rounded-md bg-slate-100 px-2.5 py-1">
                                        {notice.targetAudience}
                                    </span>
                                )}
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default TeacherNotices;