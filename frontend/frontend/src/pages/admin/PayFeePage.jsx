import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import feeService from "../../services/feeService";

const PayFeePage = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [fee, setFee] = useState(null);
    const [amount, setAmount] = useState("");
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadFee = async () => {
            try {
                const data = await feeService.getFeeById(id);
                setFee(data);
            } catch (err) {
                setError(
                    err.response?.data?.message ||
                        "Failed to load fee details."
                );
            } finally {
                setLoading(false);
            }
        };

        loadFee();
    }, [id]);

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError("");

        if (!amount || Number(amount) <= 0) {
            setError("Please enter a valid payment amount.");
            return;
        }

        if (Number(amount) > Number(fee.dueAmount)) {
            setError("Payment amount cannot exceed the due amount.");
            return;
        }

        try {
            setSaving(true);

            await feeService.payFee(id, {
                amount: Number(amount),
            });

            navigate("/admin/fees");
        } catch (err) {
            setError(
                err.response?.data?.message ||
                    "Failed to record payment."
            );
        } finally {
            setSaving(false);
        }
    };

    if (loading) {
        return (
            <div className="p-6 text-center text-slate-500">
                Loading fee details...
            </div>
        );
    }

    if (!fee) {
        return (
            <div className="p-6 text-center text-red-600">
                Fee record not found.
            </div>
        );
    }

    return (
        <div className="mx-auto max-w-2xl p-6">
            <div className="mb-6">
                <h1 className="text-3xl font-bold text-slate-800">
                    Pay Fee
                </h1>
                <p className="mt-1 text-slate-500">
                    Record a payment for this fee.
                </p>
            </div>

            {error && (
                <div className="mb-5 rounded-lg bg-red-100 p-4 text-red-700">
                    {error}
                </div>
            )}

            <div className="mb-5 rounded-lg bg-white p-6 shadow">
                <div className="grid gap-4 sm:grid-cols-2">
                    <div>
                        <p className="text-sm text-slate-500">
                            Student
                        </p>
                        <p className="font-semibold text-slate-800">
                            {fee.studentName || "-"}
                        </p>
                    </div>

                    <div>
                        <p className="text-sm text-slate-500">
                            Enrollment Number
                        </p>
                        <p className="font-semibold text-slate-800">
                            {fee.enrollmentNumber || "-"}
                        </p>
                    </div>

                    <div>
                        <p className="text-sm text-slate-500">
                            Total Amount
                        </p>
                        <p className="font-semibold text-slate-800">
                            ₹{fee.totalAmount ?? 0}
                        </p>
                    </div>

                    <div>
                        <p className="text-sm text-slate-500">
                            Already Paid
                        </p>
                        <p className="font-semibold text-slate-800">
                            ₹{fee.paidAmount ?? 0}
                        </p>
                    </div>

                    <div>
                        <p className="text-sm text-slate-500">
                            Due Amount
                        </p>
                        <p className="font-semibold text-red-600">
                            ₹{fee.dueAmount ?? 0}
                        </p>
                    </div>

                    <div>
                        <p className="text-sm text-slate-500">
                            Status
                        </p>
                        <p className="font-semibold text-slate-800">
                            {fee.status || "PENDING"}
                        </p>
                    </div>
                </div>
            </div>

            <form
                onSubmit={handleSubmit}
                className="space-y-5 rounded-lg bg-white p-6 shadow"
            >
                <div>
                    <label className="mb-2 block font-medium text-slate-700">
                        Payment Amount
                    </label>

                    <input
                        type="number"
                        value={amount}
                        onChange={(event) =>
                            setAmount(event.target.value)
                        }
                        min="0.01"
                        max={fee.dueAmount}
                        step="0.01"
                        placeholder="Enter payment amount"
                        className="w-full rounded-lg border border-slate-300 px-4 py-2"
                        required
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
                        className="rounded-lg bg-green-600 px-5 py-2 font-medium text-white hover:bg-green-700 disabled:cursor-not-allowed disabled:opacity-60"
                    >
                        {saving ? "Processing..." : "Record Payment"}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default PayFeePage;