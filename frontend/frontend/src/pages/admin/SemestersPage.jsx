import { useEffect, useState } from "react";
import semesterService from "../../services/semesterService";
import branchService from "../../services/branchService";

const SemestersPage = () => {
    const [semesters, setSemesters] = useState([]);
    const [branches, setBranches] = useState([]);

    const [formData, setFormData] = useState({
        semesterNumber: "",
        branchId: "",
        description: "",
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const loadData = async () => {
        try {
            setLoading(true);
            setError("");

            const [semesterData, branchData] = await Promise.all([
                semesterService.getAllSemesters(),
                branchService.getAllBranches(),
            ]);

            setSemesters(semesterData);
            setBranches(branchData);
        } catch (err) {
            setError(
                err.response?.data?.message ||
                "Failed to load semesters and branches."
            );
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadData();
    }, []);

    const handleChange = (event) => {
        const { name, value } = event.target;

        setFormData((previous) => ({
            ...previous,
            [name]: value,
        }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            setError("");
            setSuccess("");
            setLoading(true);

            const payload = {
                semesterNumber: Number(formData.semesterNumber),
                branchId: Number(formData.branchId),
                description: formData.description,
            };

            await semesterService.createSemester(payload);

            setSuccess("Semester created successfully.");

            setFormData({
                semesterNumber: "",
                branchId: "",
                description: "",
            });

            await loadData();
        } catch (err) {
            setError(
                err.response?.data?.message ||
                "Failed to create semester."
            );
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        const confirmed = window.confirm(
            "Are you sure you want to delete this semester?"
        );

        if (!confirmed) {
            return;
        }

        try {
            setError("");
            setSuccess("");

            await semesterService.deleteSemester(id);

            setSuccess("Semester deleted successfully.");
            await loadData();
        } catch (err) {
            setError(
                err.response?.data?.message ||
                "Failed to delete semester."
            );
        }
    };

    return (
        <div className="p-6">
            <h1 className="mb-6 text-3xl font-bold text-slate-800">
                Semester Management
            </h1>

            {error && (
                <div className="mb-4 rounded bg-red-100 p-3 text-red-700">
                    {error}
                </div>
            )}

            {success && (
                <div className="mb-4 rounded bg-green-100 p-3 text-green-700">
                    {success}
                </div>
            )}

            <form
                onSubmit={handleSubmit}
                className="mb-8 rounded-lg bg-white p-6 shadow"
            >
                <h2 className="mb-4 text-xl font-semibold">
                    Add Semester
                </h2>

                <div className="grid gap-4 md:grid-cols-3">
                    <input
                        type="number"
                        name="semesterNumber"
                        value={formData.semesterNumber}
                        onChange={handleChange}
                        placeholder="Semester number"
                        min="1"
                        max="8"
                        required
                        className="rounded border p-2"
                    />

                    <select
                        name="branchId"
                        value={formData.branchId}
                        onChange={handleChange}
                        required
                        className="rounded border p-2"
                    >
                        <option value="">Select branch</option>

                        {branches.map((branch) => (
                            <option
                                key={branch.id}
                                value={branch.id}
                            >
                                {branch.name}
                            </option>
                        ))}
                    </select>

                    <input
                        type="text"
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                        placeholder="Description"
                        className="rounded border p-2"
                    />
                </div>

                <button
                    type="submit"
                    disabled={loading}
                    className="mt-4 rounded bg-blue-600 px-4 py-2 text-white hover:bg-blue-700 disabled:opacity-50"
                >
                    {loading ? "Saving..." : "Add Semester"}
                </button>
            </form>

            <div className="overflow-x-auto rounded-lg bg-white shadow">
                <table className="min-w-full">
                    <thead className="bg-slate-100">
                        <tr>
                            <th className="px-4 py-3 text-left">
                                ID
                            </th>

                            <th className="px-4 py-3 text-left">
                                Semester Number
                            </th>

                            <th className="px-4 py-3 text-left">
                                Branch Name
                            </th>

                            <th className="px-4 py-3 text-left">
                                Description
                            </th>

                            <th className="px-4 py-3 text-left">
                                Actions
                            </th>
                        </tr>
                    </thead>

                    <tbody>
                        {semesters.map((semester) => (
                            <tr
                                key={semester.id}
                                className="border-t"
                            >
                                <td className="px-4 py-3">
                                    {semester.id}
                                </td>

                                <td className="px-4 py-3">
                                    {semester.semesterNumber}
                                </td>

                                <td className="px-4 py-3">
                                    {semester.branchName}
                                </td>

                                <td className="px-4 py-3">
                                    {semester.description || "-"}
                                </td>

                                <td className="px-4 py-3">
                                    <button
                                        onClick={() =>
                                            handleDelete(semester.id)
                                        }
                                        className="rounded bg-red-600 px-3 py-1 text-white hover:bg-red-700"
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}

                        {!loading && semesters.length === 0 && (
                            <tr>
                                <td
                                    colSpan="5"
                                    className="px-4 py-6 text-center text-slate-500"
                                >
                                    No semesters found.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default SemestersPage;