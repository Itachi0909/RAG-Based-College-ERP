import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import feeService from "../../services/feeService";

const FeesPage = () => {
  const navigate = useNavigate();

  const [fees, setFees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadFees = async () => {
    try {
      setLoading(true);
      const data = await feeService.getAllFees();
      setFees(data);
    } catch (err) {
      setError(err.response?.data?.message || "Failed to load fees.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadFees();
  }, []);

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this fee?",
    );

    if (!confirmed) return;

    try {
      await feeService.deleteFee(id);
      setFees((previousFees) => previousFees.filter((fee) => fee.id !== id));
    } catch (err) {
      alert(err.response?.data?.message || "Failed to delete fee.");
    }
  };

  const getStatusClass = (status) => {
    switch (status) {
      case "PAID":
        return "bg-green-100 text-green-700";
      case "PARTIALLY_PAID":
        return "bg-yellow-100 text-yellow-700";
      default:
        return "bg-red-100 text-red-700";
    }
  };

  return (
    <div className="p-6">
      <div className="mb-6 flex flex-wrap items-center justify-between gap-4">
        <div>
          <h1 className="text-3xl font-bold text-slate-800">Fee Management</h1>
          <p className="mt-1 text-slate-500">
            Manage student fees and payment records.
          </p>
        </div>

        <button
          onClick={() => navigate("/admin/fees/add")}
          className="rounded-lg bg-blue-600 px-4 py-2 font-medium text-white hover:bg-blue-700"
        >
          Add Fee
        </button>
      </div>

      {error && (
        <div className="mb-4 rounded-lg bg-red-100 p-4 text-red-700">
          {error}
        </div>
      )}

      <div className="overflow-x-auto rounded-lg bg-white shadow">
        {loading ? (
          <div className="p-6 text-center text-slate-500">Loading fees...</div>
        ) : fees.length === 0 ? (
          <div className="p-6 text-center text-slate-500">
            No fee records found.
          </div>
        ) : (
          <table className="min-w-full">
            <thead className="bg-slate-100">
              <tr>
                <th className="px-4 py-3 text-left">Student</th>
                <th className="px-4 py-3 text-left">Enrollment</th>
                <th className="px-4 py-3 text-left">Semester</th>
                <th className="px-4 py-3 text-left">Total Amount</th>
                <th className="px-4 py-3 text-left">Paid Amount</th>
                <th className="px-4 py-3 text-left">Due Amount</th>
                <th className="px-4 py-3 text-left">Due Date</th>
                <th className="px-4 py-3 text-left">Status</th>
                <th className="px-4 py-3 text-left">Actions</th>
              </tr>
            </thead>

            <tbody>
              {fees.map((fee) => (
                <tr key={fee.id} className="border-t hover:bg-slate-50">
                  <td className="px-4 py-3">{fee.studentName || "-"}</td>

                  <td className="px-4 py-3">{fee.enrollmentNumber || "-"}</td>

                  <td className="px-4 py-3">
                    {fee.semesterNumber
                      ? `Semester ${fee.semesterNumber}`
                      : "-"}
                  </td>

                  <td className="px-4 py-3">₹{fee.totalAmount ?? 0}</td>

                  <td className="px-4 py-3">₹{fee.paidAmount ?? 0}</td>

                  <td className="px-4 py-3">₹{fee.dueAmount ?? 0}</td>

                  <td className="px-4 py-3">{fee.dueDate || "-"}</td>

                  <td className="px-4 py-3">
                    <span
                      className={`rounded-full px-3 py-1 text-xs font-semibold ${getStatusClass(
                        fee.status,
                      )}`}
                    >
                      {fee.status || "PENDING"}
                    </span>
                  </td>

                  <td className="px-4 py-3">
                    <div className="flex flex-wrap gap-2">
                      {Number(fee.dueAmount) > 0 && (
                        <button
                          onClick={() => navigate(`/admin/fees/${fee.id}/pay`)}
                          className="rounded bg-green-600 px-3 py-1 text-sm font-medium text-white hover:bg-green-700"
                        >
                          Pay
                        </button>
                      )}

                      <button
                        onClick={() => handleDelete(fee.id)}
                        className="rounded bg-red-600 px-3 py-1 text-sm font-medium text-white hover:bg-red-700"
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

export default FeesPage;
