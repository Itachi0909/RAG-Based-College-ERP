import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../services/api";
import feeService from "../../services/feeService";

const AddFeePage = () => {
    const navigate = useNavigate();

    const [students, setStudents] = useState([]);
    const [semesters, setSemesters] = useState([]);

    const [formData, setFormData] = useState({
        studentId: "",
        semesterId: "",
        totalAmount: "",
        dueDate: "",
        remarks: "",
    });

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadData = async () => {
            try {
                const [studentsResponse, semestersResponse] =
                    await Promise.all([
                        api.get("/admin/students"),
                        api.get("/admin/semesters"),
                    ]);

                setStudents(studentsResponse.data);
                setSemesters(semestersResponse.data);
            } catch (err) {
                setError(
                    err.response?.data?.message ||
                        "Failed to load students and semesters."
                );
            } finally {
                setLoading(false);
            }
        };

        loadData();
    }, []);

    const handleChange = (event) => {
        const { name, value } = event.target;

        setFormData((previousData) => ({
            ...previousData,
            [name]: value,
        }));

        if (name === "studentId") {
            setFormData((previousData) => ({
                ...previousData,
                studentId: value,
                semesterId: "",
            }));
        }
    };

    const selectedStudent = students.find(
        (student) =>
            String(student.id) === String(formData.studentId)
    );

    const filteredSemesters = selectedStudent?.branchId
        ? semesters.filter(
              (semester) =>
                  String(semester.branchId) ===
                  String(selectedStudent.branchId)
          )
        : [];

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError("");

        if (
            !formData.studentId ||
            !formData.semesterId ||
            !formData.totalAmount ||
            !formData.dueDate
        ) {
            setError("Please fill in all required fields.");
            return;
        }

        try {
            setSaving(true);

            await feeService.createFee({
                studentId: Number(formData.studentId),
                semesterId: Number(formData.semesterId),
                totalAmount: Number(formData.totalAmount),
                dueDate: formData.dueDate,
                remarks: formData.remarks,
            });

            navigate("/admin/fees");
        } catch (err) {
            setError(
                err.response?.data?.message ||
                    "Failed to create fee."
            );
        } finally {
            setSaving(false);
        }
    };

    if (loading) {
        return (
            <div className="p-6 text-center text-slate-500">
                Loading form data...
            </div>
        );
    }

    return (
        <div className="mx-auto max-w-3xl p-6">
            <div className="mb-6">
                <h1 className="text-3xl font-bold text-slate-800">
                    Add Fee
                </h1>

                <p className="mt-1 text-slate-500">
                    Create a fee record for a student.
                </p>
            </div>

            {error && (
                <div className="mb-5 rounded-lg bg-red-100 p-4 text-red-700">
                    {error}
                </div>
            )}

            <form
                onSubmit={handleSubmit}
                className="space-y-5 rounded-lg bg-white p-6 shadow"
            >
                <div>
                    <label className="mb-2 block font-medium text-slate-700">
                        Student
                    </label>

                    <select
                        name="studentId"
                        value={formData.studentId}
                        onChange={handleChange}
                        className="w-full rounded-lg border border-slate-300 px-4 py-2"
                        required
                    >
                        <option value="">Select student</option>

                        {students.map((student) => (
                            <option key={student.id} value={student.id}>
                                {student.firstName} {student.lastName} (
                                {student.enrollmentNumber})
                            </option>
                        ))}
                    </select>

                    {formData.studentId &&
                        !selectedStudent?.branchId && (
                            <p className="mt-2 text-sm text-red-600">
                                This student is not assigned to a branch.
                            </p>
                        )}
                </div>

                <div>
                    <label className="mb-2 block font-medium text-slate-700">
                        Semester
                    </label>

                    <select
                        name="semesterId"
                        value={formData.semesterId}
                        onChange={handleChange}
                        className="w-full rounded-lg border border-slate-300 px-4 py-2"
                        disabled={!selectedStudent?.branchId}
                        required
                    >
                        <option value="">
                            {selectedStudent?.branchId
                                ? "Select semester"
                                : "Select a student first"}
                        </option>

                        {filteredSemesters.map((semester) => (
                            <option key={semester.id} value={semester.id}>
                                Semester {semester.semesterNumber} —{" "}
                                {semester.branchName ||
                                    "Branch not assigned"}
                            </option>
                        ))}
                    </select>

                    {formData.studentId &&
                        selectedStudent?.branchId &&
                        filteredSemesters.length === 0 && (
                            <p className="mt-2 text-sm text-red-600">
                                No semesters are available for this
                                student's branch.
                            </p>
                        )}
                </div>

                <div>
                    <label className="mb-2 block font-medium text-slate-700">
                        Total Amount
                    </label>

                    <input
                        type="number"
                        name="totalAmount"
                        value={formData.totalAmount}
                        onChange={handleChange}
                        min="0"
                        step="0.01"
                        placeholder="Enter total fee amount"
                        className="w-full rounded-lg border border-slate-300 px-4 py-2"
                        required
                    />
                </div>

                <div>
                    <label className="mb-2 block font-medium text-slate-700">
                        Due Date
                    </label>

                    <input
                        type="date"
                        name="dueDate"
                        value={formData.dueDate}
                        onChange={handleChange}
                        className="w-full rounded-lg border border-slate-300 px-4 py-2"
                        required
                    />
                </div>

                <div>
                    <label className="mb-2 block font-medium text-slate-700">
                        Remarks
                    </label>

                    <textarea
                        name="remarks"
                        value={formData.remarks}
                        onChange={handleChange}
                        rows="4"
                        placeholder="Optional remarks"
                        className="w-full rounded-lg border border-slate-300 px-4 py-2"
                    />
                </div>

                <div className="flex justify-end gap-3">
                    <button
                        type="button"
                        onClick={() => navigate("/admin/fees")}
                        className="rounded-lg bg-slate-200 px-5 py-2 font-medium text-slate-700 hover:bg-slate-300"
                    >
                        Cancel
                    </button>

                    <button
                        type="submit"
                        disabled={saving}
                        className="rounded-lg bg-blue-600 px-5 py-2 font-medium text-white hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-60"
                    >
                        {saving ? "Saving..." : "Create Fee"}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default AddFeePage;