import { useEffect, useState } from "react";
import departmentService from "../../services/departmentService";

function DepartmentsPage() {
    const [departments, setDepartments] = useState([]);

    const [formData, setFormData] = useState({
        name: "",
        description: "",
    });

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const loadDepartments = async () => {
        try {
            setLoading(true);
            setError("");

            const data = await departmentService.getAllDepartments();
            setDepartments(data);
        } catch (err) {
            setError(
                err.response?.data?.message ||
                "Failed to load departments"
            );
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadDepartments();
    }, []);

    const handleChange = (event) => {
        const { name, value } = event.target;

        setFormData((previousData) => ({
            ...previousData,
            [name]: value,
        }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        setError("");
        setSuccess("");
        setSaving(true);

        try {
            await departmentService.createDepartment(formData);

            setSuccess("Department added successfully");

            setFormData({
                name: "",
                description: "",
            });

            await loadDepartments();
        } catch (err) {
            setError(
                err.response?.data?.message ||
                "Failed to add department"
            );
        } finally {
            setSaving(false);
        }
    };

    const handleDelete = async (id) => {
        const confirmed = window.confirm(
            "Are you sure you want to delete this department?"
        );

        if (!confirmed) {
            return;
        }

        try {
            setError("");
            setSuccess("");

            await departmentService.deleteDepartment(id);

            setSuccess("Department deleted successfully");

            await loadDepartments();
        } catch (err) {
            setError(
                err.response?.data?.message ||
                "Failed to delete department"
            );
        }
    };

    return (
        <div className="p-6">
            <div className="mb-6">
                <h1 className="text-3xl font-bold text-slate-800">
                    Department Management
                </h1>

                <p className="mt-2 text-slate-600">
                    Add and manage departments.
                </p>
            </div>

            <div className="mb-8 rounded-xl bg-white p-6 shadow">

                {error && (
                    <div className="mb-4 rounded-lg bg-red-100 px-4 py-3 text-red-700">
                        {error}
                    </div>
                )}

                {success && (
                    <div className="mb-4 rounded-lg bg-green-100 px-4 py-3 text-green-700">
                        {success}
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                    <div className="grid gap-4 md:grid-cols-2">
                        <div>
                            <label className="mb-1 block font-medium text-slate-700">
                                Department Name
                            </label>

                            <input
                                type="text"
                                name="name"
                                value={formData.name}
                                onChange={handleChange}
                                required
                                maxLength={100}
                                placeholder="Enter department name"
                                className="w-full rounded-lg border border-slate-300 px-4 py-2 outline-none focus:border-blue-500"
                            />
                        </div>

                        <div>
                            <label className="mb-1 block font-medium text-slate-700">
                                Description
                            </label>

                            <input
                                type="text"
                                name="description"
                                value={formData.description}
                                onChange={handleChange}
                                maxLength={300}
                                placeholder="Enter department description"
                                className="w-full rounded-lg border border-slate-300 px-4 py-2 outline-none focus:border-blue-500"
                            />
                        </div>
                    </div>

                    <button
                        type="submit"
                        disabled={saving}
                        className="mt-5 rounded-lg bg-blue-600 px-5 py-2 font-medium text-white hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-60"
                    >
                        {saving ? "Saving..." : "Add Department"}
                    </button>
                </form>
            </div>

            <div className="rounded-xl bg-white p-6 shadow">
                <h2 className="mb-4 text-xl font-semibold text-slate-800">
                    All Departments
                </h2>

                {loading ? (
                    <p className="text-slate-600">
                        Loading departments...
                    </p>
                ) : departments.length === 0 ? (
                    <p className="text-slate-600">
                        No departments found.
                    </p>
                ) : (
                    <div className="overflow-x-auto">
                        <table className="w-full border-collapse">
                            <thead>
                                <tr className="border-b bg-slate-100 text-left">
                                    <th className="px-4 py-3">
                                        ID
                                    </th>

                                    <th className="px-4 py-3">
                                        Name
                                    </th>

                                    <th className="px-4 py-3">
                                        Description
                                    </th>

                                    <th className="px-4 py-3">
                                        Actions
                                    </th>
                                </tr>
                            </thead>

                            <tbody>
                                {departments.map((department) => (
                                    <tr
                                        key={department.id}
                                        className="border-b"
                                    >
                                        <td className="px-4 py-3">
                                            {department.id}
                                        </td>

                                        <td className="px-4 py-3 font-medium">
                                            {department.name}
                                        </td>

                                        <td className="px-4 py-3">
                                            {department.description || "-"}
                                        </td>

                                        <td className="px-4 py-3">
                                            <button
                                                type="button"
                                                onClick={() =>
                                                    handleDelete(
                                                        department.id
                                                    )
                                                }
                                                className="rounded-lg bg-red-600 px-3 py-1 text-sm font-medium text-white hover:bg-red-700"
                                            >
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
        </div>
    );
}

export default DepartmentsPage;