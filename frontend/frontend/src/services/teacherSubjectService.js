import api from "./api";

const getAllTeacherSubjects = async () => {
    const response = await api.get("/admin/teacher-subjects");
    return response.data;
};

const createTeacherSubject = async (data) => {
    const response = await api.post("/admin/teacher-subjects", data);
    return response.data;
};

const updateTeacherSubject = async (id, data) => {
    const response = await api.put(`/admin/teacher-subjects/${id}`, data);
    return response.data;
};

const deleteTeacherSubject = async (id) => {
    const response = await api.delete(`/admin/teacher-subjects/${id}`);
    return response.data;
};

const getMyTeacherSubjects = async () => {
    const response = await api.get("/teacher/subjects");
    return response.data;
};

export default {
    getAllTeacherSubjects,
    createTeacherSubject,
    updateTeacherSubject,
    deleteTeacherSubject,
    getMyTeacherSubjects,
};