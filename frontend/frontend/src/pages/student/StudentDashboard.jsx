import { useEffect, useState } from "react";
import studentService from "../../services/studentService";

const StudentDashboard = () => {
    const [studentName, setStudentName] = useState("Student");

    useEffect(() => {
        const loadStudentProfile = async () => {
            try {
                const profile = await studentService.getMyProfile();

                const fullName =
                    profile.studentName ||
                    profile.name ||
                    `${profile.firstName || ""} ${profile.lastName || ""}`.trim();

                if (fullName) {
                    setStudentName(fullName);
                }
            } catch (error) {
                console.error("Failed to load student profile:", error);
            }
        };

        loadStudentProfile();
    }, []);

    return (
        <div className="min-h-screen bg-slate-100 p-4 sm:p-6 lg:p-8">
            {/* Welcome section */}
            <div className="mb-8 overflow-hidden rounded-2xl bg-gradient-to-r from-blue-700 via-blue-600 to-indigo-600 p-6 text-white shadow-lg sm:p-8">
                <div className="flex flex-col justify-between gap-6 md:flex-row md:items-center">
                    <div>
                        <p className="mb-2 text-sm font-medium text-blue-100">
                            Student Portal
                        </p>

                        <h1 className="text-3xl font-bold sm:text-4xl">
                            Welcome back, {studentName}!
                        </h1>

                        <p className="mt-2 max-w-xl text-blue-100">
                            Keep track of your academic progress, attendance,
                            fees, and important college updates.
                        </p>
                    </div>

                    <div className="hidden h-24 w-24 items-center justify-center rounded-full bg-white/15 text-5xl md:flex">
                        🎓
                    </div>
                </div>
            </div>

            {/* Summary cards */}
            <div className="mb-8 grid gap-5 sm:grid-cols-2 xl:grid-cols-4">
                <div className="rounded-2xl border border-blue-100 bg-white p-5 shadow-sm transition hover:-translate-y-1 hover:shadow-md">
                    <div className="flex items-start justify-between">
                        <div>
                            <p className="text-sm font-medium text-slate-500">
                                Attendance
                            </p>

                            <h2 className="mt-3 text-3xl font-bold text-blue-600">
                                --
                            </h2>

                            <p className="mt-2 text-xs text-slate-400">
                                Overall attendance
                            </p>
                        </div>

                        <div className="rounded-xl bg-blue-100 p-3 text-2xl">
                            📊
                        </div>
                    </div>
                </div>

                <div className="rounded-2xl border border-emerald-100 bg-white p-5 shadow-sm transition hover:-translate-y-1 hover:shadow-md">
                    <div className="flex items-start justify-between">
                        <div>
                            <p className="text-sm font-medium text-slate-500">
                                Subjects
                            </p>

                            <h2 className="mt-3 text-3xl font-bold text-emerald-600">
                                --
                            </h2>

                            <p className="mt-2 text-xs text-slate-400">
                                Enrolled this semester
                            </p>
                        </div>

                        <div className="rounded-xl bg-emerald-100 p-3 text-2xl">
                            📚
                        </div>
                    </div>
                </div>

                <div className="rounded-2xl border border-rose-100 bg-white p-5 shadow-sm transition hover:-translate-y-1 hover:shadow-md">
                    <div className="flex items-start justify-between">
                        <div>
                            <p className="text-sm font-medium text-slate-500">
                                Pending Fees
                            </p>

                            <h2 className="mt-3 text-3xl font-bold text-rose-600">
                                ₹--
                            </h2>

                            <p className="mt-2 text-xs text-slate-400">
                                Amount to be paid
                            </p>
                        </div>

                        <div className="rounded-xl bg-rose-100 p-3 text-2xl">
                            💳
                        </div>
                    </div>
                </div>

                <div className="rounded-2xl border border-purple-100 bg-white p-5 shadow-sm transition hover:-translate-y-1 hover:shadow-md">
                    <div className="flex items-start justify-between">
                        <div>
                            <p className="text-sm font-medium text-slate-500">
                                Notices
                            </p>

                            <h2 className="mt-3 text-3xl font-bold text-purple-600">
                                --
                            </h2>

                            <p className="mt-2 text-xs text-slate-400">
                                Important updates
                            </p>
                        </div>

                        <div className="rounded-xl bg-purple-100 p-3 text-2xl">
                            🔔
                        </div>
                    </div>
                </div>
            </div>

            {/* Main content */}
            <div className="grid gap-6 lg:grid-cols-3">
                {/* Academic progress */}
                <div className="rounded-2xl bg-white p-6 shadow-sm lg:col-span-2">
                    <div className="mb-5 flex items-center justify-between">
                        <div>
                            <h2 className="text-xl font-bold text-slate-800">
                                Academic Overview
                            </h2>

                            <p className="mt-1 text-sm text-slate-500">
                                Your current academic performance
                            </p>
                        </div>

                        <span className="rounded-full bg-blue-100 px-3 py-1 text-xs font-semibold text-blue-700">
                            Current Semester
                        </span>
                    </div>

                    <div className="space-y-5">
                        <div>
                            <div className="mb-2 flex justify-between text-sm">
                                <span className="font-medium text-slate-600">
                                    Attendance
                                </span>

                                <span className="font-semibold text-slate-700">
                                    --
                                </span>
                            </div>

                            <div className="h-3 overflow-hidden rounded-full bg-slate-100">
                                <div className="h-full w-0 rounded-full bg-blue-600" />
                            </div>
                        </div>

                        <div>
                            <div className="mb-2 flex justify-between text-sm">
                                <span className="font-medium text-slate-600">
                                    Subject Completion
                                </span>

                                <span className="font-semibold text-slate-700">
                                    --
                                </span>
                            </div>

                            <div className="h-3 overflow-hidden rounded-full bg-slate-100">
                                <div className="h-full w-0 rounded-full bg-emerald-500" />
                            </div>
                        </div>

                        <div>
                            <div className="mb-2 flex justify-between text-sm">
                                <span className="font-medium text-slate-600">
                                    Fee Payment
                                </span>

                                <span className="font-semibold text-slate-700">
                                    --
                                </span>
                            </div>

                            <div className="h-3 overflow-hidden rounded-full bg-slate-100">
                                <div className="h-full w-0 rounded-full bg-purple-500" />
                            </div>
                        </div>
                    </div>
                </div>

                {/* Quick actions */}
                <div className="rounded-2xl bg-white p-6 shadow-sm">
                    <h2 className="text-xl font-bold text-slate-800">
                        Quick Actions
                    </h2>

                    <p className="mt-1 text-sm text-slate-500">
                        Access frequently used sections
                    </p>

                    <div className="mt-5 space-y-3">
                        <a
                            href="/student/attendance"
                            className="flex items-center gap-3 rounded-xl border border-slate-200 p-3 transition hover:border-blue-300 hover:bg-blue-50"
                        >
                            <span className="rounded-lg bg-blue-100 p-2 text-lg">
                                📊
                            </span>

                            <div>
                                <p className="font-semibold text-slate-700">
                                    View Attendance
                                </p>

                                <p className="text-xs text-slate-400">
                                    Check attendance records
                                </p>
                            </div>
                        </a>

                        <a
                            href="/student/subjects"
                            className="flex items-center gap-3 rounded-xl border border-slate-200 p-3 transition hover:border-emerald-300 hover:bg-emerald-50"
                        >
                            <span className="rounded-lg bg-emerald-100 p-2 text-lg">
                                📚
                            </span>

                            <div>
                                <p className="font-semibold text-slate-700">
                                    View Subjects
                                </p>

                                <p className="text-xs text-slate-400">
                                    See your enrolled subjects
                                </p>
                            </div>
                        </a>

                        <a
                            href="/student/fees"
                            className="flex items-center gap-3 rounded-xl border border-slate-200 p-3 transition hover:border-rose-300 hover:bg-rose-50"
                        >
                            <span className="rounded-lg bg-rose-100 p-2 text-lg">
                                💳
                            </span>

                            <div>
                                <p className="font-semibold text-slate-700">
                                    Check Fees
                                </p>

                                <p className="text-xs text-slate-400">
                                    View payment details
                                </p>
                            </div>
                        </a>

                        <a
                            href="/student/notices"
                            className="flex items-center gap-3 rounded-xl border border-slate-200 p-3 transition hover:border-purple-300 hover:bg-purple-50"
                        >
                            <span className="rounded-lg bg-purple-100 p-2 text-lg">
                                🔔
                            </span>

                            <div>
                                <p className="font-semibold text-slate-700">
                                    Read Notices
                                </p>

                                <p className="text-xs text-slate-400">
                                    View college announcements
                                </p>
                            </div>
                        </a>
                    </div>
                </div>
            </div>

            {/* Bottom sections */}
            <div className="mt-6 grid gap-6 lg:grid-cols-2">
                <div className="rounded-2xl bg-white p-6 shadow-sm">
                    <div className="mb-4 flex items-center justify-between">
                        <div>
                            <h2 className="text-xl font-bold text-slate-800">
                                Recent Notices
                            </h2>

                            <p className="mt-1 text-sm text-slate-500">
                                Latest announcements from the college
                            </p>
                        </div>

                        <a
                            href="/student/notices"
                            className="text-sm font-semibold text-blue-600 hover:text-blue-800"
                        >
                            View all
                        </a>
                    </div>

                    <div className="rounded-xl border border-dashed border-slate-300 bg-slate-50 p-6 text-center">
                        <div className="mb-2 text-3xl">🔔</div>

                        <p className="font-medium text-slate-600">
                            No recent notices
                        </p>

                        <p className="mt-1 text-sm text-slate-400">
                            New announcements will appear here.
                        </p>
                    </div>
                </div>

                <div className="rounded-2xl bg-white p-6 shadow-sm">
                    <div className="mb-4 flex items-center justify-between">
                        <div>
                            <h2 className="text-xl font-bold text-slate-800">
                                Upcoming Classes
                            </h2>

                            <p className="mt-1 text-sm text-slate-500">
                                Your upcoming timetable
                            </p>
                        </div>

                        <a
                            href="/student/timetable"
                            className="text-sm font-semibold text-blue-600 hover:text-blue-800"
                        >
                            View timetable
                        </a>
                    </div>

                    <div className="rounded-xl border border-dashed border-slate-300 bg-slate-50 p-6 text-center">
                        <div className="mb-2 text-3xl">🗓️</div>

                        <p className="font-medium text-slate-600">
                            No timetable available
                        </p>

                        <p className="mt-1 text-sm text-slate-400">
                            Your upcoming classes will appear here.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default StudentDashboard;