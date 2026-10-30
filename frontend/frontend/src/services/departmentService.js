import api from "./api";

const getAllDepartments = async () => {
    const response = await api.get("/admin/departments");

    return response.data;
};

const getDepartmentById = async (id) => {
    const response = await api.get(
        `/admin/departments/${id}`
    );

    return response.data;
};

const createDepartment = async (departmentData) => {
    const response = await api.post(
        "/admin/departments",
        departmentData
    );

    return response.data;
};

const updateDepartment = async (id, departmentData) => {
    const response = await api.put(
        `/admin/departments/${id}`,
        departmentData
    );

    return response.data;
};

const deleteDepartment = async (id) => {
    const response = await api.delete(
        `/admin/departments/${id}`
    );

    return response.data;
};

export default {
    getAllDepartments,
    getDepartmentById,
    createDepartment,
    updateDepartment,
    deleteDepartment,
};