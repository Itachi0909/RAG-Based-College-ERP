import { useEffect, useState } from "react";
import studentService from "../../services/studentService";
import { useNavigate } from "react-router-dom";

function StudentsPage() {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedBranch, setSelectedBranch] = useState("");
  const [selectedSemester, setSelectedSemester] = useState("");

  const navigate = useNavigate();

  // Pagination
  const [currentPage, setCurrentPage] = useState(1);
  const studentsPerPage = 10;

  useEffect(() => {
    loadStudents();
  }, []);

  useEffect(() => {
    setCurrentPage(1);
  }, [searchTerm, selectedBranch, selectedSemester]);

  const loadStudents = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await studentService.getAllStudents();

      setStudents(data);
    } catch (error) {
      console.error("Failed to load students:", error);

      setError("Failed to load students.");
    } finally {
      setLoading(false);
    }
  };

  // ==========================
  // Pagination Calculations
  // ==========================

  const filteredStudents = students.filter((student) => {
    const search = searchTerm.toLowerCase();

    const fullName = `${student.firstName} ${student.lastName}`.toLowerCase();

    const matchesSearch =
      fullName.includes(search) ||
      student.enrollmentNumber?.toLowerCase().includes(search) ||
      student.email?.toLowerCase().includes(search) ||
      student.phone?.toLowerCase().includes(search);

    const matchesBranch =
      selectedBranch === "" || student.branchName === selectedBranch;

    const matchesSemester =
      selectedSemester === "" ||
      String(student.semesterNumber) === selectedSemester;

    return matchesSearch && matchesBranch && matchesSemester;
  });

  const totalPages = Math.ceil(filteredStudents.length / studentsPerPage);

  const startIndex = (currentPage - 1) * studentsPerPage;

  const endIndex = startIndex + studentsPerPage;

  const currentStudents = filteredStudents.slice(startIndex, endIndex);

  const handlePrevious = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  const handleNext = () => {
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this student?",
    );

    if (!confirmed) {
      return;
    }

    try {
      await studentService.deleteStudent(id);

      alert("Student deleted successfully.");

      loadStudents();
    } catch (error) {
      console.error("Failed to delete student:", error);

      alert("Failed to delete student.");
    }
  };

  return (
    <>
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold">Students</h1>

          <p className="mt-2 text-slate-600">Manage all students here.</p>
        </div>

        <div className="flex items-center gap-4">
          <p className="text-slate-600">Total Students: {students.length}</p>

          <button
            onClick={() => navigate("/admin/students/add")}
            className="px-4 py-2 bg-slate-700 text-white rounded hover:bg-slate-800"
          >
            Add Student
          </button>
        </div>
      </div>

      <div className="mt-6">
        <div className="mb-4 flex flex-col md:flex-row gap-4">
          <input
            type="text"
            placeholder="Search by name, enrollment number, email, or phone"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full md:flex-1 px-4 py-2 border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-slate-500"
          />

          <select
            value={selectedBranch}
            onChange={(e) => setSelectedBranch(e.target.value)}
            className="w-full md:w-56 px-4 py-2 border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-slate-500"
          >
            <option value="">All Branches</option>

            {[...new Set(students.map((student) => student.branchName))]
              .filter(Boolean)
              .map((branch) => (
                <option key={branch} value={branch}>
                  {branch}
                </option>
              ))}
          </select>

          <select
            value={selectedSemester}
            onChange={(e) => setSelectedSemester(e.target.value)}
            className="w-full md:w-56 px-4 py-2 border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-slate-500"
          >
            <option value="">All Semesters</option>

            {[...new Set(students.map((student) => student.semesterNumber))]
              .filter((semester) => semester !== null && semester !== undefined)
              .sort((a, b) => Number(a) - Number(b))
              .map((semester) => (
                <option key={semester} value={String(semester)}>
                  Semester {semester}
                </option>
              ))}
          </select>

          <button
            onClick={() => {
              setSearchTerm("");
              setSelectedBranch("");
              setSelectedSemester("");
            }}
            className="w-full md:w-auto px-4 py-2 bg-slate-500 text-white rounded-lg hover:bg-slate-600"
          >
            Clear Filters
          </button>
        </div>

        {loading && <p>Loading students...</p>}

        {error && <p className="text-red-500">{error}</p>}

        {!loading && !error && filteredStudents.length === 0 && (
          <p className="text-slate-500">
            {searchTerm || selectedBranch || selectedSemester
              ? "No students match the selected filters."
              : "No students found."}
          </p>
        )}

        {!loading && !error && filteredStudents.length > 0 && (
          <>
            <div className="overflow-x-auto bg-white rounded-lg shadow">
              <table className="w-full text-left">
                <thead className="bg-slate-200">
                  <tr>
                    <th className="px-4 py-3">ID</th>

                    <th className="px-4 py-3">Name</th>

                    <th className="px-4 py-3">Enrollment No.</th>

                    <th className="px-4 py-3">Email</th>

                    <th className="px-4 py-3">Phone</th>

                    <th className="px-4 py-3">Branch</th>

                    <th className="px-4 py-3">Semester</th>

                    <th className="px-4 py-3">Actions</th>
                  </tr>
                </thead>

                <tbody>
                  {currentStudents.map((student) => (
                    <tr key={student.id} className="border-t">
                      <td className="px-4 py-3">{student.id}</td>

                      <td className="px-4 py-3">
                        {student.firstName} {student.lastName}
                      </td>

                      <td className="px-4 py-3">{student.enrollmentNumber}</td>

                      <td className="px-4 py-3">{student.email}</td>

                      <td className="px-4 py-3">{student.phone}</td>

                      <td className="px-4 py-3">{student.branchName}</td>

                      <td className="px-4 py-3">{student.semesterNumber}</td>

                      <td className="px-4 py-3">
                        <button
                          onClick={() =>
                            navigate(`/admin/students/edit/${student.id}`)
                          }
                          className="px-3 py-1 bg-blue-600 text-white rounded hover:bg-blue-700"
                        >
                          Edit
                        </button>

                        <button
                          onClick={() => handleDelete(student.id)}
                          className="px-3 py-1 bg-red-600 text-white rounded hover:bg-red-700 ml-2"
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            {/* Pagination Controls */}

            <div className="flex justify-between items-center mt-6">
              <p className="text-slate-600">
                Showing {filteredStudents.length === 0 ? 0 : startIndex + 1}
                {" - "}
                {Math.min(endIndex, filteredStudents.length)}
                {" of "}
                {filteredStudents.length}
                {" students"}
              </p>

              <div className="flex items-center gap-4">
                <button
                  onClick={handlePrevious}
                  disabled={currentPage === 1}
                  className="px-4 py-2 bg-slate-700 text-white rounded disabled:opacity-50"
                >
                  Previous
                </button>

                <span>
                  Page {currentPage} of {totalPages}
                </span>

                <button
                  onClick={handleNext}
                  disabled={currentPage === totalPages}
                  className="px-4 py-2 bg-slate-700 text-white rounded disabled:opacity-50"
                >
                  Next
                </button>
              </div>
            </div>
          </>
        )}
      </div>
    </>
  );
}

export default StudentsPage;
