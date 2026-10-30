import { Routes, Route, Navigate } from "react-router-dom";

import LoginPage from "./pages/auth/LoginPage";

import AdminDashboard from "./pages/admin/AdminDashboard";
import TeacherDashboard from "./pages/teacher/TeacherDashboard";
import StudentDashboard from "./pages/student/StudentDashboard";

import ProtectedRoute from "./routes/ProtectedRoute";

import StudentsPage from "./pages/admin/StudentsPage";
import StudentForm from "./pages/admin/StudentForm";
import EditStudentPage from "./pages/admin/EditStudentPage";

import TeachersPage from "./pages/admin/TeachersPage";
import EditTeacherPage from "./pages/admin/EditTeacherPage";
import AddTeacherPage from "./pages/admin/AddTeacherPage";

import DepartmentsPage from "./pages/admin/DepartmentsPage";
import BranchesPage from "./pages/admin/BranchesPage";
import SemestersPage from "./pages/admin/SemestersPage";

import AdminLayout from "./components/layout/AdminLayout";
import StudentLayout from "./components/layout/StudentLayout";
import TeacherLayout from "./components/layout/TeacherLayout";

import SubjectsPage from "./pages/admin/SubjectsPage";
import AddSubjectPage from "./pages/admin/AddSubjectPage";

import TimetablePage from "./pages/admin/TimetablePage";
import AddTimetablePage from "./pages/admin/AddTimetablePage";

import TeacherSubjectsPage from "./pages/admin/TeacherSubjectsPage";
import AddTeacherSubjectPage from "./pages/admin/AddTeacherSubjectPage";

import NoticesPage from "./pages/admin/NoticesPage";
import AddNoticePage from "./pages/admin/AddNoticePage";

import FeesPage from "./pages/admin/FeesPage";
import AddFeePage from "./pages/admin/AddFeePage";
import PayFeePage from "./pages/admin/PayFeePage";

import StudentProfile from "./pages/student/StudentProfile";
import StudentAttendance from "./pages/student/StudentAttendance";
import StudentSubjects from "./pages/student/StudentSubjects";
import StudentTimetable from "./pages/student/StudentTimetable";
import StudentFees from "./pages/student/StudentFees";
import StudentNotices from "./pages/student/StudentNotices";
import ChatAssistant from "./pages/student/ChatAssistant";

import TeacherProfile from "./pages/teacher/TeacherProfile";
import TeacherSubjects from "./pages/teacher/TeacherSubjects";
import TeacherTimetable from "./pages/teacher/TeacherTimetable";
import TeacherAttendance from "./pages/teacher/TeacherAttendance";
import CreateAttendanceSession from "./pages/teacher/CreateAttendanceSession";
import MarkAttendance from "./pages/teacher/MarkAttendance";
import TeacherNotices from "./pages/teacher/TeacherNotices";


function AdminRoute({ children }) {
  return (
    <ProtectedRoute allowedRoles={["ADMIN"]}>
      <AdminLayout>{children}</AdminLayout>
    </ProtectedRoute>
  );
}

