import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import teacherService from "../../services/teacherService";

function TeachersPage() {
  const [teachers, setTeachers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [searchTerm, setSearchTerm] = useState("");
  const [selectedDepartment, setSelectedDepartment] = useState("");
  const [selectedStatus, setSelectedStatus] = useState("");

  // Pagination
  const [currentPage, setCurrentPage] = useState(1);
  const teachersPerPage = 10;

  const navigate = useNavigate();

  useEffect(() => {
    loadTeachers();
  }, []);

  const loadTeachers = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await teacherService.getAllTeachers();

      setTeachers(data);
    } catch (error) {
      console.error("Failed to load teachers:", error);
      setError("Failed to load teachers.");
    } finally {
      setLoading(false);
    }
  };

  // Filter teachers
  const filteredTeachers = teachers.filter((teacher) => {
    const search = searchTerm.toLowerCase();

    const fullName =
      `${teacher.firstName || ""} ${teacher.lastName || ""}`.toLowerCase();

    const matchesSearch =
      fullName.includes(search) ||
      teacher.employeeId?.toLowerCase().includes(search) ||
      teacher.email?.toLowerCase().includes(search) ||
      teacher.phone?.toLowerCase().includes(search);

    const matchesDepartment =
      selectedDepartment === "" ||
      teacher.departmentName === selectedDepartment;

    const matchesStatus =
      selectedStatus === "" ||
      teacher.status === selectedStatus;

    return matchesSearch && matchesDepartment && matchesStatus;
  });

  // Reset pagination when filters change
  useEffect(() => {
    setCurrentPage(1);
  }, [searchTerm, selectedDepartment, selectedStatus]);

  // Pagination calculations
  const totalPages = Math.ceil(
    filteredTeachers.length / teachersPerPage
  );

  useEffect(() => {
    if (totalPages > 0 && currentPage > totalPages) {
      setCurrentPage(totalPages);
    }
  }, [currentPage, totalPages]);

  const startIndex = (currentPage - 1) * teachersPerPage;
  const endIndex = startIndex + teachersPerPage;

  const currentTeachers = filteredTeachers.slice(
    startIndex,
    endIndex
  );

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
      "Are you sure you want to delete this teacher?"
    );

    if (!confirmed) {
      return;
    }

    try {
      await teacherService.deleteTeacher(id);

      alert("Teacher deleted successfully.");

      loadTeachers();
    } catch (error) {
      console.error("Failed to delete teacher:", error);
      alert("Failed to delete teacher.");
    }
  };

  const clearFilters = () => {
    setSearchTerm("");
    setSelectedDepartment("");
    setSelectedStatus("");
  };

  return (
    <>
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold">Teachers</h1>

          <p className="mt-2 text-slate-600">
            Manage all teachers here.
          </p>
        </div>

        <button
          onClick={() => navigate("/admin/teachers/add")}
          className="px-4 py-2 bg-slate-700 text-white rounded hover:bg-slate-800"
        >
          Add Teacher
        </button>
      </div>

      <div className="mt-6">
        {loading && <p>Loading teachers...</p>}

        {error && <p className="text-red-500">{error}</p>}

        {!loading && !error && (
          <>
            {/* Search and filters */}
            <div className="mb-4 flex flex-col md:flex-row gap-4">
              <input
                type="text"
                placeholder="Search by name, employee ID, email, or phone"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="w-full md:flex-1 px-4 py-2 border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-slate-500"
              />

              <select
                value={selectedDepartment}
                onChange={(e) =>
                  setSelectedDepartment(e.target.value)
                }
                className="w-full md:w-64 px-4 py-2 border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-slate-500"
              >
                <option value="">All Departments</option>

                {[
                  ...new Set(
                    teachers.map((teacher) => teacher.departmentName)
                  ),
                ]
                  .filter(Boolean)
                  .map((department) => (
                    <option key={department} value={department}>
                      {department}
                    </option>
                  ))}
              </select>

              <select
                value={selectedStatus}
                onChange={(e) => setSelectedStatus(e.target.value)}
                className="w-full md:w-48 px-4 py-2 border border-slate-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-slate-500"
              >
                <option value="">All Statuses</option>
                <option value="ACTIVE">Active</option>
                <option value="INACTIVE">Inactive</option>
              </select>

              <button
                onClick={clearFilters}
                className="w-full md:w-auto px-4 py-2 bg-slate-500 text-white rounded-lg hover:bg-slate-600"
              >
                Clear Filters
              </button>
            </div>

            {/* Empty state */}
            {filteredTeachers.length === 0 ? (
              <p className="text-slate-500">
                {searchTerm ||
                selectedDepartment ||
                selectedStatus
                  ? "No teachers match the selected filters."
                  : "No teachers found."}
              </p>
            ) : (
              <>
                {/* Teacher table */}
                <div className="overflow-x-auto bg-white rounded-lg shadow">
                  <table className="w-full text-left">
                    <thead className="bg-slate-200">
                      <tr>
                        <th className="px-4 py-3">ID</th>
                        <th className="px-4 py-3">Employee ID</th>
                        <th className="px-4 py-3">Name</th>
                        <th className="px-4 py-3">Email</th>
                        <th className="px-4 py-3">Phone</th>
                        <th className="px-4 py-3">Designation</th>
                        <th className="px-4 py-3">Department</th>
                        <th className="px-4 py-3">Branch</th>
                        <th className="px-4 py-3">Status</th>
                        <th className="px-4 py-3">Actions</th>
                      </tr>
                    </thead>

                    <tbody>
                      {currentTeachers.map((teacher) => (
                        <tr
                          key={teacher.id}
                          className="border-t"
                        >
                          <td className="px-4 py-3">
                            {teacher.id}
                          </td>

                          <td className="px-4 py-3">
                            {teacher.employeeId}
                          </td>

                          <td className="px-4 py-3">
                            {teacher.firstName} {teacher.lastName}
                          </td>

                          <td className="px-4 py-3">
                            {teacher.email}
                          </td>

                          <td className="px-4 py-3">
                            {teacher.phone}
                          </td>

                          <td className="px-4 py-3">
                            {teacher.designation}
                          </td>

                          <td className="px-4 py-3">
                            {teacher.departmentName}
                          </td>

                          <td className="px-4 py-3">
                            {teacher.branchName}
                          </td>

                          <td className="px-4 py-3">
                            {teacher.status}
                          </td>

                          <td className="px-4 py-3 whitespace-nowrap">
                            <button
                              onClick={() =>
                                navigate(
                                  `/admin/teachers/edit/${teacher.id}`
                                )
                              }
                              className="px-3 py-1 bg-blue-600 text-white rounded hover:bg-blue-700"
                            >
                              Edit
                            </button>

                            <button
                              onClick={() =>
                                handleDelete(teacher.id)
                              }
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

                {/* Pagination */}
                <div className="flex justify-between items-center mt-6">
                  <p className="text-slate-600">
                    Showing {startIndex + 1} -{" "}
                    {Math.min(endIndex, filteredTeachers.length)}{" "}
                    of {filteredTeachers.length} teachers
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
          </>
        )}
      </div>
    </>
  );
}

export default TeachersPage;