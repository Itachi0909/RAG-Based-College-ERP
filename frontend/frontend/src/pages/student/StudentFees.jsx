import { useEffect, useState } from "react";
import feeService from "../../services/feeService";

const StudentFees = () => {
    const [fees, setFees] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const fetchFees = async () => {
            try {
                const data = await feeService.getStudentFees();
                setFees(data);
            } catch (err) {
                console.error(err);
                setError("Failed to load fees.");
            } finally {
                setLoading(false);
            }
        };

        fetchFees();
    }, []);

    if (loading) {
        return <p className="p-6 text-gray-600">Loading fees...</p>;
    }

    return (
        <div className="space-y-6 p-6">
            <div>
                <h1 className="text-3xl font-bold text-slate-800">
                    My Fees
                </h1>
                <p className="text-gray-600">
                    View your fee details and payment status
                </p>
            </div>

            {error && (
                <div className="rounded-lg bg-red-100 p-4 text-red-700">
                    {error}
                </div>
            )}

            {!error && fees.length === 0 && (
                <div className="rounded-lg bg-white p-6 text-gray-600 shadow">
                    No fee records found.
                </div>
            )}

            {!error && fees.length > 0 && (
                <div className="overflow-x-auto rounded-lg bg-white shadow">
                    <table className="min-w-full">
                        <thead className="bg-slate-100">
                            <tr>
                                <th className="px-6 py-3 text-left">
                                    Semester
                                </th>
                                <th className="px-6 py-3 text-left">
                                    Total Amount
                                </th>
                                <th className="px-6 py-3 text-left">
                                    Paid Amount
                                </th>
                                <th className="px-6 py-3 text-left">
                                    Due Amount
                                </th>
                                <th className="px-6 py-3 text-left">
                                    Due Date
                                </th>
                                <th className="px-6 py-3 text-left">
                                    Status
                                </th>
                            </tr>
                        </thead>

                        <tbody>
                            {fees.map((fee) => (
                                <tr
                                    key={fee.id}
                                    className="border-t hover:bg-slate-50"
                                >
                                    <td className="px-6 py-4">
                                        Semester {fee.semesterNumber}
                                    </td>
                                    <td className="px-6 py-4">
                                        ₹{fee.totalAmount}
                                    </td>
                                    <td className="px-6 py-4">
                                        ₹{fee.paidAmount}
                                    </td>
                                    <td className="px-6 py-4">
                                        ₹{fee.dueAmount}
                                    </td>
                                    <td className="px-6 py-4">
                                        {fee.dueDate || "-"}
                                    </td>
                                    <td className="px-6 py-4">
                                        <span
                                            className={`rounded-full px-3 py-1 text-sm font-medium ${
                                                fee.status === "PAID"
                                                    ? "bg-green-100 text-green-700"
                                                    : fee.status ===
                                                        "PARTIALLY_PAID"
                                                      ? "bg-yellow-100 text-yellow-700"
                                                      : "bg-red-100 text-red-700"
                                            }`}
                                        >
                                            {fee.status}
                                        </span>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default StudentFees;