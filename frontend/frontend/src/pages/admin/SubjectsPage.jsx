import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import subjectService from "../../services/subjectService";

function SubjectsPage() {
  const navigate = useNavigate();

  const [subjects, setSubjects] = useState([]);
  const [search, setSearch] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadSubjects = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await subjectService.getAllSubjects();
      setSubjects(data);
    } catch (err) {
      setError(err.response?.data?.message || "Failed to load subjects.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadSubjects();
  }, []);

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this subject?",
    );

    if (!confirmed) {
      return;
    }

    try {
      await subjectService.deleteSubject(id);
      setSubjects((previousSubjects) =>
        previousSubjects.filter((subject) => subject.id !== id),
      );
    } catch (err) {
      alert(err.response?.data?.message || "Failed to delete subject.");
    }
  };

  const filteredSubjects = subjects.filter((subject) => {
    const searchText = search.toLowerCase();

    return (
      subject.subjectCode?.toLowerCase().includes(searchText) ||
      subject.subjectName?.toLowerCase().includes(searchText) ||
      subject.subjectType?.toLowerCase().includes(searchText)
    );
  });

  return (
    <div>
      <div className="flex items-center justify-between mb-8">
        <div>
          <h1 className="text-3xl font-bold text-slate-800">Subjects</h1>

          <p className="text-slate-600 mt-2">Manage all subjects here.</p>
        </div>

        <button
          onClick={() => navigate("/admin/subjects/add")}
          className="bg-slate-700 hover:bg-slate-800 text-white px-5 py-3 rounded-lg"
        >
          Add Subject
        </button>
      </div>

      <div className="bg-white rounded-xl shadow-sm p-5">
        <div className="flex items-center justify-between mb-5">
          <h2 className="text-xl font-semibold text-slate-800">Subject List</h2>

          <span className="text-slate-600">
            Total Subjects: {filteredSubjects.length}
          </span>
        </div>

        <input
          type="text"
          placeholder="Search by code, name, or type"
          value={search}
          onChange={(event) => setSearch(event.target.value)}
          className="w-full md:w-96 border border-slate-300 rounded-lg px-4 py-3 mb-5 focus:outline-none focus:ring-2 focus:ring-slate-400"
        />

        {loading && <p className="text-slate-600">Loading subjects...</p>}

        {!loading && error && <p className="text-red-600">{error}</p>}

        {!loading && !error && (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse">
              <thead>
                <tr className="bg-slate-100 text-left">
                  <th className="border-b border-slate-300 px-4 py-3">ID</th>

                  <th className="border-b border-slate-300 px-4 py-3">Code</th>

                  <th className="border-b border-slate-300 px-4 py-3">Name</th>

                  <th className="border-b border-slate-300 px-4 py-3">
                    Credits
                  </th>

                  <th className="border-b border-slate-300 px-4 py-3">Type</th>

                  <th className="border-b border-slate-300 px-4 py-3">
                    Actions
                  </th>
                </tr>
              </thead>

              <tbody>
                {filteredSubjects.length === 0 ? (
                  <tr>
                    <td
                      colSpan="6"
                      className="text-center text-slate-500 px-4 py-8"
                    >
                      No subjects found.
                    </td>
                  </tr>
                ) : (
                  filteredSubjects.map((subject) => (
                    <tr key={subject.id} className="hover:bg-slate-50">
                      <td className="border-b border-slate-200 px-4 py-3">
                        {subject.id}
                      </td>

                      <td className="border-b border-slate-200 px-4 py-3 font-medium">
                        {subject.subjectCode}
                      </td>

                      <td className="border-b border-slate-200 px-4 py-3">
                        {subject.subjectName}
                      </td>

                      <td className="border-b border-slate-200 px-4 py-3">
                        {subject.credits}
                      </td>

                      <td className="border-b border-slate-200 px-4 py-3">
                        {subject.subjectType}
                      </td>

                      <td className="border-b border-slate-200 px-4 py-3">
                        <button
                          onClick={() => handleDelete(subject.id)}
                          className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-lg"
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}

export default SubjectsPage;
