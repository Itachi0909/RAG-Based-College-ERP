import { useState } from "react";
import { useNavigate } from "react-router-dom";
import subjectService from "../../services/subjectService";

function AddSubjectPage() {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        subjectCode: "",
        subjectName: "",
        description: "",
        credits: "",
        subjectType: "",
    });

    const [error, setError] = useState("");
    const [saving, setSaving] = useState(false);

    const handleChange = (event) => {
        const { name, value } = event.target;

        setFormData((previousData) => ({
            ...previousData,
            [name]: value,
        }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            setSaving(true);
            setError("");

            await subjectService.createSubject({
                subjectCode: formData.subjectCode.trim(),
                subjectName: formData.subjectName.trim(),
                description: formData.description.trim(),
                credits: Number(formData.credits),
                subjectType: formData.subjectType,
            });

            navigate("/admin/subjects");
        } catch (err) {
            setError(
                err.response?.data?.message ||
                "Failed to create subject."
            );
        } finally {
            setSaving(false);
        }
    };

    return (
        <div className="max-w-3xl mx-auto">
            <div className="mb-8">
                <h1 className="text-3xl font-bold text-slate-800">
                    Add Subject
                </h1>

                <p className="text-slate-600 mt-2">
                    Create a new subject.
                </p>
            </div>

            <form
                onSubmit={handleSubmit}
                className="bg-white rounded-xl shadow-sm p-6 space-y-6"
            >
                {error && (
                    <div className="rounded-lg bg-red-50 border border-red-200 px-4 py-3 text-red-700">
                        {error}
                    </div>
                )}

                <div>
                    <label className="block text-sm font-medium text-slate-700 mb-2">
                        Subject Code
                    </label>

                    <input
                        type="text"
                        name="subjectCode"
                        value={formData.subjectCode}
                        onChange={handleChange}
                        placeholder="Example: CS101"
                        maxLength={20}
                        required
                        className="w-full border border-slate-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-slate-400"
                    />
                </div>

                <div>
                    <label className="block text-sm font-medium text-slate-700 mb-2">
                        Subject Name
                    </label>

                    <input
                        type="text"
                        name="subjectName"
                        value={formData.subjectName}
                        onChange={handleChange}
                        placeholder="Example: Data Structures"
                        maxLength={150}
                        required
                        className="w-full border border-slate-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-slate-400"
                    />
                </div>

                <div>
                    <label className="block text-sm font-medium text-slate-700 mb-2">
                        Description
                    </label>

                    <textarea
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                        placeholder="Enter subject description"
                        maxLength={300}
                        rows={4}
                        className="w-full border border-slate-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-slate-400"
                    />
                </div>

                <div>
                    <label className="block text-sm font-medium text-slate-700 mb-2">
                        Credits
                    </label>

                    <input
                        type="number"
                        name="credits"
                        value={formData.credits}
                        onChange={handleChange}
                        min="1"
                        max="10"
                        required
                        className="w-full border border-slate-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-slate-400"
                    />
                </div>

                <div>
                    <label className="block text-sm font-medium text-slate-700 mb-2">
                        Subject Type
                    </label>

                    <select
                        name="subjectType"
                        value={formData.subjectType}
                        onChange={handleChange}
                        required
                        className="w-full border border-slate-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-slate-400"
                    >
                        <option value="">
                            Select subject type
                        </option>

                        <option value="THEORY">
                            Theory
                        </option>

                        <option value="PRACTICAL">
                            Practical
                        </option>
                    </select>
                </div>

                <div className="flex justify-end gap-3">
                    <button
                        type="button"
                        onClick={() => navigate("/admin/subjects")}
                        className="px-5 py-3 rounded-lg border border-slate-300 text-slate-700 hover:bg-slate-100"
                    >
                        Cancel
                    </button>

                    <button
                        type="submit"
                        disabled={saving}
                        className="px-5 py-3 rounded-lg bg-slate-700 text-white hover:bg-slate-800 disabled:opacity-50"
                    >
                        {saving ? "Saving..." : "Save Subject"}
                    </button>
                </div>
            </form>
        </div>
    );
}

export default AddSubjectPage;