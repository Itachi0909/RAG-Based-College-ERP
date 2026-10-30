import { useEffect, useState } from "react";
import teacherService from "../../services/teacherService";
import teacherSubjectService from "../../services/teacherSubjectService";
import timetableService from "../../services/timetableService";
import noticeService from "../../services/noticeService";

const TeacherDashboard = () => {
    const [teacherName, setTeacherName] = useState("Teacher");
    const [subjectCount, setSubjectCount] = useState(0);
    const [studentCount, setStudentCount] = useState(0);
    const [todayClassCount, setTodayClassCount] = useState(0);
    const [noticeCount, setNoticeCount] = useState(0);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadDashboardData();
    }, []);

    const loadDashboardData = async () => {
        try {
            setLoading(true);

            const [
                profile,
                assignedSubjects,
                timetable,
                notices,
            ] = await Promise.all([
                teacherService.getMyProfile(),
                teacherSubjectService.getMyTeacherSubjects(),
                timetableService.getTeacherTimetable(),
                noticeService.getTeacherNotices(),
            ]);

            const fullName = [
                profile?.firstName,
                profile?.middleName,
                profile?.lastName,
            ]
                .filter(Boolean)
                .join(" ");

            if (fullName) {
                setTeacherName(fullName);
            }

            const subjects = assignedSubjects || [];
            const classes = timetable || [];
            const teacherNotices = notices || [];

            setSubjectCount(subjects.length);
            setNoticeCount(teacherNotices.length);

            const today = new Date().toLocaleDateString("en-US", {
                weekday: "long",
            });

            const todayClasses = classes.filter(
                (item) =>
                    item.dayOfWeek?.toUpperCase() === today.toUpperCase()
            );

            setTodayClassCount(todayClasses.length);

            const uniqueStudents = new Set();

            subjects.forEach((subject) => {
                if (subject.studentIds && Array.isArray(subject.studentIds)) {
                    subject.studentIds.forEach((studentId) =>
                        uniqueStudents.add(studentId)
                    );
                }
            });

            setStudentCount(uniqueStudents.size);
        } catch (error) {
            console.error("Failed to load teacher dashboard:", error);
        } finally {
            setLoading(false);
        }
    };

    const cards = [
        {
            icon: "📚",
            value: subjectCount,
            label: "Assigned Subjects",
            color: "from-teal-500 to-cyan-600",
        },
        {
            icon: "👥",
            value: studentCount,
            label: "Total Students",
            color: "from-blue-500 to-indigo-600",
        },
        {
            icon: "🗓️",
            value: todayClassCount,
            label: "Today's Classes",
            color: "from-purple-500 to-pink-600",
        },
        {
            icon: "📢",
            value: noticeCount,
            label: "Available Notices",
            color: "from-orange-500 to-red-600",
        },
    ];

    return (
        <div className="min-h-screen bg-slate-50 p-4 sm:p-6">
            <div className="mb-8 overflow-hidden rounded-2xl bg-gradient-to-r from-teal-600 via-cyan-600 to-blue-700 p-6 text-white shadow-lg">
                <div className="flex flex-col justify-between gap-6 md:flex-row md:items-center">
                    <div>
                        <p className="mb-2 text-sm font-medium text-cyan-100">
                            RCET ERP Teacher Portal
                        </p>

                        <h1 className="text-3xl font-bold">
                            Welcome back, {teacherName}!
                        </h1>

                        <p className="mt-2 text-sm text-cyan-100">
                            Manage your classes, subjects, timetable and
                            academic activities from one place.
                        </p>
                    </div>

                    <div className="hidden rounded-full bg-white/20 p-5 md:block">
                        <span className="text-5xl">👨‍🏫</span>
                    </div>
                </div>
            </div>

            <div className="mb-8 grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
                {cards.map((card) => (
                    <div
                        key={card.label}
                        className="overflow-hidden rounded-2xl bg-white shadow-sm"
                    >
                        <div className={`h-2 bg-gradient-to-r ${card.color}`} />

                        <div className="p-6">
                            <div className="mb-4 text-2xl">
                                {card.icon}
                            </div>

                            <h2 className="text-3xl font-bold text-slate-800">
                                {loading ? "..." : card.value}
                            </h2>

                            <p className="mt-1 text-sm text-slate-500">
                                {card.label}
                            </p>
                        </div>
                    </div>
                ))}
            </div>

            <div className="grid gap-6 lg:grid-cols-2">
                <div className="rounded-2xl bg-white p-6 shadow-sm">
                    <h2 className="mb-4 text-lg font-bold text-slate-800">
                        Quick Actions
                    </h2>

                    <div className="grid gap-3 sm:grid-cols-2">
                        <a
                            href="/teacher/subjects"
                            className="rounded-xl bg-teal-50 p-4 text-sm font-semibold text-teal-700 transition hover:bg-teal-100"
                        >
                            View Assigned Subjects
                        </a>

                        <a
                            href="/teacher/timetable"
                            className="rounded-xl bg-blue-50 p-4 text-sm font-semibold text-blue-700 transition hover:bg-blue-100"
                        >
                            View Timetable
                        </a>

                        <a
                            href="/teacher/attendance"
                            className="rounded-xl bg-purple-50 p-4 text-sm font-semibold text-purple-700 transition hover:bg-purple-100"
                        >
                            Manage Attendance
                        </a>

                        <a
                            href="/teacher/notices"
                            className="rounded-xl bg-orange-50 p-4 text-sm font-semibold text-orange-700 transition hover:bg-orange-100"
                        >
                            Read Notices
                        </a>
                    </div>
                </div>

                <div className="rounded-2xl bg-white p-6 shadow-sm">
                    <h2 className="mb-4 text-lg font-bold text-slate-800">
                        Dashboard Summary
                    </h2>

                    <div className="space-y-4 text-sm">
                        <div className="flex items-center justify-between border-b border-slate-100 pb-3">
                            <span className="text-slate-500">
                                Assigned subjects
                            </span>

                            <span className="font-semibold text-slate-800">
                                {subjectCount}
                            </span>
                        </div>

                        <div className="flex items-center justify-between border-b border-slate-100 pb-3">
                            <span className="text-slate-500">
                                Classes today
                            </span>

                            <span className="font-semibold text-slate-800">
                                {todayClassCount}
                            </span>
                        </div>

                        <div className="flex items-center justify-between">
                            <span className="text-slate-500">
                                Available notices
                            </span>

                            <span className="font-semibold text-slate-800">
                                {noticeCount}
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default TeacherDashboard;