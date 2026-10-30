import { Outlet, NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

const StudentLayout = () => {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const navItems = [
    {
      label: "Dashboard",
      path: "/student/dashboard",
      icon: "📊",
    },
    {
      label: "My Profile",
      path: "/student/profile",
      icon: "👤",
    },
    {
      label: "My Attendance",
      path: "/student/attendance",
      icon: "✅",
    },
    {
      label: "My Subjects",
      path: "/student/subjects",
      icon: "📚",
    },
    {
      label: "My Timetable",
      path: "/student/timetable",
      icon: "🗓️",
    },
    {
      label: "My Fees",
      path: "/student/fees",
      icon: "💰",
    },
    {
      label: "Notices",
      path: "/student/notices",
      icon: "📢",
    },
    {
      label: "Chat Assistant",
      path: "/student/chat",
      icon: "🤖",
    },
  ];

  const handleLogout = () => {
    logout();
    navigate("/login", { replace: true });
  };

  return (
    <div className="flex min-h-screen bg-slate-50">
      {/* Sidebar */}
      <aside className="hidden w-64 flex-col bg-slate-900 text-white md:flex">
        {/* Branding */}
        <div className="border-b border-slate-700 px-6 py-6">
          <p className="text-sm font-semibold uppercase tracking-wider text-blue-400">
            Student Portal
          </p>

          <h2 className="mt-2 text-2xl font-bold">RCET ERP</h2>
        </div>

        {/* Navigation */}
        <nav className="flex-1 space-y-1 p-4">
          {navItems.map((item) => (
            <NavLink
              key={item.path}
              to={item.path}
              className={({ isActive }) =>
                `flex items-center gap-3 rounded-xl px-4 py-3 text-sm font-medium transition ${
                  isActive
                    ? "bg-blue-600 text-white"
                    : "text-slate-300 hover:bg-slate-800 hover:text-white"
                }`
              }
            >
              <span className="text-lg">{item.icon}</span>

              <span>{item.label}</span>
            </NavLink>
          ))}
        </nav>

        {/* Logout */}
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

      {/* Main Content */}
      <main className="min-w-0 flex-1">
        <Outlet />
      </main>
    </div>
  );
};

export default StudentLayout;
