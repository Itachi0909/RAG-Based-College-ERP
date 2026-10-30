import api from "./api";

const getAllSemesters = async () => {
    const response = await api.get("/admin/semesters");
    return response.data;
};

const getSemesterById = async (id) => {
    const response = await api.get(`/admin/semesters/${id}`);
    return response.data;
};

const createSemester = async (semesterData) => {
    const response = await api.post("/admin/semesters", semesterData);
    return response.data;
};

const updateSemester = async (id, semesterData) => {
    const response = await api.put(`/admin/semesters/${id}`, semesterData);
    return response.data;
};

const deleteSemester = async (id) => {
    const response = await api.delete(`/admin/semesters/${id}`);
    return response.data;
};

export default {
    getAllSemesters,
    getSemesterById,
    createSemester,
    updateSemester,
    deleteSemester,
};