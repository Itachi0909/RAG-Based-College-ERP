import api from "./api";

const getAllBranches = async () => {
    const response = await api.get("/admin/branches");
    return response.data;
};

const getBranchById = async (id) => {
    const response = await api.get(`/admin/branches/${id}`);
    return response.data;
};

const createBranch = async (branchData) => {
    const response = await api.post("/admin/branches", branchData);
    return response.data;
};

const updateBranch = async (id, branchData) => {
    const response = await api.put(`/admin/branches/${id}`, branchData);
    return response.data;
};

const deleteBranch = async (id) => {
    const response = await api.delete(`/admin/branches/${id}`);
    return response.data;
};

export default {
    getAllBranches,
    getBranchById,
    createBranch,
    updateBranch,
    deleteBranch,
};