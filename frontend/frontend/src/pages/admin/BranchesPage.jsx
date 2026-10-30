import { useEffect, useState } from "react";
import branchService from "../../services/branchService";
import departmentService from "../../services/departmentService";

function BranchesPage() {
    const [branches, setBranches] = useState([]);
    const [departments, setDepartments] = useState([]);

    const [formData, setFormData] = useState({
        name: "",
        description: "",
        departmentId: "",
    });

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const loadData = async () => {
        try {
            setLoading(true);
            setError("");

            const [branchesData, departmentsData] = await Promise.all([
                branchService.getAllBranches(),
                departmentService.getAllDepartments(),
            ]);

            setBranches(branchesData);
            setDepartments(departmentsData);
        } catch (err) {
            setError(
                err.response?.data?.message ||
                "Failed to load branches"
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
            await branchService.createBranch({
                name: formData.name,
                description: formData.description,
                departmentId: Number(formData.departmentId),
            });

            setSuccess("Branch added successfully");

            setFormData({
                name: "",
                description: "",
                departmentId: "",
            });

            await loadData();
        } catch (err) {
            setError(
                err.response?.data?.message ||
                "Failed to add branch"
            );
        } finally {
            setSaving(false);
        }
    };

    const handleDelete = async (id) => {
        const confirmed = window.confirm(
            "Are you sure you want to delete this branch?"
        );

        if (!confirmed) {
            return;
        }

        try {
            setError("");
            setSuccess("");

            await branchService.deleteBranch(id);

            setSuccess("Branch deleted successfully");

            await loadData();
        } catch (err) {
            setError(
                err.response?.data?.message ||
                "Failed to delete branch"
            );
        }
    };

    return (
        <div className="p-6">
            <div className="mb-6">
                <h1 className="text-3xl font-bold text-slate-800">
                    Branch Management
                </h1>

                <p className="mt-2 text-slate-600">
                    Add and manage academic branches.
                </p>
            </div>

            <div className="mb-8 rounded-xl bg-white p-6 shadow">
                <h2 className="mb-4 text-xl font-semibold text-slate-800">
                    Add Branch
                </h2>

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
                    <div className="grid gap-4 md:grid-cols-3">
                        <div>
                            <label className="mb-1 block font-medium text-slate-700">
                                Branch Name
                            </label>

                            <input
                                type="text"
                                name="name"
                                value={formData.name}
                                onChange={handleChange}
                                required
                                maxLength={100}
                                placeholder="Enter branch name"
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
                                placeholder="Enter branch description"
                                className="w-full rounded-lg border border-slate-300 px-4 py-2 outline-none focus:border-blue-500"
                            />
                        </div>

                        <div>
                            <label className="mb-1 block font-medium text-slate-700">
                                Department
                            </label>

                            <select
                                name="departmentId"
                                value={formData.departmentId}
                                onChange={handleChange}
                                required
                                className="w-full rounded-lg border border-slate-300 bg-white px-4 py-2 outline-none focus:border-blue-500"
                            >
                                <option value="">
                                    Select Department
                                </option>

                                {departments.map((department) => (
                                    <option
                                        key={department.id}
                                        value={department.id}
                                    >
                                        {department.name}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>

                    <button
                        type="submit"
                        disabled={saving}
                        className="mt-5 rounded-lg bg-blue-600 px-5 py-2 font-medium text-white hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-60"
                    >
                        {saving ? "Saving..." : "Add Branch"}
                    </button>
                </form>
            </div>

            <div className="rounded-xl bg-white p-6 shadow">
                <h2 className="mb-4 text-xl font-semibold text-slate-800">
                    All Branches
                </h2>

                {loading ? (
                    <p className="text-slate-600">
                        Loading branches...
                    </p>
                ) : branches.length === 0 ? (
                    <p className="text-slate-600">
                        No branches found.
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
                                        Branch Name
                                    </th>

                                    <th className="px-4 py-3">
                                        Description
                                    </th>

                                    <th className="px-4 py-3">
                                        Department
                                    </th>

                                    <th className="px-4 py-3">
                                        Actions
                                    </th>
                                </tr>
                            </thead>

                            <tbody>
                                {branches.map((branch) => (
                                    <tr
                                        key={branch.id}
                                        className="border-b hover:bg-slate-50"
                                    >
                                        <td className="px-4 py-3">
                                            {branch.id}
                                        </td>

                                        <td className="px-4 py-3 font-medium text-slate-800">
                                            {branch.name}
                                        </td>

                                        <td className="px-4 py-3 text-slate-600">
                                            {branch.description || "-"}
                                        </td>

                                        <td className="px-4 py-3 text-slate-600">
                                            {branch.departmentName || "-"}
                                        </td>

                                        <td className="px-4 py-3">
                                            <button
                                                type="button"
                                                onClick={() =>
                                                    handleDelete(branch.id)
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

export default BranchesPage;