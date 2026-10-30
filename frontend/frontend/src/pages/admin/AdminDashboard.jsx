import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import studentService from "../../services/studentService";
import teacherService from "../../services/teacherService";
import departmentService from "../../services/departmentService";
import branchService from "../../services/branchService";
import semesterService from "../../services/semesterService";

function AdminDashboard() {
    const [students, setStudents] = useState([]);
    const [teachers, setTeachers] = useState([]);

    const [departmentCount, setDepartmentCount] = useState(0);
    const [branchCount, setBranchCount] = useState(0);
    const [semesterCount, setSemesterCount] = useState(0);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadDashboardData = async () => {
            try {
                setLoading(true);
                setError("");

                const [
                    studentsResponse,
                    teachersResponse,
                    departmentsResponse,
                    branchesResponse,
                    semestersResponse,
                ] = await Promise.all([
                    studentService.getAllStudents(),
                    teacherService.getAllTeachers(),
                    departmentService.getAllDepartments(),
                    branchService.getAllBranches(),
                    semesterService.getAllSemesters(),
                ]);

                setStudents(studentsResponse.data || []);
                setTeachers(teachersResponse.data || []);

                setDepartmentCount(
                    departmentsResponse.data?.length || 0
                );

                setBranchCount(
                    branchesResponse.data?.length || 0
                );

                setSemesterCount(
                    semestersResponse.data?.length || 0
                );
            } catch (err) {
                console.error("Error loading dashboard data:", err);

                setError(
                    err.response?.data?.message ||
                        "Unable to load dashboard data."
                );
            } finally {
                setLoading(false);
            }
        };

        loadDashboardData();
    }, []);

    const recentStudents = students.slice(0, 5);

    if (loading) {
        return (
            <div className="flex min-h-screen items-center justify-center bg-slate-50">
                <div className="rounded-xl bg-white px-6 py-4 text-sm font-medium text-slate-600 shadow-sm">
                    Loading dashboard...
                </div>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-slate-50 px-4 py-8 sm:px-6 lg:px-8">
            <div className="mx-auto max-w-7xl">
                {/* Page Header */}
                <div className="mb-8">
                    <p className="text-sm font-semibold uppercase tracking-wider text-blue-600">
                        RCET ERP Management
                    </p>

                    <h1 className="mt-2 text-3xl font-bold tracking-tight text-slate-900 sm:text-4xl">
                        Admin Dashboard
                    </h1>

                    <p className="mt-2 text-slate-500">
                        Monitor and manage your academic information from one
                        place.
                    </p>
                </div>

                {/* Error Message */}
                {error && (
                    <div className="mb-6 rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm font-medium text-red-700">
                        {error}
                    </div>
                )}

                {/* Statistics Cards */}
                <div className="grid gap-5 sm:grid-cols-2 xl:grid-cols-5">
                    {/* Students */}
                    <div className="group rounded-2xl border border-blue-100 bg-white p-5 shadow-sm transition duration-300 hover:-translate-y-1 hover:shadow-xl">
                        <div className="flex items-center justify-between">
                            <div>
                                <p className="text-sm font-medium text-slate-500">
                                    Total Students
                                </p>

                                <h3 className="mt-3 text-3xl font-bold text-slate-800">
                                    {students.length}
                                </h3>
                            </div>

                            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-blue-100 text-2xl">
                                🎓
                            </div>
                        </div>

                        <p className="mt-4 text-xs font-semibold text-blue-600">
                            Registered students
                        </p>
                    </div>

                    {/* Teachers */}
                    <div className="group rounded-2xl border border-purple-100 bg-white p-5 shadow-sm transition duration-300 hover:-translate-y-1 hover:shadow-xl">
                        <div className="flex items-center justify-between">
                            <div>
                                <p className="text-sm font-medium text-slate-500">
                                    Total Teachers
                                </p>

                                <h3 className="mt-3 text-3xl font-bold text-slate-800">
                                    {teachers.length}
                                </h3>
                            </div>

                            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-purple-100 text-2xl">
                                👨‍🏫
                            </div>
                        </div>

                        <p className="mt-4 text-xs font-semibold text-purple-600">
                            Registered teachers
                        </p>
                    </div>

                    {/* Departments */}
                    <div className="group rounded-2xl border border-emerald-100 bg-white p-5 shadow-sm transition duration-300 hover:-translate-y-1 hover:shadow-xl">
                        <div className="flex items-center justify-between">
                            <div>
                                <p className="text-sm font-medium text-slate-500">
                                    Total Departments
                                </p>

                                <h3 className="mt-3 text-3xl font-bold text-slate-800">
                                    {departmentCount}
                                </h3>
                            </div>

                            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-emerald-100 text-2xl">
                                🏢
                            </div>
                        </div>

                        <p className="mt-4 text-xs font-semibold text-emerald-600">
                            Academic departments
                        </p>
                    </div>

                    {/* Branches */}
                    <div className="group rounded-2xl border border-orange-100 bg-white p-5 shadow-sm transition duration-300 hover:-translate-y-1 hover:shadow-xl">
                        <div className="flex items-center justify-between">
                            <div>
                                <p className="text-sm font-medium text-slate-500">
                                    Total Branches
                                </p>

                                <h3 className="mt-3 text-3xl font-bold text-slate-800">
                                    {branchCount}
                                </h3>
                            </div>

                            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-orange-100 text-2xl">
                                🌐
                            </div>
                        </div>

                        <p className="mt-4 text-xs font-semibold text-orange-600">
                            Academic branches
                        </p>
                    </div>

                    {/* Semesters */}
                    <div className="group rounded-2xl border border-cyan-100 bg-white p-5 shadow-sm transition duration-300 hover:-translate-y-1 hover:shadow-xl">
                        <div className="flex items-center justify-between">
                            <div>
                                <p className="text-sm font-medium text-slate-500">
                                    Total Semesters
                                </p>

                                <h3 className="mt-3 text-3xl font-bold text-slate-800">
                                    {semesterCount}
                                </h3>
                            </div>

                            <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-cyan-100 text-2xl">
                                📚
                            </div>
                        </div>

                        <p className="mt-4 text-xs font-semibold text-cyan-600">
                            Branch-wise semesters
                        </p>
                    </div>
                </div>

                {/* Quick Actions */}
                <div className="mt-10">
                    <div className="mb-5">
                        <h2 className="text-2xl font-bold text-slate-900">
                            Quick Actions
                        </h2>

                        <p className="mt-1 text-sm text-slate-500">
                            Quickly access the main management modules.
                        </p>
                    </div>

                    <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-3">
                        <Link
                            to="/admin/students"
                            className="group rounded-2xl border border-slate-200 bg-white p-6 shadow-sm transition duration-300 hover:-translate-y-1 hover:border-blue-300 hover:shadow-xl"
                        >
                            <div className="mb-5 flex h-12 w-12 items-center justify-center rounded-xl bg-blue-100 text-2xl">
                                🎓
                            </div>

                            <h3 className="text-lg font-bold text-slate-800">
                                Manage Students
                            </h3>

                            <p className="mt-2 text-sm leading-6 text-slate-500">
                                Add, edit, delete, and manage student records.
                            </p>

                            <span className="mt-5 inline-block text-sm font-semibold text-blue-600 transition group-hover:translate-x-1">
                                Open module →
                            </span>
                        </Link>

                        <Link
                            to="/admin/teachers"
                            className="group rounded-2xl border border-slate-200 bg-white p-6 shadow-sm transition duration-300 hover:-translate-y-1 hover:border-purple-300 hover:shadow-xl"
                        >
                            <div className="mb-5 flex h-12 w-12 items-center justify-center rounded-xl bg-purple-100 text-2xl">
                                👨‍🏫
                            </div>

                            <h3 className="text-lg font-bold text-slate-800">
                                Manage Teachers
                            </h3>

                            <p className="mt-2 text-sm leading-6 text-slate-500">
                                Manage teacher information and accounts.
                            </p>

                            <span className="mt-5 inline-block text-sm font-semibold text-purple-600 transition group-hover:translate-x-1">
                                Open module →
                            </span>
                        </Link>

                        <Link
                            to="/admin/departments"
                            className="group rounded-2xl border border-slate-200 bg-white p-6 shadow-sm transition duration-300 hover:-translate-y-1 hover:border-emerald-300 hover:shadow-xl"
                        >
                            <div className="mb-5 flex h-12 w-12 items-center justify-center rounded-xl bg-emerald-100 text-2xl">
                                🏢
                            </div>

                            <h3 className="text-lg font-bold text-slate-800">
                                Manage Departments
                            </h3>

                            <p className="mt-2 text-sm leading-6 text-slate-500">
                                Create and manage academic departments.
                            </p>

                            <span className="mt-5 inline-block text-sm font-semibold text-emerald-600 transition group-hover:translate-x-1">
                                Open module →
                            </span>
                        </Link>

                        <Link
                            to="/admin/branches"
                            className="group rounded-2xl border border-slate-200 bg-white p-6 shadow-sm transition duration-300 hover:-translate-y-1 hover:border-orange-300 hover:shadow-xl"
                        >
                            <div className="mb-5 flex h-12 w-12 items-center justify-center rounded-xl bg-orange-100 text-2xl">
                                🌐
                            </div>

                            <h3 className="text-lg font-bold text-slate-800">
                                Manage Branches
                            </h3>

                            <p className="mt-2 text-sm leading-6 text-slate-500">
                                Create and manage department branches.
                            </p>

                            <span className="mt-5 inline-block text-sm font-semibold text-orange-600 transition group-hover:translate-x-1">
                                Open module →
                            </span>
                        </Link>

                        <Link
                            to="/admin/semesters"
                            className="group rounded-2xl border border-slate-200 bg-white p-6 shadow-sm transition duration-300 hover:-translate-y-1 hover:border-cyan-300 hover:shadow-xl"
                        >
                            <div className="mb-5 flex h-12 w-12 items-center justify-center rounded-xl bg-cyan-100 text-2xl">
                                📚
                            </div>

                            <h3 className="text-lg font-bold text-slate-800">
                                Manage Semesters
                            </h3>

                            <p className="mt-2 text-sm leading-6 text-slate-500">
                                Create and manage branch-wise semesters.
                            </p>

                            <span className="mt-5 inline-block text-sm font-semibold text-cyan-600 transition group-hover:translate-x-1">
                                Open module →
                            </span>
                        </Link>
                    </div>
                </div>

                {/* Recent Students */}
                <div className="mt-10 overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">
                    <div className="flex flex-col gap-3 border-b border-slate-100 px-6 py-5 sm:flex-row sm:items-center sm:justify-between">
                        <div>
                            <h2 className="text-xl font-bold text-slate-900">
                                Recent Students
                            </h2>

                            <p className="mt-1 text-sm text-slate-500">
                                Recently registered students.
                            </p>
                        </div>

                        <Link
                            to="/admin/students"
                            className="text-sm font-semibold text-blue-600 transition hover:text-blue-800"
                        >
                            View All →
                        </Link>
                    </div>

                    {recentStudents.length === 0 ? (
                        <div className="px-6 py-10 text-center text-sm text-slate-500">
                            No students found.
                        </div>
                    ) : (
                        <div className="overflow-x-auto">
                            <table className="min-w-full">
                                <thead className="bg-slate-50">
                                    <tr>
                                        <th className="px-6 py-4 text-left text-xs font-semibold uppercase tracking-wider text-slate-500">
                                            Student
                                        </th>

                                        <th className="px-6 py-4 text-left text-xs font-semibold uppercase tracking-wider text-slate-500">
                                            Email
                                        </th>

                                        <th className="px-6 py-4 text-left text-xs font-semibold uppercase tracking-wider text-slate-500">
                                            Roll Number
                                        </th>

                                        <th className="px-6 py-4 text-left text-xs font-semibold uppercase tracking-wider text-slate-500">
                                            Branch
                                        </th>
                                    </tr>
                                </thead>

                                <tbody className="divide-y divide-slate-100">
                                    {recentStudents.map((student) => (
                                        <tr
                                            key={student.id}
                                            className="transition hover:bg-slate-50"
                                        >
                                            <td className="whitespace-nowrap px-6 py-4">
                                                <div className="flex items-center gap-3">
                                                    <div className="flex h-10 w-10 items-center justify-center rounded-full bg-blue-100 font-bold text-blue-700">
                                                        {(
                                                            student.name || "S"
                                                        )
                                                            .charAt(0)
                                                            .toUpperCase()}
                                                    </div>

                                                    <span className="font-semibold text-slate-800">
                                                        {student.name || "N/A"}
                                                    </span>
                                                </div>
                                            </td>

                                            <td className="whitespace-nowrap px-6 py-4 text-sm text-slate-600">
                                                {student.email || "N/A"}
                                            </td>

                                            <td className="whitespace-nowrap px-6 py-4 text-sm text-slate-600">
                                                {student.rollNumber || "N/A"}
                                            </td>

                                            <td className="whitespace-nowrap px-6 py-4 text-sm text-slate-600">
                                                {student.branchName || "N/A"}
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default AdminDashboard;