import { useEffect, useState } from "react";
import noticeService from "../../services/noticeService";

const StudentNotices = () => {
    const [notices, setNotices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchNotices = async () => {
            try {
                const data = await noticeService.getStudentNotices();
                setNotices(data);
            } catch (err) {
                console.error(err);
                setError("Failed to load notices.");
            } finally {
                setLoading(false);
            }
        };

        fetchNotices();
    }, []);

    if (loading) {
        return <p className="p-6 text-gray-600">Loading notices...</p>;
    }

    return (
        <div className="space-y-6 p-6">
            <div>
                <h1 className="text-3xl font-bold text-slate-800">
                    Notices
                </h1>
                <p className="text-gray-600">
                    Important announcements for students
                </p>
            </div>

            {error && (
                <div className="rounded-lg bg-red-100 p-4 text-red-700">
                    {error}
                </div>
            )}

            {!error && notices.length === 0 && (
                <div className="rounded-lg bg-white p-6 text-gray-600 shadow">
                    No notices available.
                </div>
            )}

            {!error && notices.length > 0 && (
                <div className="space-y-4">
                    {notices.map((notice) => (
                        <div
                            key={notice.id}
                            className="rounded-lg bg-white p-6 shadow"
                        >
                            <div className="flex flex-wrap items-start justify-between gap-3">
                                <h2 className="text-xl font-semibold text-slate-800">
                                    {notice.title}
                                </h2>

                                <span
                                    className={`rounded-full px-3 py-1 text-sm font-medium ${
                                        notice.priority === "URGENT"
                                            ? "bg-red-100 text-red-700"
                                            : notice.priority === "HIGH"
                                              ? "bg-orange-100 text-orange-700"
                                              : notice.priority === "MEDIUM"
                                                ? "bg-yellow-100 text-yellow-700"
                                                : "bg-green-100 text-green-700"
                                    }`}
                                >
                                    {notice.priority}
                                </span>
                            </div>

                            <p className="mt-3 text-gray-700">
                                {notice.description || notice.content}
                            </p>

                            <p className="mt-4 text-sm text-gray-500">
                                Published on:{" "}
                                {notice.createdAt ||
                                    notice.publishDate ||
                                    "-"}
                            </p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default StudentNotices;