function App() {
  return (
    <Routes>
      {/* Public routes */}
      <Route path="/" element={<Navigate to="/login" replace />} />

      <Route path="/login" element={<LoginPage />} />

      {/* Student routes */}
      <Route
        path="/student"
        element={
          <ProtectedRoute allowedRoles={["STUDENT"]}>
            <StudentLayout />
          </ProtectedRoute>
        }
      >
        <Route path="dashboard" element={<StudentDashboard />} />

        <Route path="profile" element={<StudentProfile />} />

        <Route path="attendance" element={<StudentAttendance />} />

        <Route path="subjects" element={<StudentSubjects />} />

        <Route path="timetable" element={<StudentTimetable />} />

        <Route path="fees" element={<StudentFees />} />

        <Route path="notices" element={<StudentNotices />} />

        <Route path="chat" element={<ChatAssistant />} />

      </Route>

      {/* Teacher routes */}
      <Route
        path="/teacher"
        element={
          <ProtectedRoute allowedRoles={["TEACHER"]}>
            <TeacherLayout />
          </ProtectedRoute>
        }
      >
        <Route path="dashboard" element={<TeacherDashboard />} />
        <Route path="profile" element={<TeacherProfile />} />
        <Route path="subjects" element={<TeacherSubjects />} />
        <Route path="timetable" element={<TeacherTimetable />} />
        <Route path="attendance" element={<TeacherAttendance />} />
        <Route path="attendance/create" element={<CreateAttendanceSession />} />
        <Route
          path="attendance/session/:sessionId/mark"
          element={<MarkAttendance />}
        />
        <Route path="notices" element={<TeacherNotices />} />
      </Route>

      {/* Admin dashboard */}
      <Route
        path="/admin/dashboard"
        element={
          <AdminRoute>
            <AdminDashboard />
          </AdminRoute>
        }
      />

      {/* Student management */}
      <Route
        path="/admin/students"
        element={
          <AdminRoute>
            <StudentsPage />
          </AdminRoute>
        }
      />

      <Route
        path="/admin/students/add"
        element={
          <AdminRoute>
            <StudentForm />
          </AdminRoute>
        }
      />

      <Route
        path="/admin/students/edit/:id"
        element={
          <AdminRoute>
            <EditStudentPage />
          </AdminRoute>
        }
      />

      {/* Teacher management */}
      <Route
        path="/admin/teachers"
        element={
          <AdminRoute>
            <TeachersPage />
          </AdminRoute>
        }
      />

      <Route
        path="/admin/teachers/add"
        element={
          <AdminRoute>
            <AddTeacherPage />
          </AdminRoute>
        }
      />

      <Route
        path="/admin/teachers/edit/:id"
        element={
          <AdminRoute>
            <EditTeacherPage />
          </AdminRoute>
        }
      />

      {/* Department management */}
      <Route
        path="/admin/departments"
        element={
          <AdminRoute>
            <DepartmentsPage />
          </AdminRoute>
        }
      />

      {/* Branch management */}
      <Route
        path="/admin/branches"
        element={
          <AdminRoute>
            <BranchesPage />
          </AdminRoute>
        }
      />

      {/* Semester management */}
      <Route
        path="/admin/semesters"
        element={
          <AdminRoute>
            <SemestersPage />
          </AdminRoute>
        }
      />

      {/* Subject management */}
      <Route
        path="/admin/subjects"
        element={
          <AdminRoute>
            <SubjectsPage />
          </AdminRoute>
        }
      />

      <Route
        path="/admin/subjects/add"
        element={
          <AdminRoute>
            <AddSubjectPage />
          </AdminRoute>
        }
      />

      {/* Timetable management */}
      <Route
        path="/admin/timetable"
        element={
          <AdminRoute>
            <TimetablePage />
          </AdminRoute>
        }
      />

      <Route
        path="/admin/timetable/add"
        element={
          <AdminRoute>
            <AddTimetablePage />
          </AdminRoute>
        }
      />

      {/* Teacher-subject management */}
      <Route
        path="/admin/teacher-subjects"
        element={
          <AdminRoute>
            <TeacherSubjectsPage />
          </AdminRoute>
        }
      />

      <Route
        path="/admin/teacher-subjects/add"
        element={
          <AdminRoute>
            <AddTeacherSubjectPage />
          </AdminRoute>
        }
      />

      {/* Notice management */}
      <Route
        path="/admin/notices"
        element={
          <AdminRoute>
            <NoticesPage />
          </AdminRoute>
        }
      />

      <Route
        path="/admin/notices/add"
        element={
          <AdminRoute>
            <AddNoticePage />
          </AdminRoute>
        }
      />

      {/* Fee management */}
      <Route
        path="/admin/fees"
        element={
          <AdminRoute>
            <FeesPage />
          </AdminRoute>
        }
      />

      <Route
        path="/admin/fees/add"
        element={
          <AdminRoute>
            <AddFeePage />
          </AdminRoute>
        }
      />

      <Route
        path="/admin/fees/:id/pay"
        element={
          <AdminRoute>
            <PayFeePage />
          </AdminRoute>
        }
      />

      {/* Fallback */}
      <Route path="*" element={<h1>Page Not Found</h1>} />
    </Routes>
  );
}

export default App;
