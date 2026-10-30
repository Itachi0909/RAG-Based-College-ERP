import { NavLink } from "react-router-dom";

function Sidebar() {
  return (
    <aside className="w-64 min-h-screen bg-slate-900 text-white">
      {/* Logo / Application Name */}
      <div className="p-6 border-b border-slate-700">
        <h1 className="text-2xl font-bold">RCET ERP</h1>

        <p className="text-sm text-slate-400 mt-1">Admin Panel</p>
      </div>

      {/* Navigation */}
      <nav className="p-4">
        <NavLink
          to="/admin/dashboard"
          className={({ isActive }) =>
            `block px-4 py-3 rounded-lg ${
              isActive ? "bg-slate-700" : "hover:bg-slate-800"
            }`
          }
        >
          Dashboard
        </NavLink>

        <NavLink
          to="/admin/students"
          className={({ isActive }) =>
            `block px-4 py-3 rounded-lg ${
              isActive ? "bg-slate-700" : "hover:bg-slate-800"
            }`
          }
        >
          Students
        </NavLink>

        <NavLink
          to="/admin/teachers"
          className={({ isActive }) =>
            `block px-4 py-3 rounded-lg ${
              isActive ? "bg-slate-700" : "hover:bg-slate-800"
            }`
          }
        >
          Teachers
        </NavLink>

        <NavLink
          to="/admin/departments"
          className={({ isActive }) =>
            `block px-4 py-3 rounded-lg ${
              isActive ? "bg-slate-700" : "hover:bg-slate-800"
            }`
          }
        >
          Departments
        </NavLink>

        <NavLink
          to="/admin/branches"
          className={({ isActive }) =>
            `block px-4 py-3 rounded-lg ${
              isActive ? "bg-slate-700" : "hover:bg-slate-800"
            }`
          }
        >
          Branches
        </NavLink>

        <NavLink
          to="/admin/semesters"
          className={({ isActive }) =>
            `block px-4 py-3 rounded-lg ${
              isActive ? "bg-slate-700" : "hover:bg-slate-800"
            }`
          }
        >
          Semesters
        </NavLink>

        <NavLink
          to="/admin/subjects"
          className={({ isActive }) =>
            `block px-4 py-3 rounded-lg ${
              isActive ? "bg-slate-700" : "hover:bg-slate-800"
            }`
          }
        >
          Subjects
        </NavLink>

        <NavLink
          to="/admin/timetable"
          className={({ isActive }) =>
            `block px-4 py-3 rounded-lg ${
              isActive ? "bg-slate-700" : "hover:bg-slate-800"
            }`
          }
        >
          Timetable
        </NavLink>

        <NavLink
          to="/admin/notices"
          className={({ isActive }) =>
            `block px-4 py-3 rounded-lg ${
              isActive ? "bg-slate-700" : "hover:bg-slate-800"
            }`
          }
        >
          Notices
        </NavLink>

        <NavLink
          to="/admin/fees"
          className={({ isActive }) =>
            `block px-4 py-3 rounded-lg ${
              isActive ? "bg-slate-700" : "hover:bg-slate-800"
            }`
          }
        >
          Fees
        </NavLink>

        <NavLink
          to="/admin/teacher-subjects"
          className={({ isActive }) =>
            `block rounded-lg px-4 py-3 ${
              isActive ? "bg-slate-700" : "hover:bg-slate-800"
            }`
          }
        >
          Assignments
        </NavLink>
      </nav>
    </aside>
  );
}

export default Sidebar;
