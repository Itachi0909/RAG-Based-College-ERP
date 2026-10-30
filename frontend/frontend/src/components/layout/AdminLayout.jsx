import { NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

const AdminLayout = ({ children }) => {
    const { logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate("/login", { replace: true });
    };

    const navItems = [
        {
            name: "Dashboard",
            path: "/admin/dashboard",
            icon: "📊",
        },
        {
            name: "Students",
            path: "/admin/students",
            icon: "👨‍🎓",
        },
        {
            name: "Teachers",
            path: "/admin/teachers",
            icon: "👨‍🏫",
        },
        {
            name: "Departments",
            path: "/admin/departments",
            icon: "🏢",
        },
        {
            name: "Branches",
            path: "/admin/branches",
            icon: "🌿",
        },
        {
            name: "Semesters",
            path: "/admin/semesters",
            icon: "📚",
        },
        {
            name: "Subjects",
            path: "/admin/subjects",
            icon: "📖",
        },
        {
            name: "Timetable",
            path: "/admin/timetable",
            icon: "🗓️",
        },
        {
            name: "Teacher Subjects",
            path: "/admin/teacher-subjects",
            icon: "👨‍🏫",
        },
        {
            name: "Notices",
            path: "/admin/notices",
            icon: "📢",
        },
        {
            name: "Fees",
            path: "/admin/fees",
            icon: "💰",
        },
    ];

    return (
        <div className="flex min-h-screen bg-slate-50">
            <aside className="hidden w-64 flex-col bg-slate-900 text-white md:flex">
                <div className="border-b border-slate-700 px-6 py-6">
                    <p className="text-sm font-semibold uppercase tracking-wider text-teal-400">
                        Admin Portal
                    </p>

                    <h2 className="mt-2 text-2xl font-bold">
                        RCET ERP
                    </h2>
                </div>

                <nav className="flex-1 space-y-1 overflow-y-auto p-4">
                    {navItems.map((item) => (
                        <NavLink
                            key={item.path}
                            to={item.path}
                            className={({ isActive }) =>
                                `flex items-center gap-3 rounded-xl px-4 py-3 text-sm font-medium transition ${
                                    isActive
                                        ? "bg-teal-600 text-white"
                                        : "text-slate-300 hover:bg-slate-800 hover:text-white"
                                }`
                            }
                        >
                            <span className="text-lg">
                                {item.icon}
                            </span>

                            <span>{item.name}</span>
                        </NavLink>
                    ))}
                </nav>

                <div className="border-t border-slate-700 p-4">
                    <button
                        onClick={handleLogout}
                        className="flex w-full items-center gap-3 rounded-xl px-4 py-3 text-sm font-medium text-red-300 transition hover:bg-red-500/10 hover:text-red-200"
                    >
                        <span className="text-lg">🚪</span>
                        <span>Logout</span>
                    </button>
                </div>
            </aside>

            <main className="min-w-0 flex-1">
                {children}
            </main>
        </div>
    );
};

export default AdminLayout;