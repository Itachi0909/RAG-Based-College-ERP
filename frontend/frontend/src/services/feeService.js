import api from "./api";

const feeService = {
  getAllFees: async () => {
    const response = await api.get("/admin/fees");
    return response.data;
  },

  getFeeById: async (id) => {
    const response = await api.get(`/admin/fees/${id}`);
    return response.data;
  },

  createFee: async (feeData) => {
    const response = await api.post("/admin/fees", feeData);
    return response.data;
  },

  updateFee: async (id, feeData) => {
    const response = await api.put(`/admin/fees/${id}`, feeData);
    return response.data;
  },

  payFee: async (id, paymentData) => {
    const response = await api.put(`/admin/fees/${id}/pay`, paymentData);
    return response.data;
  },

  deleteFee: async (id) => {
    const response = await api.delete(`/admin/fees/${id}`);
    return response.data;
  },

  getStudentFees: async () => {
    const response = await api.get("/admin/fees/student");
    return response.data;
  },

  getStudentFees: async () => {
    const response = await api.get("/student/fees");
    return response.data;
  },
};

export default feeService;
